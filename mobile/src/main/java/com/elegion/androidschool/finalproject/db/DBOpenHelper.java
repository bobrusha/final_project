package com.elegion.androidschool.finalproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aleksandra on 11.10.15.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping_list.db";
    private static final int DB_VERSION = 7;

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

        final String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + Contract.ProductEntity.TABLE_NAME + " (" +
                Contract.ProductEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.ProductEntity.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                Contract.ProductEntity.COLUMN_DESCRIPTION + " TEXT" +
                " );";


        final String SQL_CREATE_ENTRY_TABLE = "CREATE TABLE " + Contract.EntryEntity.TABLE_NAME + " (" +
                Contract.EntryEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.EntryEntity.COLUMN_LITS_FK + " INTEGER REFERENCES " +
                Contract.ListEntity.TABLE_NAME + ", " +
                Contract.EntryEntity.COLUMN_PRODUCT_FK + " INTEGER REFERENCES " +
                Contract.ProductEntity.TABLE_NAME + ", " +
                Contract.EntryEntity.COLUMN_PRICE_ID + " INTEGER REFERENCES " +
                Contract.PriceEntity.TABLE_NAME + ", " +
                Contract.EntryEntity.COLUM_IS_BOUGHT + " INTEGER " +
                " );";

        final String SQL_CREATE_PRICE_TABLE = "CREATE TABLE " + Contract.PriceEntity.TABLE_NAME + " (" +
                Contract.PriceEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.PriceEntity.COLUMN_PRODUCT_FK+ " INTEGER REFERENCES " +
                Contract.ProductEntity.TABLE_NAME + ", " +
                Contract.PriceEntity.COLUMN_VALUE + " REAL " +
                " );";

        final String SQL_CREATE_MARKET_TABLE = "CREATE TABLE " + Contract.MarketEntity.TABLE_NAME + " (" +
                Contract.MarketEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.MarketEntity.COLUMN_NAME + "TEXT NOT NULL" +
                " );";


        db.execSQL(SQL_CREATE_LIST_TABLE);
        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
        db.execSQL(SQL_CREATE_ENTRY_TABLE);
        db.execSQL(SQL_CREATE_PRICE_TABLE);
        db.execSQL(SQL_CREATE_MARKET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String dropTable = "DROP TABLE IF EXISTS ";

        db.execSQL(dropTable + Contract.PriceEntity.TABLE_NAME);
        db.execSQL(dropTable + Contract.EntryEntity.TABLE_NAME);
        db.execSQL(dropTable + Contract.ProductEntity.TABLE_NAME);
        db.execSQL(dropTable + Contract.ListEntity.TABLE_NAME);
        db.execSQL(dropTable + Contract.MarketEntity.TABLE_NAME);

        onCreate(db);
    }
}
