package com.elegion.androidschool.finalproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.elegion.androidschool.finalproject.MyApplication;
import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.Arrays;

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
                        .query("SELECT " + Contract.ProductEntity.TABLE_NAME + "." + Contract.ProductEntity.COLUMN_NAME + " AS " + Contract.ProductEntity.COLUMN_NAME + ", " +
                                Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity._ID + " as " + Contract.EntryEntity._ID + ", " +
                                Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUMN_PRODUCT_FK + " AS " + Contract.EntryEntity.COLUMN_PRODUCT_FK + ", " +
                                Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUM_IS_BOUGHT + " AS " + Contract.EntryEntity.COLUM_IS_BOUGHT + ", " +
                                Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUMN_LITS_FK + " AS " + Contract.EntryEntity.COLUMN_LITS_FK + ", " +
                                Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUMN_PRICE_ID + " AS " + Contract.EntryEntity.COLUMN_PRICE_ID + " " +
                                "FROM " +
                                "(SELECT * FROM " + Contract.EntryEntity.TABLE_NAME + " WHERE " + Contract.EntryEntity.COLUMN_LITS_FK + " = ?) " +
                                " AS " + Contract.EntryEntity.TABLE_NAME +
                                " JOIN " + Contract.ProductEntity.TABLE_NAME +
                                " ON " + Contract.ProductEntity.TABLE_NAME + "." + Contract.ProductEntity._ID +
                                " = " + Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUMN_PRODUCT_FK +
                                ";")
                        .args(mId)
                        .build())
                .prepare()
                .executeAsBlocking();

        Log.v(Constants.LOG_TAG, Arrays.deepToString(result.getColumnNames()));
        Log.v("qq", "Size " + result.getCount());
        return result;
    }
}
