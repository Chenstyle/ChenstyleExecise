package com.example.exercise.container;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @auther lizhi
 * @time 2020-1-18 22:29
 */
public class T06_ArrayBlockingQueue {

    static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            strs.put("a" + i);
        }

//        strs.put("aaa"); // 满了就会等待，阻塞
//        strs.add("aaa"); // 满了会抛异常
//        strs.offer("aaa"); // 根据返回值判断有没有添加成功
        strs.offer("aaa", 1, TimeUnit.SECONDS); // 按时间段阻塞

        System.out.println(strs);
    }
}
