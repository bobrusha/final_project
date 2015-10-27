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

    public Market(String name) {
        mName = name;
    }

    public Market(Long id, String name, double latitude, double longitude) {
        mId = id;
        mName = name;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}
