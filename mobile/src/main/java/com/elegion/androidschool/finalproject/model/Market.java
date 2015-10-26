package com.elegion.androidschool.finalproject.model;

import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aleksandra on 26.10.15.
 */
@StorIOSQLiteType(table = Contract.MarketEntity.TABLE_NAME)
public class Market {
    @StorIOSQLiteColumn(name = Contract.MarketEntity._ID, key = true)
    Long mId;

    @StorIOSQLiteColumn(name = Contract.MarketEntity.COLUMN_NAME)
    String mName;

    @StorIOSQLiteColumn(name = Contract.MarketEntity.COLUMN_LATITUDE)
    double mLatitude;

    @StorIOSQLiteColumn(name = Contract.MarketEntity.COLUMN_LONGITUDE)
    double mLongitude;

    // default constructor must be package local
    Market() {
    }
}
