package com.example.wechatframe;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wechatframe.fragment.SplashFragment;
import com.example.wechatframe.transform.RotateTransformer;
import com.example.wechatframe.transform.SplashTransformer;

public class BannerActivity extends AppCompatActivity {

    private ViewPager mVpSplash;

    private int[] mResIds = new int[] {
            0xFFFF0000,
            0xFF00FF00,
            0xFF0000FF,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mVpSplash = findViewById(R.id.vp_banner);
        mVpSplash.setOffscreenPageLimit(mResIds.length);
        mVpSplash.setPageMargin(40); // Use DP
        mVpSplash.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mResIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = new View(container.getContext());
                view.setBackgroundColor(mResIds[position]);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(layoutParams);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        mVpSplash.setPageTransformer(true, new RotateTransformer());
    }
}
