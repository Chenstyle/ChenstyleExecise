package com.example.exercise.concurrence.e_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock用于替代synchronized
 * 本例中由于m1锁定this，只有m1执行完毕的时候，m2才能执行
 * 这里时是复习synchronized最原始的语义
 * <p>
 * 使用ReentrantLock可以完成同样的功能
 * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
 * 使用synchronized锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
 *
 * 使用ReentrantLock可以进行“尝试锁定”tryLock，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 *
 * @auther lizhi
 * @time 2020-1-29 0:02
 */
public class ReentrantLock3 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock(); // synchronized(this)
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判定是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unLock的处理，必须放到finally中国
     */
    void m2() {
        /*
        boolean locked = lock.tryLock();
        System.out.println("m2 ...");
        if (locked) lock.unLock();
        */

        boolean locked = false;

        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2 ..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock3 rl = new ReentrantLock3();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }
}
