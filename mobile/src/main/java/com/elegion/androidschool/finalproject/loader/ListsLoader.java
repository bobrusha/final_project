package com.elegion.androidschool.finalproject.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.elegion.androidschool.finalproject.ListsActivity;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.model.ShoppingList;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

/**
 * Created by Aleksandra on 20.10.15.
 */
public class ListsLoader extends AsyncTaskLoader<List<ShoppingList>> {
    private StorIOSQLite mStorIOSQLite;
    public ListsLoader(Context context) {
        super(context);
        ListsActivity listsActivity = (ListsActivity) context;
        mStorIOSQLite = listsActivity.getStorIOSQLite();
    }

    @Override
    public List<ShoppingList> loadInBackground() {
        List<ShoppingList> result = mStorIOSQLite.get()
                .listOfObjects(ShoppingList.class)
                .withQuery(Query.builder()
                        .table(Contract.ListEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        Log.v("qq", "Size " + result.size());
        return result;
    }
}
