package com.elegion.androidschool.finalproject;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.elegion.androidschool.finalproject.loader.MarketsLoader;
import com.elegion.androidschool.finalproject.model.Market;
import com.elegion.androidschool.finalproject.model.ShoppingList;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

/**
 * Created by Aleksandra on 27.10.15.
 */
public class CreateNewListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mNameListEditText;
    private AppCompatSpinner mSpinner;
    private ArrayAdapter<Market> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_add_new_list);

        mNameListEditText = (EditText) findViewById(R.id.edit_text_shopping_list_name);
        mSpinner = (AppCompatSpinner) findViewById(R.id.markets_spinner);
        mSpinnerAdapter = new ArrayAdapter<>(this, R.layout.view_market);

        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);

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
        String marketName;
        long marketId;
        while (!data.isAfterLast()) {
            Log.v(Constants.LOG_TAG, "" + data.getString(data.getColumnIndex(Contract.MarketEntity.COLUMN_NAME)));
            marketId = data.getLong(data.getColumnIndex(Contract.MarketEntity._ID));
            marketName = data.getString(data.getColumnIndex(Contract.MarketEntity.COLUMN_NAME));
            mSpinnerAdapter.add(new Market(marketId, marketName, 0, 0));
            data.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void onFABClick(View view) {
        String name = mNameListEditText.getText().toString();
        Market market = (Market) mSpinner.getSelectedItem();
        if (market != null) {
            ShoppingList list = new ShoppingList(name);
            list.setMarketId(market.getId());
            PutResult putResult = MyApplication
                    .getStorIOSQLite()
                    .put()
                    .object(list)
                    .prepare()
                    .executeAsBlocking();
            if (putResult.wasInserted() || putResult.wasUpdated()) {
                onBackPressed();
            } else {
                Log.v(Constants.LOG_TAG, "Wasn't inserted");
            }
        }
    }
}
