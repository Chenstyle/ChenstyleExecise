package com.example.exercise.concurrence.e_006;

/**
 * 对比上面一个小程序，分析一下这个程序的输出
 *
 * @auther to Chenstyle
 * @time 2020-1-24 0:00
 */
public class T implements Runnable {

    private int count = 10;

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            T t = new T();
            new Thread(t, "THREAD" + i).start();
        }
    }

    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

}
