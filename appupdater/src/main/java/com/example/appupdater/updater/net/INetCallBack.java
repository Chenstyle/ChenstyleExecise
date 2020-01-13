package com.example.appupdater.updater.net;

/**
 * Created by lizhi for WechatFrame
 * at the 2020-1-13 21:23
 */
public interface INetCallBack {
    void success(String response);

    void failed(Throwable throwable);
}
