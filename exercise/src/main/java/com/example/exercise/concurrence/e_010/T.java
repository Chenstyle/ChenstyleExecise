package com.example.exercise.concurrence.e_010;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍会得到该对象的锁
 * 也就是说synchronized获得的锁是可重入的
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法
 *
 * @auther to Chenstyle
 * @time 2020-1-24 22:36
 */
public class T {
    public static void main(String[] args) {
        new TT().m();
    }

    synchronized void m() {
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    static class TT extends T {
        synchronized void m() {
            System.out.println("child m start");
            super.m();
            System.out.println("child m end");
        }
    }
}
