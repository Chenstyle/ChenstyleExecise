package com.qiesi.officialguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.qiesi.officialguide.motionlayout.MotionLayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void motionLayout(View view) {
        startOther(MotionLayoutActivity.class);
    }

    private void startOther(Class activityClass) {
        startActivity(new Intent(MainActivity.this, activityClass));
    }

}
