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
    Long mId;

    @StorIOSQLiteColumn(name = Contract.EntryEntity.COLUMN_LITS_FK)
    Long mListId;

    @StorIOSQLiteColumn(name = Contract.EntryEntity.COLUMN_PRODUCT_FK)
    Long mProductId;

    @StorIOSQLiteColumn(name = Contract.EntryEntity.COLUMN_PRICE_ID)
    Long mPriceId = null;

    @StorIOSQLiteColumn(name = Contract.EntryEntity.COLUM_IS_BOUGHT)
    int mIsBought;



    // must be package-local
    Entry() {
    }

    public Entry(Long listId, Long productId) {
        mListId = listId;
        mProductId = productId;
    }

    public Long getId() {
        return mId;
    }

    public Long getListId() {
        return mListId;
    }

    public Long getProductId() {
        return mProductId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public void setListId(Long listId) {
        mListId = listId;
    }

    public void setProductId(Long productId) {
        mProductId = productId;
    }

    public Long getPriceId() {
        return mPriceId;
    }

    public int getIsBought() {
        return mIsBought;
    }
}
