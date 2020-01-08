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
import com.example.wechatframe.view.TabView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityTab extends AppCompatActivity {

    private static final String BUNDLE_KEY_POS = "bundle_key_pos";

    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信", "通讯录", "发现", "我"));

    private TabView mTabWechat;
    private TabView mTabFriend;
    private TabView mTabFind;
    private TabView mTabMine;

    private List<TabView> mTabs = new ArrayList<>();

    private SparseArray<TabFragment> mFragments = new SparseArray<>();

    private int mCurTabPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        if (savedInstanceState != null) {
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS);
        }
        initViewPager();
        initView();
        initEvents();
    }

    private void initEvents() {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVpMain.setCurrentItem(finalI, false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_KEY_POS, mCurTabPos);
        super.onSaveInstanceState(outState);
    }

    private void initViewPager() {
        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TabFragment.newInstance(mTitles.get(position));
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
                if (positionOffset > 0) {
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);

                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }
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
        mTabWechat = findViewById(R.id.tab_wechat);
        mTabFriend = findViewById(R.id.tab_friend);
        mTabFind = findViewById(R.id.tab_find);
        mTabMine = findViewById(R.id.tab_mine);

        mTabWechat.setIconAndText(R.drawable.wechat, R.drawable.wechat_select, "微信");
        mTabFriend.setIconAndText(R.drawable.friend, R.drawable.friend_select, "通讯录");
        mTabFind.setIconAndText(R.drawable.find, R.drawable.find_select, "发现");
        mTabMine.setIconAndText(R.drawable.mine, R.drawable.mine_select, "我");

        mTabs.add(mTabWechat);
        mTabs.add(mTabFriend);
        mTabs.add(mTabFind);
        mTabs.add(mTabMine);
        setCurrentTab(0);
    }

    private void setCurrentTab(int pos) {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            if (i == pos) {
                tabView.setProgress(1);
            } else {
                tabView.setProgress(0);
            }
        }
    }
}
