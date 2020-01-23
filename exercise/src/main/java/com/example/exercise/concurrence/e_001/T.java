package com.example.exercise.concurrence.e_001;

/**
 * synchronized关键字
 * 对某个对象加锁
 * 真正申请锁的时候，锁的信息是记录在堆内存对象里的。
 * 只要有一个线程拿到了对象的锁，其他的线程就拿不到，这叫做互斥锁
 *
 * @auther to Chenstyle
 * @time 2020-1-23 20:52
 */
public class T {

    private int count = 10;
    private Object o = new Object();

    public void m() {
        synchronized (o) { // 任何线程要执行下面的代码，必须先拿到o的锁
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
