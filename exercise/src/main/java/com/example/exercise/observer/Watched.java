package com.example.exercise.observer;

import java.util.Observable;

/**
 * Created by Chenstyle on 2020-1-19.
 * Used to WechatMainInterface.
 */
public class Watched extends Observable {

    private String mData = "";

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        if (!mData.equals(data)) {
            mData = data;
            setChanged();
        }

        notifyObservers();
    }
}
