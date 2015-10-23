package com.elegion.androidschool.finalproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.elegion.androidschool.finalproject.db.Contract;

public class ListsActivity extends AppCompatActivity {
    public static final String EXTRA_QUERY = "query";
    public static final String EXTRA_ARG = "arg";
    private static final String mQuery = "SELECT * FROM " + Contract.ProductEntity.TABLE_NAME +
            " WHERE " + Contract.ProductEntity.COLUMN_NAME + " LIKE ? ; ";

    private ListsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mFragment = new ListsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.qq, mFragment)
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

    public ListsFragment getFragment() {
        return mFragment;
    }
}
