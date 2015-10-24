package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class ProductSelectedEvent {
    private long mSelectedProductId;

    public ProductSelectedEvent(long selectedProductId) {
        mSelectedProductId = selectedProductId;
    }

    public long getSelectedProductId() {
        return mSelectedProductId;
    }
}
