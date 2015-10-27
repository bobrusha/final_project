package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Aleksandra on 27.10.15.
 */
public class GeofencesLoader extends AsyncTaskLoader<Cursor> {
    private static final String QUERY = "SELECT * FROM " + Contract.ListEntity.TABLE_NAME +
            " LEFT JOIN " + Contract.MarketEntity.TABLE_NAME +
            " ON " + Contract.ListEntity.TABLE_NAME + "." + Contract.ListEntity.COLUMN_MARKET_ID +
            " = " + Contract.MarketEntity.TABLE_NAME + "." + Contract.MarketEntity._ID +
            " ;";

    public GeofencesLoader(Context context) {
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
        Cursor result = MyApplication
                .getStorIOSQLite()
                .get()
                .cursor()
                .withQuery(
                        RawQuery
                                .builder()
                                .query(QUERY)
                                .build()
                )
                .prepare()
                .executeAsBlocking();
        Log.v(Constants.LOG_TAG, "Loaded " + result.getCount());
        return result;
    }
}
