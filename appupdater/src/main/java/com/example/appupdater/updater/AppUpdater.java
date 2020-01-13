package com.example.appupdater.updater;

import com.example.appupdater.updater.net.INetManager;
import com.example.appupdater.updater.net.OkHttpNetManager;

/**
 * Created by lizhi for WechatFrame
 * at the 2020-1-13 21:13
 */
public class AppUpdater {
    private static AppUpdater sInstance = new AppUpdater();

    // 网络请求，下载的能力
    // OkHttp, Volley, HttpClient, HttpUrlConnection
    private INetManager mNetManager = new OkHttpNetManager();

    // 更好的方法：接口隔离具体实现，定义一个setManager方法，由传递参数的使用者决定使用哪一种网络框架
    public void setNetManager(INetManager manager) {
        mNetManager = manager;
    }

    public static AppUpdater getInstance() {
        return sInstance;
    }
}
