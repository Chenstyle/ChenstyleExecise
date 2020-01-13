package com.example.appupdater.updater.net;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lizhi for WechatFrame
 * at the 2020-1-13 21:28
 */
public class OkHttpNetManager implements INetManager {

    private static OkHttpClient sOkHttpClient;

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 参数设置
        builder.connectTimeout(15, TimeUnit.SECONDS);
        sOkHttpClient = builder.build();

        // http
        // https 自签名的，OkHttp 握手的错误
//        builder.sslSocketFactory(); // 传入证书相关的操作
    }

    @Override
    public void get(String url, final INetCallBack callBack) {

        // requestBuilder -> Request -> Call -> execute/enqueue
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().build();
        Call call = sOkHttpClient.newCall(request);
//        Response response = call.execute(); // 同步的操作 在当前线程直接执行
        // 异步的操作
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // 非 UI 线程
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(string);
                        }
                    });
                } catch (Throwable e) {
                    // 因为返回结果不确定性 和 数据处理不确定性，所以要捕获异常，再回调给调用方
                    e.printStackTrace();
                    callBack.failed(e);
                }
            }
        });
    }

    @Override
    public void download(String url, final File targetFile, final INetDownloadCallBack callBack) {

        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().build();
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // 文件的保存会涉及到输入流和输出流
                InputStream is = null;
                OutputStream os = null;

                try {
                    final long totalLen = response.body().contentLength(); // 总长度

                    is = response.body().byteStream(); // 拿到写入流
                    os = new FileOutputStream(targetFile); // 声明输出流
                    // buffer越大，理论上保存的越快
                    byte[] buffer = new byte[8 * 1028]; // 缓存数组
                    long curLen = 0; // 保存了多少个字节

                    int bufferLen = 0; // 缓冲
                    // 如果没有读取完
                    while ((bufferLen = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bufferLen); // 写入
                        os.flush(); // 刷新
                        curLen += bufferLen;

                        final long finalCurLen = curLen;
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.progress((int) (finalCurLen * 1.0f / totalLen * 100));
                            }
                        });
                    }

                    try {
                        targetFile.setExecutable(true, false); // 可执行
                        targetFile.setReadable(true, false); // 可读取
                        targetFile.setWritable(true, false); // 可写入
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(targetFile);
                        }
                    });
                } catch (final Throwable e) {
                    e.printStackTrace();
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.failed(e);
                        }
                    });
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                }
            }
        });
    }
}
