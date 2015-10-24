package com.elegion.androidschool.finalproject.model;

import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aleksandra on 24.10.15.
 */

@StorIOSQLiteType(table = Contract.PriceEntity.TABLE_NAME)
public class Price {
    @StorIOSQLiteColumn( name = Contract.PriceEntity._ID, key = true)
    Long mId = null;

    @StorIOSQLiteColumn(name = Contract.PriceEntity.COLUMN_PRODUCT_FK)
    Long mProductId;

    @StorIOSQLiteColumn(name = Contract.PriceEntity.COLUMN_VALUE)
    Double mValue;


    Price() {
    }

    public Price(Long productId, Double value) {
        mProductId = productId;
        mValue = value;
    }
}
