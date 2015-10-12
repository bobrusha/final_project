package com.elegion.androidschool.finalproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aleksandra on 11.10.15.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping_list.db";
    private static final int DB_VERSION = 1;

    //TODO: replace null to DB_NAME
    public DBOpenHelper(Context context) {
        super(context, null, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + Contract.ItemEntity.TABLE_NAME + " (" +
                Contract.ItemEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.ItemEntity.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                Contract.ItemEntity.COLUMN_DESCRIPTION + " TEXT " +
                " );";

        db.execSQL(SQL_CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.ItemEntity.TABLE_NAME);

        onCreate(db);
    }
}
