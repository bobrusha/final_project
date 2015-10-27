package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 26.10.15.
 */
public class MarketsLoader extends AsyncTaskLoader<Cursor> {

    public MarketsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //TODO: write it more accurate
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        return MyApplication.getStorIOSQLite()
                .get()
                .cursor()
                .withQuery(
                        Query.builder()
                                .table(Contract.MarketEntity.TABLE_NAME)
                                .build()
                )
                .prepare()
                .executeAsBlocking();

    }
}
