package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class MaxPriceLoader extends AsyncTaskLoader<Cursor> {
    public static final String QUERY = "SELECT max(" + Contract.PriceEntity.COLUMN_VALUE + ")" +
            " FROM " + Contract.PriceEntity.TABLE_NAME +
            " WHERE " + Contract.PriceEntity.COLUMN_PRODUCT_FK + " = ? ;";

    private Long mProductId;

    public MaxPriceLoader(Context context, Long productId) {
        super(context);
        mProductId = productId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //TODO: write it more accurate
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        Cursor result = MyApplication
                .getStorIOSQLite()
                .get()
                .cursor()
                .withQuery(RawQuery.builder()
                        .query(QUERY)
                        .args(mProductId)
                        .build())
                .prepare()
                .executeAsBlocking();
        return result;
    }
}
