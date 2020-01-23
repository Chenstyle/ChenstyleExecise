package com.example.exercise.concurrence.e_004;

/**
 * synchronized 关键字
 * synchronized如果写在一个静态方法上，相当于锁的是这个类的class对象
 *
 * @auther to Chenstyle
 * @time 2020-1-23 21:16
 */
public class T {

    private static int count = 10;

    public static synchronized void m() { // 这里等同于synchronized(e.004.T.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        synchronized (T.class) { // 静态的方法，因为不需要创建对象，所以没有this的存在
            count--;
        }
    }
}
