package com.example.exercise.ticket;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * Q:在对资源加锁（Vector是线程安全的）后，还存在什么问题？
 * <p>
 * A:线程中的操作语句之间存在步骤差，执行到语句之间的时候资源已经被其他线程清空，
 * 导致数组越界
 * 虽然size()和remove()方法都是原子性的，但是size()+remove()就不是原子性的了
 *
 * @auther lizhi
 * @time 2020-1-16 23:24
 */
public class TicketSeller2 {

    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }
}
