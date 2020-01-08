package com.example.wechatframe;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wechatframe.fragment.TabFragment;
import com.example.wechatframe.util.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信", "通讯录", "发现", "我"));

    private Button mBtnWechat;
    private Button mBtnFriend;
    private Button mBtnFind;
    private Button mBtnMine;

    private SparseArray<TabFragment> mFragments = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
        initView();


    }

    private void initViewPager() {
        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                TabFragment fragment = TabFragment.newInstance(mTitles.get(position));
                if (position == 0) {
                    fragment.setOnTitleClickListener(new TabFragment.OnClickTitleListener() {
                        @Override
                        public void clickTitle(String title) {
                            changeWechatTab(title);
                        }
                    });
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                mFragments.delete(position);
                super.destroyItem(container, position, object);
            }
        });

        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                L.d("onPageScrolled pos = " + position + " , positionOffset = " + positionOffset);

                // 左->右 0-1, left pos, right pos + 1, positionOffset 0~1
                // left progress: 1~0 (1 - positionOffset); right progress: 0~1 positionOffset;

                // 右->左 1-0, left pos, right pos + 1, positionOffset 1~0
                // left progress: 0~1(1 - positionOffset); right progress: 1~0 positionOffset;

                // left tab
                // right tab
            }

            @Override
            public void onPageSelected(int position) {
                L.d("onPageSelected pos = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mBtnWechat = findViewById(R.id.btn_wechat);
        mBtnFriend = findViewById(R.id.btn_friend);
        mBtnFind = findViewById(R.id.btn_find);
        mBtnMine = findViewById(R.id.btn_mine);

        mBtnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment tabFragment = mFragments.get(0);
                if (tabFragment != null) {
                    tabFragment.changeTitle("微信 Changed!");
                }
            }
        });
    }

    public void changeWechatTab(String title) {
        mBtnWechat.setText(title);
    }
}
