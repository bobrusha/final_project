package com.elegion.androidschool.finalproject.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Aleksandra on 22.10.15.
 */
public class EntriesLoader extends AsyncTaskLoader<Cursor> {
    private long mId;

    public EntriesLoader(Context context, long id) {
        super(context);
        mId = id;
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
                .withQuery(RawQuery
                        .builder()
                        .query("select * from " +
                                "(select * from entry where entry.list_id = ?) as T " +
                                "join product on product._id = T.product_id;")
                        .args(mId)
                        .build())
                .prepare()
                .executeAsBlocking();
        Log.v("qq", "Size " + result.getCount());
        return result;
    }
}
