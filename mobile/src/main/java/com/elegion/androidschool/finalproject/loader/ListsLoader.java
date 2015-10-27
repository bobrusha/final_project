package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.model.ShoppingList;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Aleksandra on 20.10.15.
 */
public class ListsLoader extends AsyncTaskLoader<Cursor> {

    public ListsLoader(Context context) {
        super(context);
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
        Cursor result = MyApplication.getStorIOSQLite().get()
                .cursor()
                .withQuery(Query.<ShoppingList>builder()
                        .table(Contract.ListEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        Log.v("qq", "Size " + result.getCount());
        return result;
    }

}
