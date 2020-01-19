package com.example.exercise.pool;

import java.util.concurrent.Executor;

/**
 * 认识 Executor
 * <p>
 * Created by Chenstyle on 2020-1-19.
 * Used to WechatMainInterface.
 */
public class T01_MyExecutor implements Executor {

    public static void main(String[] args) {
        new T01_MyExecutor().execute(() -> System.out.println("hello executor"));
    }

    @Override
    public void execute(Runnable runnable) {
//        new Thread(runnable).run();
        runnable.run();
    }
}
