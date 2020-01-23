package com.example.exercise.concurrence.e_007;

/**
 * 同步和非同步方法是否可以同时调用
 * 可以，因为synchronized锁的是代码块，而锁是这个对象。
 *
 * @auther to Chenstyle
 * @time 2020-1-24 0:02
 */
public class T {

    public static void main(String[] args) {
        T t = new T();

        new Thread(() -> t.m1(), "t1").start();
        new Thread(() -> t.m2(), "t2").start();
    }

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2");
    }
}
