package com.example.exercise.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Chenstyle on 2020-1-19.
 * Used to WechatMainInterface.
 */
public class Watcher implements Observer {

    public Watcher(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        String data;
        if (o != null) {
            data = ((Watched) o).getData();
        } else {
            data = "not data";
        }
        System.out.println("Watched data changed: " + data);
    }
}
