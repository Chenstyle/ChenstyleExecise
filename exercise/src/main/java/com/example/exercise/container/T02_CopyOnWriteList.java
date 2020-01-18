package com.example.exercise.container;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写时复制容器 copy on write
 * 多线程环境下，写时效率低，读时效率高
 * 适合写少读多的环境
 *
 * @auther lizhi
 * @time 2020-1-17 23:49
 */
public class T02_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> lists =
//                new ArrayList<>(); // 这个会出并发问题
//                new Vector<>();
                new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        lists.add("a" + r.nextInt(10000));
                    }
                }
            };
            ths[i] = new Thread(task);
        }

        runAndComputeTime(ths);
        System.out.println(lists.size());
    }

    private static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(Thread::start);
        Arrays.asList(ths).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }
}
