package com.elegion.androidschool.finalproject.model;

import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aleksandra on 08.10.15.
 */
@StorIOSQLiteType(table = Contract.ProductEntity.TABLE_NAME)
public class Product {

    @StorIOSQLiteColumn(name = Contract.ProductEntity._ID, key = true)
    Long mId;

    @StorIOSQLiteColumn(name = Contract.ProductEntity.COLUMN_NAME)
    String mName;

    @StorIOSQLiteColumn(name = Contract.ProductEntity.COLUMN_DESCRIPTION)
    String mDescription;

    //must be package local
    Product() {
    }

    public Product(String name) {
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
