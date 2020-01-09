package com.example.wechatframe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wechatframe.fragment.SplashFragment;
import com.example.wechatframe.transform.SplashTransformer;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mVpSplash;

    private int[] mResIds = new int[] {
            R.drawable.guide_image1,
            R.drawable.guide_image2,
            R.drawable.guide_image3,
            R.drawable.guide_image4
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVpSplash = findViewById(R.id.vp_splash);
        mVpSplash.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return SplashFragment.newInstance(mResIds[position]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });

        mVpSplash.setPageTransformer(true, new SplashTransformer());
    }
}
