package com.elegion.androidschool.finalproject;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.adapter.PricesAdapter;
import com.elegion.androidschool.finalproject.loader.AllPricesForCurrentProductLoader;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.elegion.androidschool.finalproject.loader.MaxPriceLoader;
import com.elegion.androidschool.finalproject.loader.MinPriceLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductStatisticsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Long mSelectedProductId;

    private TextView mMaxPriceTextView;
    private TextView mMinPriceTextView;
    private RecyclerView mRecyclerView;
    private PricesAdapter mPricesAdapter;


    public ProductStatisticsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMaxPriceTextView = (TextView) view.findViewById(R.id.text_view_max_price);
        mMinPriceTextView = (TextView) view.findViewById(R.id.text_view_min_price);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.prices_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPricesAdapter = new PricesAdapter();
        mRecyclerView.setAdapter(mPricesAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSelectedProductId = getActivity().getIntent().getLongExtra(Extras.EXTRA_PRODUCT_ID, 0);
        getLoaderManager().initLoader(LoadersId.ALL_PRICES_LOADER, null, this);
        getLoaderManager().initLoader(LoadersId.MAX_PRICE_LOADER, null, this);
        getLoaderManager().initLoader(LoadersId.MIN_PRICE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v("qq", "onCreateLoader");
        switch (id) {
            case LoadersId.ALL_PRICES_LOADER:
                return new AllPricesForCurrentProductLoader(getActivity(), mSelectedProductId);
            case LoadersId.MAX_PRICE_LOADER:
                return new MaxPriceLoader(getActivity(), mSelectedProductId);
            case LoadersId.MIN_PRICE_LOADER:
                return new MinPriceLoader(getActivity(), mSelectedProductId);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            Log.v("qq", "cursor is empty");
            return;
        }
        data.moveToFirst();
        int id = loader.getId();
        switch (id) {
            case LoadersId.ALL_PRICES_LOADER:
                mPricesAdapter.swapCursor(data);
                return;
            case LoadersId.MAX_PRICE_LOADER:
                String v = String.valueOf(data.getDouble(data.getColumnIndex(MaxPriceLoader.MAX_PRICE)));
                Log.v("qq", v);
                mMaxPriceTextView.setText(v);
                return;
            case LoadersId.MIN_PRICE_LOADER:
                mMinPriceTextView.setText(
                        String.valueOf(
                                data.getDouble(data.getColumnIndex(MinPriceLoader.MIN_PRICE))
                        )
                );
                return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
