package com.elegion.androidschool.finalproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aleksandra on 11.10.15.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping_list.db";
    private static final int DB_VERSION = 2;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " + Contract.ListEntity.TABLE_NAME + " (" +
                Contract.ListEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.ListEntity.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                Contract.ListEntity.COLUMN_DESCRIPTION + " TEXT " +
                " );";

        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + Contract.ProductEntity.TABLE_NAME + " (" +
                Contract.ProductEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.ProductEntity.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                Contract.ProductEntity.COLUMN_DESCRIPTION + " TEXT, " +
                Contract.ProductEntity.COLUMN_LIST_ID + " INTEGER REFERENCES list" +
                " );";

        db.execSQL(SQL_CREATE_LIST_TABLE);
        db.execSQL(SQL_CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.ProductEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.ListEntity.TABLE_NAME);

        onCreate(db);
    }
}
