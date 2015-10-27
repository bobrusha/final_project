package com.elegion.androidschool.finalproject;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.elegion.androidschool.finalproject.loader.MarketsLoader;

/**
 * Created by Aleksandra on 27.10.15.
 */
public class CreateNewListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ArrayAdapter<String> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_add_new_list);

        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.markets_spinner);
        mSpinnerAdapter = new ArrayAdapter<>(this, R.layout.view_market);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSpinnerAdapter);

        getSupportLoaderManager().initLoader(LoadersId.MARKETS_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MarketsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(Constants.LOG_TAG, "Loaded " + data.getCount() + " market");
        data.moveToFirst();
        if (data.getCount() == 0) {

            return;
        }
        while (!data.isAfterLast()) {
            Log.v(Constants.LOG_TAG, "" + data.getString(data.getColumnIndex(Contract.MarketEntity.COLUMN_NAME)));
            mSpinnerAdapter.add(data.getString(data.getColumnIndex(Contract.MarketEntity.COLUMN_NAME)));
            data.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
