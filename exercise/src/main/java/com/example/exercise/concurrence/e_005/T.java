package com.example.exercise.concurrence.e_005;

/**
 * 分析一下这个程序的输出
 * <p>
 * 一个Synchronized代码块相当于一个原子操作，原子是不可分的。
 * 在线程执行这段代码块的时候持有了这把锁，在执行这段代码的过程是不可能被其他的线程打断的。
 * 只有当这个线程执行完毕之后，其他线程才能继续执行同一段代码。
 *
 * @auther to Chenstyle
 * @time 2020-1-23 23:45
 */
public class T implements Runnable {
    private int count = 10;

    public static void main(String[] args) {
        T t = new T();
        for (int i = 0; i < 5; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }

    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
