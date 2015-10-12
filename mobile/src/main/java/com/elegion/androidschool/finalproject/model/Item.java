package com.elegion.androidschool.finalproject.model;

/**
 * Created by Aleksandra on 08.10.15.
 */

public class Item {
    String mName;

    String mNote;

    public Item() {
    }

    public Item(String name, String note) {
        mName = name;
        mNote = note;
    }

    public String getName() {
        return mName;
    }

    public String getNote() {
        return mNote;
    }
}
