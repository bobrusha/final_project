package com.elegion.androidschool.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.elegion.androidschool.finalproject.db.DBOpenHelper;
import com.elegion.androidschool.finalproject.model.Item;
import com.elegion.androidschool.finalproject.model.ItemStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.ItemStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.ItemStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

public class ItemsActivity extends AppCompatActivity {
    private DBOpenHelper mDBHelper;
    private StorIOSQLite mStorIOSQLite;

    private ItemsListFragment mItemsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new DBOpenHelper(getApplicationContext());
        mStorIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDBHelper)
                .addTypeMapping(Item.class, SQLiteTypeMapping.<Item>builder()
                        .putResolver(new ItemStorIOSQLitePutResolver())
                        .getResolver(new ItemStorIOSQLiteGetResolver())
                        .deleteResolver(new ItemStorIOSQLiteDeleteResolver())
                        .build())
                .build();

        setContentView(R.layout.activity_items);
        mItemsListFragment = new ItemsListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mItemsListFragment)
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

    public StorIOSQLite getStorIOSQLite() {
        return mStorIOSQLite;
    }
}
