package com.elegion.androidschool.finalproject.event;

import com.squareup.otto.Bus;

/**
 * Created by Aleksandra on 17.10.15.
 */
public class MyBus {
    private static Bus ourInstance = new Bus();

    public static Bus getInstance() {
        return ourInstance;
    }

    private MyBus() {
    }
}
