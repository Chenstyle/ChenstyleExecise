package com.example.exercise.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CachedThreadPool是弹性的，来一个任务起一个线程，线程数最大是int类型的最大数
 * 默认一个线程闲置60秒就会自动销毁
 *
 * @auther to Chenstyle
 * @time 2020-1-19 17:09
 */
public class T08_CachedPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);
        TimeUnit.SECONDS.sleep(80);
        System.out.println(service);
    }
}
