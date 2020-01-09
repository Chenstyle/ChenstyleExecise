package com.example.wechatframe.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.wechatframe.util.L;

/**
 * Created by Chenstyle on 2020-1-9.
 * Used to WechatMainInterface.
 */
public class SplashTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        L.d("ViewTag = " + page.getTag() + " position = " + position);

        // a->b
        // a , position : (0, -1)
        // b , position : (1,  0)

        // b->a
        // a , position : (-1, 0)
        // b , position : (0,  1)

        // [, -1]
        if (position < -1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        } else if (position <= 1) {

            // 左边这个页面
            if (position < 0) {
                // a->b position : (0, -1)
                // [1, 0.75f]
                // 从页面A滑动到页面B，A页面position的值是从0到-1的。
                // 页面A的缩放值是从1到0.75之间的。
                float scaleA = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);

                // [1, 0.5f]
                // 左边的页面是要消失的，从清晰到模糊
                float alphaA = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);
                page.setAlpha(alphaA);
                L.d("alphaA = " + alphaA);

                // b->a position : (-1, 0)
                // [0.75f, 1]
            } else { // 右边这个页面
                L.d("设置右边");
                // a->b
                // b, position : (1, 0)
                // [0.75f, 1]
                // 页面A滑动到页面B，B页面position的值是从1到0的。
                // 页面B的缩放值是从0.75到1之间的
                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                page.setScaleX(scaleB);
                page.setScaleY(scaleB);

                // [0.5, 1]
                // 右边的页面是进入的，从模糊到清晰
                float alphaB = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - position);
                page.setAlpha(alphaB);
                L.d("alphaB = " + alphaB);
                // b->a
                // b , position : (0, 1)
                // [1, 0.75f]
            }
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }
    }
}
