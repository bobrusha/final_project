package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 17.10.15.
 */
public class ListSelectedEvent {
    private long mListId;
    private String  mListName;

    public ListSelectedEvent(long listId, String listName) {
        mListId = listId;
        mListName = listName;
    }

    public long getListId() {
        return mListId;
    }

    public String getListName() {
        return mListName;
    }
}
