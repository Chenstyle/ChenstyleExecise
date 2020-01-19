package com.example.exercise.pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * WorkStealingPool默认CPU有几个线程就会创建几个线程
 * 当有一个线程执行完毕后，会主动去执行没有执行的任务
 * 是使用ForkJoinPool来实现的
 *
 * @auther to Chenstyle
 * @time 2020-1-19 17:53
 */
public class T11_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
        for (int i = 0; i < 8; i++) {
            service.execute(new R(2000));
        }

        // 由于生产的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        // DaemonThread，守护进程（Daemon）是运行在后台的一种特殊进程。它独立于控制终端并且周期性地执行某种任务或等待处理某些发生的事件。
        System.in.read();
    }

    static class R implements Runnable {
        int time;

        R(int t) {
            time = t;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
