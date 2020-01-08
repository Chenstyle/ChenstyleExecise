package com.example.wechatframe.util;

import android.util.Log;

import com.example.wechatframe.BuildConfig;

public class L {

    private static final String TAG = "Chen";

    private static boolean sDebug = BuildConfig.DEBUG;

    public static void d(String msg, Object... args) {
        if (!sDebug) {
            return;
        }
        Log.d(TAG, String.format(msg, args));
    }
}
