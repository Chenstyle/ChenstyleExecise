package com.example.exercise.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池里只有一个线程
 * 保证了任务一定是顺序执行的，先处理的任务一定先执行完
 * 只有等第一个任务完成后才会执行第二个
 * 因为多线程并发的时候，并不能保证执行顺序，所以就有了SingleThreadPool
 *
 * @auther to Chenstyle
 * @time 2020-1-19 17:26
 */
public class T09_SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(() -> {
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
    }
}
