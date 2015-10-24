package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class EntrySelectedEvent {
    private long mEntryId;
    private long mProductId;

    public EntrySelectedEvent(long productId, long entryId) {
        mProductId = productId;
        mEntryId = entryId;
    }

    public long getEntryId() {
        return mEntryId;
    }

    public long getProductId() {
        return mProductId;
    }

}
