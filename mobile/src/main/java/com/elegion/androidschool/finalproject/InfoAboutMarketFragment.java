package com.elegion.androidschool.finalproject;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.loader.InfoAboutMarketLoader;
import com.elegion.androidschool.finalproject.model.Market;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoAboutMarketFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Market mMarketModel;

    private EditText mMarketNameEditText;

    public InfoAboutMarketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_about_market, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMarketNameEditText = (EditText) view.findViewById(R.id.edit_text_market_name);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long marketId = getActivity().getIntent().getLongExtra(Extras.EXTRA_MARKET_ID, -1);
        if (marketId > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong(Extras.EXTRA_MARKET_ID, marketId);
            getLoaderManager().initLoader(LoadersId.INFO_ABOUT_MARKET_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new InfoAboutMarketLoader(getActivity(), args.getLong(Extras.EXTRA_MARKET_ID));

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            long marketId = data.getLong(data.getColumnIndex(Contract.MarketEntity._ID));
            String marketName = data.getString(data.getColumnIndex(Contract.MarketEntity.COLUMN_NAME));
            double latitude = data.getDouble(data.getColumnIndex(Contract.MarketEntity.COLUMN_LATITUDE));
            double longitude = data.getDouble(data.getColumnIndex(Contract.MarketEntity.COLUMN_LONGITUDE));
            mMarketModel = new Market(marketId, marketName, latitude, longitude);
            setData();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setData() {
        mMarketNameEditText.setText(mMarketModel.getName());
    }

}
