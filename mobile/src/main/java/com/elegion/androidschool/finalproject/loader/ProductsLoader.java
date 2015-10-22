package com.elegion.androidschool.finalproject.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.elegion.androidschool.finalproject.ProductsActivity;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 21.10.15.
 */
public class ProductsLoader extends AsyncTaskLoader<Cursor> {
    private StorIOSQLite mStorIOSQLite;

    public ProductsLoader(Context context) {
        super(context);
        ProductsActivity productsActivity = (ProductsActivity) context;
        mStorIOSQLite = productsActivity.getStorIOSQLite();
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
        Cursor result = mStorIOSQLite.get()
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
