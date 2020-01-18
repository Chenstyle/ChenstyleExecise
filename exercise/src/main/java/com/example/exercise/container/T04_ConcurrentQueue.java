package com.example.exercise.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @auther lizhi
 * @time 2020-1-18 21:45
 */
public class T04_ConcurrentQueue {
    public static void main(String[] args) {
        Queue<String> strs = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            // 如果是Array，长度会有限制。用add方法当无法添加的时候会抛异常。
            // 用 offer会有一个boolean返回值，可以通过这个返回值确定是否添加成功
            strs.offer("a" + i);
        }

        System.out.println(strs);
        System.out.println(strs.size());
        // poll的意思是从顶部拿走一个元素，同时集合内的这个会删除
        System.out.println(strs.poll());
        System.out.println(strs.size());
        // peek的意思是仅拿出来，不会删除（显示顶部的元素）
        System.out.println(strs.peek());
        System.out.println(strs.size());
    }
}
