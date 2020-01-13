package com.example.appupdater.updater.net;

import java.io.File;

/**
 * Created by lizhi for WechatFrame
 * at the 2020-1-13 21:23
 */
public interface INetDownloadCallBack {

    void success(File apkFile);

    void progress(int progress);

    void failed(Throwable throwable);
}
