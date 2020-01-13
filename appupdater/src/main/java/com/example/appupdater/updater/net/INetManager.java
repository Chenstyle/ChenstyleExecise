package com.example.appupdater.updater.net;

import java.io.File;

/**
 * Created by lizhi for WechatFrame
 * at the 2020-1-13 21:20
 */
public interface INetManager {

    void get(String url, INetCallBack callBack, Object tag);

    void download(String url, File targetFile, INetDownloadCallBack callBack, Object tag);

    void cancel(Object tag);
}
