package com.example.exercise.concurrence.e_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock用于替代synchronized
 * 本例中由于m1锁定this，只有m1执行完毕的时候，m2才能执行
 * 这里时是复习synchronized最原始的语义
 * <p>
 * 使用ReentrantLock可以完成同样的功能
 * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
 * 使用synchronized锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
 * <p>
 * 使用ReentrantLock可以进行“尝试锁定”tryLock，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 * <p>
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应
 * <p>
 * ReentrantLock还可以指定为公平锁
 *
 * @auther lizhi
 * @time 2020-1-29 0:02
 */
public class ReentrantLock5 extends Thread {

    private static ReentrantLock lock = new ReentrantLock(true); // 参数true为公平锁，请对比输出结果

    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " get lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock5 rl = new ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
    }
}
