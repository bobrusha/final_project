package com.elegion.androidschool.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.adapter.MarketsAdapter;
import com.elegion.androidschool.finalproject.event.MarketSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.elegion.androidschool.finalproject.loader.MarketsLoader;
import com.squareup.otto.Subscribe;


public class MarketsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView mRecyclerView;
    MarketsAdapter mMarketsAdapter;
    Toolbar mToolbar;

    public MarketsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_markets, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_with_markets);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMarketsAdapter = new MarketsAdapter();
        mRecyclerView.setAdapter(mMarketsAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_market);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InfoAboutMarket.class));
            }
        });
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_markets);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity acttivity = (AppCompatActivity) getActivity();
        acttivity.setSupportActionBar(mToolbar);

        ActionBar actionBar = acttivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.markets_toolbar_title);
            DrawerArrowDrawable arrowDrawable = new DrawerArrowDrawable(getActivity());
            arrowDrawable.setSpinEnabled(true);
            actionBar.setHomeAsUpIndicator(arrowDrawable);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getLoaderManager().initLoader(LoadersId.MARKETS_LOADER, null, this);
    }


    @Override
    public void onResume() {
        super.onResume();
        MyBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyBus.getInstance().unregister(this);
    }

    @Subscribe
    public void marketWasSelected(MarketSelectedEvent event) {
        long marketId = event.getMarketId();
        Log.v(Constants.LOG_TAG, "In marketWasSelected");
        startActivity(new Intent(getActivity(), InfoAboutMarket.class)
                .putExtra(Extras.EXTRA_MARKET_ID, marketId));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MarketsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMarketsAdapter.swapCursor(data);
        Log.v("qq", "" + data.getCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
