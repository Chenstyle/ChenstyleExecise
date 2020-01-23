package com.example.exercise.concurrence.e_002;

/**
 * synchronized关键字
 * 对某个对象加锁
 * synchronized 锁定的是一个对象
 *
 * @auther to Chenstyle
 * @time 2020-1-23 21:01
 */
public class T {
    private int count = 10;

    public void m() {
        synchronized (this) { // 任何线程要执行下面的代码，必须先拿到this锁
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
