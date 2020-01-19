package com.example.exercise.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @auther to Chenstyle
 * @time 2020-1-19 15:09
 */
public class T06_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });

        new Thread(task).start();

        System.out.println(task.get()); // 阻塞

        //****************************
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });

        System.out.println(f.get());
        System.out.println(f.isDone());
        service.shutdown(); // 线程池需要手动关闭
    }
}
