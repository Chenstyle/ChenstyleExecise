package com.example.appupdater.updater.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.appupdater.R;
import com.example.appupdater.updater.AppUpdater;
import com.example.appupdater.updater.bean.DownloadBean;
import com.example.appupdater.updater.net.INetDownloadCallBack;
import com.example.appupdater.updater.utils.AppUtils;

import java.io.File;

/**
 * Created by lizhi for WechatFrame
 * at the 2020-1-13 23:59
 */
public class UpdateVersionShowDialog extends DialogFragment {
    private static final String KEY_DOWNLOAD_BEAN = "download_bean";

    private DownloadBean mDownloadBean;

    public static void show(FragmentActivity activity, DownloadBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DOWNLOAD_BEAN, bean);
        UpdateVersionShowDialog dialog = new UpdateVersionShowDialog();
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(), "updateVersionShowDialog");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mDownloadBean = (DownloadBean) arguments.getSerializable(KEY_DOWNLOAD_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_updater, container, false);
        bindEvent(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void bindEvent(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        final TextView tvUpdate = view.findViewById(R.id.tv_update);

        tvTitle.setText(mDownloadBean.title);
        tvContent.setText(mDownloadBean.content);
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                view.setEnabled(false);

                final File targetFile = new File(getActivity().getCacheDir(), "target.apk");
                // 检测是否已经下载好了Apk文件，避免重复下载
                String fileMd5 = AppUtils.getFileMd5(targetFile);
                if (fileMd5 != null && fileMd5.equals(mDownloadBean.md5)) {
                    Log.d("Chen", "已经下载好了文件，直接安装");
                    AppUtils.installApk(getActivity(), targetFile);
                    dismiss();
                    return;
                }

                AppUpdater.getInstance().getNetManager().download(mDownloadBean.url, targetFile, new INetDownloadCallBack() {
                    @Override
                    public void success(File apkFile) {
                        view.setEnabled(true);
                        // 安装的代码
                        Log.d("Chen", "success = " + apkFile.getAbsolutePath());

                        dismiss();

                        String fileMd5 = AppUtils.getFileMd5(targetFile);
                        Log.d("Chen", "md5 = " + fileMd5);
                        if (fileMd5 != null && fileMd5.equals(mDownloadBean.md5)) {
                            AppUtils.installApk(getActivity(), apkFile);
                        } else {
                            Toast.makeText(getActivity(), "md5 检测失败", Toast.LENGTH_SHORT).show();
                        }
                        
                    }

                    @Override
                    public void progress(int progress) {
                        // 更新界面的代码
                        Log.d("Chen", "progress = " + progress);
                        tvUpdate.setText(progress + "%");
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        view.setEnabled(true);
                        throwable.printStackTrace();
                        Toast.makeText(getActivity(), "文件下载失败", Toast.LENGTH_SHORT).show();

                    }
                }, UpdateVersionShowDialog.this);
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        Log.d("Chen", "onDismiss: ");
        AppUpdater.getInstance().getNetManager().cancel(UpdateVersionShowDialog.this);
    }
}
