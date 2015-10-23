package com.elegion.androidschool.finalproject;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.elegion.androidschool.finalproject.db.Contract;

public class SelectProductActivity extends AppCompatActivity {
    public static final String EXTRA_QUERY = "query";
    public static final String EXTRA_ARG = "arg";

    private final String mQuery = "SELECT * FROM " + Contract.ProductEntity.TABLE_NAME +
            " WHERE " + Contract.ProductEntity.COLUMN_NAME + " LIKE ? ; ";

    private SelectProductActivityFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFragment = (SelectProductActivityFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_select_product);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v("qq", "new intent was received");
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String arg = intent.getStringExtra(SearchManager.QUERY);
            Log.v("qq" , arg);
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_QUERY, mQuery);
            bundle.putString(EXTRA_ARG, arg);
            mFragment.getLoaderManager().restartLoader(R.id.fragment_select_product, bundle, mFragment);
        }
    }
}
