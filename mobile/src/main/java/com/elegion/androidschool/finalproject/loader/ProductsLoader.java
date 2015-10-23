package com.elegion.androidschool.finalproject.loader;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.model.Product;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Aleksandra on 21.10.15.
 */
public class ProductsLoader extends AsyncTaskLoader<Cursor> {
    private String mRawQuery = null;
    private String mProductName = null;

    public ProductsLoader(Context context) {
        super(context);
        Log.v("qq", "ProductsLoader was created");
    }

    public ProductsLoader(Context context, String rawQuery, String productName) {
        super(context);
        mRawQuery = rawQuery;
        mProductName = productName;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //TODO: write it more accurate
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        Cursor result;
        if (mRawQuery != null && !mRawQuery.isEmpty()) {
            result = MyApplication.getStorIOSQLite()
                    .get()
                    .cursor()
                    .withQuery(RawQuery.<Product>builder()
                            .query(mRawQuery)
                            .args(mProductName)
                            .build())
                    .prepare().executeAsBlocking();
        } else {
            result = MyApplication.getStorIOSQLite().get()
                    .cursor()
                    .withQuery(Query.builder()
                            .table(Contract.ProductEntity.TABLE_NAME)
                            .build())
                    .prepare()
                    .executeAsBlocking();
        }
        Log.v("qq", "Size " + result.getCount());
        return result;

    }
}
