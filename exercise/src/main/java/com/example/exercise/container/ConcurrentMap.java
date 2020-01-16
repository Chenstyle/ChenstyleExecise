package com.example.exercise.container;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @auther lizhi
 * @time 2020-1-17 0:37
 */
public class ConcurrentMap {
    public static void main(String[] args) {
//        Map<String, String> map = new ConcurrentHashMap<>();
//        Map<String, String> map = new ConcurrentSkipListMap<>();
//        Map<String, String> map = new Hashtable<>();
        Map<String, String> map = new HashMap<>();

        Random random = new Random();
        Thread[] ths = new Thread[1000];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    map.put("a" + random.nextInt(1000000), "a" + random.nextInt(100000));
                    latch.countDown();
                }
            });
        }

        Arrays.asList(ths).forEach(Thread::start);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
