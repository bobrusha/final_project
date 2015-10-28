package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 28.10.15.
 */
public class UpdateEntries {
    private int mId;

    public UpdateEntries(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
