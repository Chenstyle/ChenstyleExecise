package com.example.exercise;

import com.example.exercise.observer.Watched;
import com.example.exercise.observer.Watcher;

import java.util.Observer;

public class MyClass {
    public static void main(String[] args) {
        Watched watched = new Watched();
        Observer watcher = new Watcher(watched);

        watched.setData("start");
        watched.setData("run");
        watched.setData("stop");
    }
}
