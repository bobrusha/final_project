package com.elegion.androidschool.finalproject.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 21.10.15.
 */
public class ProductsLoader extends AsyncTaskLoader<Cursor> {
    public ProductsLoader(Context context) {
        super(context);
        Log.v("qq", "ProductsLoader was created");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //TODO: write it more accurate
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        Cursor result = MyApplication.getStorIOSQLite().get()
                .cursor()
                .withQuery(Query.builder()
                        .table(Contract.ProductEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        Log.v("qq", "Size " + result.getCount());
        return result;

    }
}
