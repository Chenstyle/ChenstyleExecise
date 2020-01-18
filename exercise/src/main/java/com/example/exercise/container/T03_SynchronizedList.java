package com.example.exercise.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @auther lizhi
 * @time 2020-1-18 21:41
 */
public class T03_SynchronizedList {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        List<String> strSync = Collections.synchronizedList(strs);
    }
}
