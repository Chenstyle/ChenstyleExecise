package com.example.exercise.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 用线程池计算素数
 *
 * @auther to Chenstyle
 * @time 2020-1-19 16:20
 */
public class T07_ParallelComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> results = getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        System.out.println("Thread count: " + results.size());

        final int cpuCoreNum = 4;
        // Fixed 固定的。固定个数的线程池
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

        // 为什么不平均？
        // 越大的数，素数计算的时间越长
        MyTask t1 = new MyTask(1, 80000); // 80000
        MyTask t2 = new MyTask(80001, 130000); // 50000
        MyTask t3 = new MyTask(130001, 170000); // 40000
        MyTask t4 = new MyTask(170001, 200000); // 30000

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        service.shutdown();
        end = System.currentTimeMillis();
        System.out.println(end - start);

        results.clear();
        try {
            results.addAll(t1.call());
            results.addAll(t2.call());
            results.addAll(t3.call());
            results.addAll(t4.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Thread pool count: " + results.size());
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) results.add(i);
        }
        return results;
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;

        MyTask(int s, int e) {
            startPos = s;
            endPos = e;
        }

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> r = getPrime(startPos, endPos);
            return r;
        }
    }
}
