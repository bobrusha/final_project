package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 17.10.15.
 */
public class ListSelectedEvent {
    private long id;

    public ListSelectedEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
