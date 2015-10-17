package com.elegion.androidschool.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.db.DBOpenHelper;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    private DBOpenHelper mDBHelper;

    private ItemsListFragment mItemsListFragment;
    private ArrayList<String> mStringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new DBOpenHelper(getApplicationContext());

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < 5; ++i) {
            values.put(Contract.ItemEntity.COLUMN_NAME, "" + i);
            long newRowId;
            newRowId = db.insert(
                    Contract.ItemEntity.TABLE_NAME,
                    Contract.ItemEntity.COLUMN_DESCRIPTION,
                    values);
        }
        db = mDBHelper.getReadableDatabase();
        String projection[] = {
                Contract.ItemEntity._ID,
                Contract.ItemEntity.COLUMN_NAME
        };
        Cursor c = db.query(
                Contract.ItemEntity.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        c.moveToFirst();
        int columnIndex = c.getColumnIndex(Contract.ItemEntity.COLUMN_NAME);
        for (int i = 0; i < c.getCount(); ++i) {
            mStringArrayList.add(c.getString(columnIndex));
        }

        setContentView(R.layout.activity_items);
        mItemsListFragment = ItemsListFragment.newInstance(mStringArrayList);
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
}
