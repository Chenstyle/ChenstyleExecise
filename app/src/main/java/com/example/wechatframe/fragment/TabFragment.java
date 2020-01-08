package com.example.wechatframe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wechatframe.R;

public class TabFragment extends Fragment {
    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle; // 依赖外部传入的Title
    private OnClickTitleListener mClickListener;

    // 为什么要通过newInstance这种方式去设置参数，因为要用Arguments来传递参数。为什么不在Activity中new Fragment().title = ""这种形式，
    // 因为涉及到Activity和Fragment的生命周期和用户的操作习惯问题。
    public static TabFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE, title);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnTitleClickListener(OnClickTitleListener listener) {
        mClickListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_KEY_TITLE, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);

        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.clickTitle("微信 Changed!");
                }
            }
        });
    }

    public void changeTitle(String title) {
        // 暴露给外部的方法一定要有这样的约束
        if (!isAdded()) {
            return;
        }
        mTvTitle.setText(title);
    }

    public interface OnClickTitleListener {
        void clickTitle(String title);
    }
}
