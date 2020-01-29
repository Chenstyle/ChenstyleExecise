package com.example.exercise.concurrence.e_021;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：写一个固定容量的同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * <p>
 * 使用wait和notify/notifyAll来实现
 *
 * @auther lizhi
 * @time 2020-1-29 22:35
 */
public class MyContainer1<T> {
    final private LinkedList<T> list = new LinkedList<T>();
    final private int MAX = 10;
    private int count = 0;

    public static void main(String[] args) {
        MyContainer1 c = new MyContainer1();
        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 5; i1++) {
                    System.out.println(c.get());
                }
            }, "c " + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 25; i1++) {
                    c.put(Thread.currentThread().getName() + " " + i1);
                }
            }, "p " + i).start();
        }
    }

    public synchronized void put(T t) {
        while (list.size() == MAX) { // 想想为什么用while而不是if
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(t);
        ++count;
        this.notifyAll(); // 通知消费者线程进行消费
    }

    public synchronized T get() {
        T t = null;
        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.removeFirst();
        count--;
        this.notifyAll(); // 通知生产者线程进行生产
        return t;
    }

    public int getCount() {
        return count;
    }
}
