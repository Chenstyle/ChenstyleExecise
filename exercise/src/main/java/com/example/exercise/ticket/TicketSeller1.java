package com.example.exercise.ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * Q:分析下面的程序可能会产生哪些问题？
 * 重复销售？超量销售？
 * <p>
 * A:没有对资源加锁，会出现线程抢占资源，导致重复出票，漏票不出.
 * 甚至导致数组越界（线程速度比代码速度快）
 *
 * @auther lizhi
 * @time 2020-1-16 23:24
 */
public class TicketSeller1 {

    static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }
}
