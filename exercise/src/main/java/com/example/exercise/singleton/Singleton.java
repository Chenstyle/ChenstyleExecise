package com.example.exercise.singleton;

import java.util.Arrays;

/**
 * 线程安全的单例模式
 * <p>
 * 阅读文章：https://www.cnblogs.com/xudong-bupt/p/3433643.html
 * <p>
 * 更好的是采用下面的方式，既不用加锁，也能实现懒加载
 * Created by lizhi for WechatFrame
 * at the 2020-1-16 23:02
 */
public class Singleton {
    private Singleton() {
        System.out.println("single");
    }

    public static Singleton getSingle() {
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(Singleton::getSingle);
        }

        Arrays.asList(ths).forEach(Thread::start);
    }

    private static class Inner {
        private static Singleton s = new Singleton();
    }
}
