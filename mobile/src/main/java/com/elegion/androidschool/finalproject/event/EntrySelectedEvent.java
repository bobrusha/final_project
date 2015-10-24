package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class EntrySelectedEvent {
    private long id;

    public EntrySelectedEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
