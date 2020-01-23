package com.example.exercise.concurrence.e_003;

/**
 * synchronized 关键字
 * 对某个对象加锁
 *
 * @auther to Chenstyle
 * @time 2020-1-23 21:06
 */
public class T {

    private int count = 10;

    public synchronized void m() { // 等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
