package com.elegion.androidschool.finalproject.model;

import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aleksandra on 20.10.15.
 */

@StorIOSQLiteType(table = Contract.ListEntity.TABLE_NAME)
public class ShoppingList {

    @StorIOSQLiteColumn(name = Contract.ListEntity._ID, key = true)
    long mId;

    @StorIOSQLiteColumn(name = Contract.ListEntity.COLUMN_NAME)
    String mName;

    @StorIOSQLiteColumn(name = Contract.ListEntity.COLUMN_DESCRIPTION)
    String mDescription;

    //must be package-level
    ShoppingList() {
    }

    public ShoppingList(String name) {
        mName = name;
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
