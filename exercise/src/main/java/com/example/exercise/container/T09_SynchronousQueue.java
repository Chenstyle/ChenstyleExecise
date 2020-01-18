package com.example.exercise.container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 没有容量的队列 容量为0
 * 添加的东西必须马上消费掉，否则会出问题
 *
 * @auther lizhi
 * @time 2020-1-18 23:26
 */
public class T09_SynchronousQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.put("aaa"); // 阻塞，等待消费者消费
//        strs.add("aaa");
        System.out.println(strs.size());
    }
}
