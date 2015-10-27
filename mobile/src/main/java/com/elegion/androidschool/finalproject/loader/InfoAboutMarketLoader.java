package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.model.Market;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 26.10.15.
 */
public class InfoAboutMarketLoader extends AsyncTaskLoader<Cursor> {
    Long mMarketId;

    public InfoAboutMarketLoader(Context context, long marketId) {
        super(context);
        mMarketId = marketId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //TODO: write it more accurate
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        Log.v(Constants.LOG_TAG, "MarketId" + mMarketId );
        return MyApplication.getStorIOSQLite()
                .get()
                .cursor()
                .withQuery(Query.<Market>builder()
                        .table(Contract.MarketEntity.TABLE_NAME)
                        .where(Contract.MarketEntity._ID + " = ?")
                        .whereArgs(mMarketId).build())
                .prepare()
                .executeAsBlocking();
    }
}
