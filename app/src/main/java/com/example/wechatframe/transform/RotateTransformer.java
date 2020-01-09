package com.example.wechatframe.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.wechatframe.util.L;

/**
 * Created by Chenstyle on 2020-1-9.
 * Used to WechatMainInterface.
 */
public class RotateTransformer implements ViewPager.PageTransformer {

    private static final int MAX_ROTATE = 15;

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
            // [-1, 1]
            page.setRotation(-MAX_ROTATE);
            page.setPivotY(page.getHeight());
            page.setPivotX(page.getWidth());
        } else if (position <= 1) {

            // 左边这个页面
            if (position < 0) {

                page.setPivotY(page.getHeight());
                // 0.5w, w
                page.setPivotX(0.5f * page.getWidth() + 0.5f * (-position) * page.getWidth());
                // (0, -1) -> 0, -max
                page.setRotation(MAX_ROTATE * position);
            } else { // 右边这个页面
                // a -> b
                // b, position : (1, 0)
                page.setPivotY(page.getHeight());
                // 0, 0.5w
                page.setPivotX(page.getWidth() * 0.5f * (1 - position));
                // MAX, 0
                page.setRotation(MAX_ROTATE * position);

                page.setPivotY(page.getHeight());
                page.setPivotX(page.getWidth() * 0.5f * (1 - position));
                page.setRotation(MAX_ROTATE * position);
            }
        } else {
            page.setRotation(MAX_ROTATE);
            page.setPivotY(page.getHeight());
            page.setPivotX(0);
        }
    }
}
