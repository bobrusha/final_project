package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.model.Price;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class AllPricesForCurrentProductLoader extends AsyncTaskLoader<Cursor> {

    private Long mProductId;

    public AllPricesForCurrentProductLoader(Context context, Long productId) {
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
                .withQuery(Query.<Price>builder()
                        .table(Contract.PriceEntity.TABLE_NAME)
                        .where(Contract.PriceEntity.COLUMN_PRODUCT_FK + " = ?")
                        .whereArgs(mProductId)
                        .build())
                .prepare()
                .executeAsBlocking();
        return result;
    }
}
