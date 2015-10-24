package com.elegion.androidschool.finalproject;

import android.app.Application;

import com.elegion.androidschool.finalproject.db.DBOpenHelper;
import com.elegion.androidschool.finalproject.model.Entry;
import com.elegion.androidschool.finalproject.model.EntryStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.EntryStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.EntryStorIOSQLitePutResolver;
import com.elegion.androidschool.finalproject.model.Price;
import com.elegion.androidschool.finalproject.model.PriceStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.PriceStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.PriceStorIOSQLitePutResolver;
import com.elegion.androidschool.finalproject.model.Product;
import com.elegion.androidschool.finalproject.model.ProductStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.ProductStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.ProductStorIOSQLitePutResolver;
import com.elegion.androidschool.finalproject.model.ShoppingList;
import com.elegion.androidschool.finalproject.model.ShoppingListStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.ShoppingListStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.ShoppingListStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

/**
 * Created by Aleksandra on 22.10.15.
 */
public class MyApplication extends Application {

    private DBOpenHelper mDBHelper;
    private static StorIOSQLite mStorIOSQLite;

    @Override
    public void onCreate() {
        super.onCreate();

        mDBHelper = new DBOpenHelper(getApplicationContext());
        mStorIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDBHelper)
                .addTypeMapping(ShoppingList.class, SQLiteTypeMapping.<ShoppingList>builder()
                        .putResolver(new ShoppingListStorIOSQLitePutResolver())
                        .getResolver(new ShoppingListStorIOSQLiteGetResolver())
                        .deleteResolver(new ShoppingListStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Product.class, SQLiteTypeMapping.<Product>builder()
                        .putResolver(new ProductStorIOSQLitePutResolver())
                        .getResolver(new ProductStorIOSQLiteGetResolver())
                        .deleteResolver(new ProductStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Entry.class, SQLiteTypeMapping.<Entry>builder()
                        .putResolver(new EntryStorIOSQLitePutResolver())
                        .getResolver(new EntryStorIOSQLiteGetResolver())
                        .deleteResolver(new EntryStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Price.class, SQLiteTypeMapping.<Price>builder()
                        .putResolver(new PriceStorIOSQLitePutResolver())
                        .getResolver(new PriceStorIOSQLiteGetResolver())
                        .deleteResolver(new PriceStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }

    public static StorIOSQLite getStorIOSQLite() {
        return mStorIOSQLite;
    }
}
