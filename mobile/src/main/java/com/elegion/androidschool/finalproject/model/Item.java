package com.elegion.androidschool.finalproject.model;

import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aleksandra on 08.10.15.
 */
@StorIOSQLiteType(table = Contract.ItemEntity.TABLE_NAME)
public class Item {

    @StorIOSQLiteColumn(name = Contract.ItemEntity._ID, key = true)
    long mId;

    @StorIOSQLiteColumn(name = Contract.ItemEntity.COLUMN_NAME)
    String mName;

    @StorIOSQLiteColumn(name = Contract.ItemEntity.COLUMN_DESCRIPTION)
    String mDescription;

    //must be package local
    Item() {
    }

    public Item(long id, String name, String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
