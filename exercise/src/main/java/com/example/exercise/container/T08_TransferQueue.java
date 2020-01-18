package com.example.exercise.container;

import java.util.concurrent.LinkedTransferQueue;

/**
 * 生产者生产了直接给消费者，如果找不到消费者，会阻塞
 *
 * @auther lizhi
 * @time 2020-1-18 23:16
 */
public class T08_TransferQueue {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("aaa"); // 如果使用了 transfer 就必须要进行处理，否则阻塞

        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
