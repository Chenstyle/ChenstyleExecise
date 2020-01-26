package com.example.exercise.concurrence.e_017;

import java.util.concurrent.TimeUnit;

/**
 * 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成另一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外的对象
 *
 * @auther lizhi
 * @time 2020-1-26 22:13
 */
public class T {

    Object o = new Object();

    void m() {
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(t::m, "t2");

        t.o = new Object(); // 锁对象发生改变，所以t2得以执行。如果注释这一句，t2永远也得不到执行机会

        t2.start();
    }
}
