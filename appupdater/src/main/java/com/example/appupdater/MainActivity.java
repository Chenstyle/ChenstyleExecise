package com.example.appupdater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appupdater.updater.AppUpdater;
import com.example.appupdater.updater.net.INetCallBack;
import com.example.appupdater.updater.net.INetDownloadCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button mBtnUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnUpdater = findViewById(R.id.btn_updater);
        mBtnUpdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater.getInstance().getNetManager().get("", new INetCallBack() {
                    @Override
                    public void success(String response) {
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

                        AppUpdater.getInstance().getNetManager().download("", null, new INetDownloadCallBack() {
                            @Override
                            public void success(File apkFile) {
                                // 安装的代码
                            }

                            @Override
                            public void progress(int progress) {
                                // 更新界面的代码
                            }

                            @Override
                            public void failed(Throwable throwable) {

                            }
                        });
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "版本更新接口请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
