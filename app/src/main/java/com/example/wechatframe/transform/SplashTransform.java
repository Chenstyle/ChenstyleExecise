package com.example.wechatframe.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.wechatframe.util.L;

/**
 * Created by Chenstyle on 2020-1-9.
 * Used to WechatMainInterface.
 */
public class SplashTransform implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        L.d("ViewTag = " + page.getTag() + " position = " + position);
    }
}
