package com.elegion.androidschool.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.elegion.androidschool.finalproject.db.DBOpenHelper;
import com.elegion.androidschool.finalproject.loader.ListsLoader;
import com.elegion.androidschool.finalproject.model.ShoppingList;
import com.elegion.androidschool.finalproject.model.ShoppingListStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.ShoppingListStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.ShoppingListStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import java.util.ArrayList;
import java.util.List;

public class ListsActivity extends AppCompatActivity {

    private DBOpenHelper mDBHelper;
    private StorIOSQLite mStorIOSQLite;
    private final ArrayList<String> mStringArrayList = new ArrayList<>();
    private ListsFragment mListsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mDBHelper = new DBOpenHelper(getApplicationContext());
        mStorIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDBHelper)
                .addTypeMapping(ShoppingList.class, SQLiteTypeMapping.<ShoppingList>builder()
                        .putResolver(new ShoppingListStorIOSQLitePutResolver())
                        .getResolver(new ShoppingListStorIOSQLiteGetResolver())
                        .deleteResolver(new ShoppingListStorIOSQLiteDeleteResolver())
                        .build())
                .build();

        final List<ShoppingList> shoppingLists = new ListsLoader(this).loadInBackground();

        for (ShoppingList l : shoppingLists) {
            mStringArrayList.add(l.getName());
            Log.v("qq", l.getName());
        }
        setContentView(R.layout.activity_lists);
        mListsFragment = ListsFragment.newInstance(mStringArrayList);
        getFragmentManager().beginTransaction()
                .replace(R.id.lists_container, mListsFragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public DBOpenHelper getDBHelper() {
        return mDBHelper;
    }

    public StorIOSQLite getStorIOSQLite() {
        return mStorIOSQLite;
    }
}
