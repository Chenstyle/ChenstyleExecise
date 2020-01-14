package com.example.appupdater.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenstyle on 2020-1-14.
 * Used to WechatMainInterface.
 */
public class TestUtil {


    private static String getApk(Context context, String packageName) {
        String appDir = null;
        try {
            //通过包名获取程序源文件路径
            appDir = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appDir;
    }

    /**
     * 获取data/app下已安装的app对应Apk
     */
    public static void getApkFileOfDataPath(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> mApps = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        if (resolveInfos != null && resolveInfos.size() > 0) {
            for (int i = 0; i < resolveInfos.size(); i++) {
                mApps.add(resolveInfos.get(i));
            }
        }
        if (mApps.size() > 0 && mApps != null) {
            for (int i = 0; i < mApps.size(); i++) {
                Log.d("Chen", getApk(context, mApps.get(i).activityInfo.packageName));
            }
        }
    }
}
