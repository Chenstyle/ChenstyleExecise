package com.qiesi.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mTips;
    private TextView mShowClick;

    private Handler mHandler;

    private int mClickTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mHandler = new Handler(Looper.getMainLooper());
        mTips = findViewById(R.id.tv_task_tips);
        mShowClick = findViewById(R.id.txw_show_click);
        Button mBtnStart = findViewById(R.id.btn_start_task);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mClickTimes++;
                        mShowClick.setText(String.valueOf(mClickTimes));
                        try {
                            TimeUnit.MILLISECONDS.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mTips.setText("Changed!!! " + mClickTimes);
                    }
                });
            }
        });
    }
}
