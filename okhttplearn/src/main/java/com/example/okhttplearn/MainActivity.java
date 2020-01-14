package com.example.okhttplearn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    OkHttpClient okHttpClient = new OkHttpClient();


    private String mBaseUrl = "http://192.168.0.102:8080/";

    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvShow = findViewById(R.id.tv_show);
    }

    public void doGet(View view) {

        // 1.拿到OkHttpClient对象
//        OkHttpClient okHttpClient = new OkHttpClient();

        // 2.构造Request
        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url(mBaseUrl + "login/?username=chen&password=1234").build();
        // 3 4
        executeRequest(request);
    }

    private void executeRequest(Request request) {
        // 3.将Request封装为Call
        Call call = okHttpClient.newCall(request);

        // 4.执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.d(TAG, "onResponse: " + res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvShow.setText(res);
                    }
                });

            }
        });
    }

    public void doPost(View view) {
        // 1.拿到OkHttpClient对象
        // 2.构建Request
        // 2.1构建RequestBody
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        RequestBody requestBody = requestBodyBuilder
                .add("username", "chenstyle")
                .add("password", "1234").build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(mBaseUrl + "login").post(requestBody).build();
        // 3 4
        executeRequest(request);
    }
}
