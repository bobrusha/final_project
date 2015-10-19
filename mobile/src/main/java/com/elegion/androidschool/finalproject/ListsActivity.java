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

public class ListsActivity extends AppCompatActivity {

    private DBOpenHelper mDBHelper;
    private final ArrayList<String> mStringArrayList = new ArrayList<>();
    private ListsFragment mListsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mDBHelper = new DBOpenHelper(getApplicationContext());

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        db = mDBHelper.getReadableDatabase();
        String projection[] = {
                Contract.ListEntity._ID,
                Contract.ListEntity.COLUMN_NAME
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
}
