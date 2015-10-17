package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 17.10.15.
 */
public class ListSelectedEvent {
    private int id;

    public ListSelectedEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
