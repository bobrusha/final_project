package com.elegion.androidschool.finalproject.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.elegion.androidschool.finalproject.ItemsActivity;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 21.10.15.
 */
public class ItemsLoader extends AsyncTaskLoader<Cursor> {
    private StorIOSQLite mStorIOSQLite;

    public ItemsLoader(Context context) {
        super(context);
        ItemsActivity itemsActivity = (ItemsActivity) context;
        mStorIOSQLite = itemsActivity.getStorIOSQLite();
        Log.v("qq", "ItemsLoader was created");
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
                        .table(Contract.ItemEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        Log.v("qq", "Size " + result.getCount());
        return result;

    }
}
