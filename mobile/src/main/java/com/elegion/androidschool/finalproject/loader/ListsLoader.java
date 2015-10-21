package com.elegion.androidschool.finalproject.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.elegion.androidschool.finalproject.ListsActivity;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 20.10.15.
 */
public class ListsLoader extends AsyncTaskLoader<Cursor> {
    private StorIOSQLite mStorIOSQLite;

    public ListsLoader(Context context) {
        super(context);
        ListsActivity listsActivity = (ListsActivity) context;
        mStorIOSQLite = listsActivity.getStorIOSQLite();
        Log.v("qq", "ListLoader was created");
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
                        .table(Contract.ListEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        Log.v("qq", "Size " + result.getCount());
        return result;
    }

}
