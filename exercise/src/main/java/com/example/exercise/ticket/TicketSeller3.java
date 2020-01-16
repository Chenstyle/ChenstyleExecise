package com.example.exercise.ticket;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * Q:在对资源加锁（Vector是线程安全的），操作步骤加锁后，还存在什么问题？
 * <p>
 * A:效率太低了 会卡死（未知原因）
 *
 * @auther lizhi
 * @time 2020-1-16 23:24
 */
public class TicketSeller3 {

    static List<String> tickets = new LinkedList<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (tickets) {
                        if (tickets.size() > 0) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            System.out.println("销售了--" + tickets.remove(0));
                        }
                    }
                }
            }).start();
        }
    }
}
