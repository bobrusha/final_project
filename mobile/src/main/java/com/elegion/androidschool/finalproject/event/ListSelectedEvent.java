package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 17.10.15.
 */
public class ListSelectedEvent {
    private long mListId;
    private String mListName;
    private boolean isLongClick = false;

    public ListSelectedEvent(long listId, String listName) {
        mListId = listId;
        mListName = listName;
    }

    public ListSelectedEvent(long listId, String listName, boolean isLongClick) {
        mListId = listId;
        mListName = listName;
        this.isLongClick = isLongClick;
    }

    public long getListId() {
        return mListId;
    }

    public String getListName() {
        return mListName;
    }

    public boolean isLongClick() {
        return isLongClick;
    }
}
