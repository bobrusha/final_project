package com.elegion.androidschool.finalproject.model;

import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aleksandra on 22.10.15.
 */

@StorIOSQLiteType(table = Contract.EntryEntity.TABLE_NAME)
public class Entry {

    @StorIOSQLiteColumn(name = Contract.EntryEntity._ID, key = true)
    long mId;

    @StorIOSQLiteColumn(name = Contract.EntryEntity.COLUMN_LITS_FK)
    long mListId;

    @StorIOSQLiteColumn(name = Contract.EntryEntity.COLUMN_PRODUCT_FK)
    long mProductId;

    // must be package-local
    Entry() {
    }

    public long getId() {
        return mId;
    }

    public long getListId() {
        return mListId;
    }

    public long getProductId() {
        return mProductId;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setListId(long listId) {
        mListId = listId;
    }

    public void setProductId(long productId) {
        mProductId = productId;
    }
}
