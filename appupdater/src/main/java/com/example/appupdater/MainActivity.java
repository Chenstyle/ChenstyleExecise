package com.example.appupdater;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.appupdater.updater.AppUpdater;
import com.example.appupdater.updater.bean.DownloadBean;
import com.example.appupdater.updater.net.INetCallBack;
import com.example.appupdater.updater.ui.UpdateVersionShowDialog;
import com.example.appupdater.updater.utils.AppUtils;

public class MainActivity extends FragmentActivity {

    private Button mBtnUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnUpdater = findViewById(R.id.btn_updater);
        mBtnUpdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater.getInstance().getNetManager().get("http://59.110.162.30/app_updater_version.json", new INetCallBack() {
                    @Override
                    public void success(String response) {

                        Log.d("Chen", "response = " + response);
                        
                        // 1.解析json
                        // {
                        //    "title":"4.5.0更新啦！",
                        //    "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
                        //    "url":"http://59.110.162.30/v450_imooc_updater.apk",
                        //    "md5":"14480fc08932105d55b9217c6d2fb90b",
                        //    "versionCode":"450"
                        //}
                        // 2.做版本匹配
                        // 如果需要更新
                        // 3.弹框
                        // 4.点击下载

                        DownloadBean bean = DownloadBean.parse(response);

                        if (bean == null) {
                            Toast.makeText(MainActivity.this, "版本检测接口返回数据异常", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 检测：是否需要弹框
                        try {
                            long versionCode = Long.parseLong(bean.versionCode);
                            if (versionCode <= AppUtils.getVersionCode(MainActivity.this)) {
                                Toast.makeText(MainActivity.this, "已经是最新版本，无需更新", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            // 依赖server的代码要潜在风险判断
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "版本检测接口返回版本号异常", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 弹框
                        UpdateVersionShowDialog.show(MainActivity.this, bean);
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(MainActivity.this, "版本更新接口请求失败", Toast.LENGTH_SHORT).show();
                    }
                }, MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(MainActivity.this);
    }
}
