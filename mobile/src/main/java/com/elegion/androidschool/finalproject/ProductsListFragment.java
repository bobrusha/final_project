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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.adapter.ProductsAdapter;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.elegion.androidschool.finalproject.event.ProductSelectedEvent;
import com.elegion.androidschool.finalproject.loader.ProductsLoader;
import com.squareup.otto.Subscribe;


public class ProductsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView mItemsListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProductsAdapter mProductsAdapter;
    private Toolbar mToolbar;

    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mItemsListView = (RecyclerView) view.findViewById(R.id.items_list);
        mItemsListView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mItemsListView.setLayoutManager(mLayoutManager);

        mProductsAdapter = new ProductsAdapter();
        mItemsListView.setAdapter(mProductsAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateNewProductActivity.class));
            }
        });
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_products);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.products_toolbar_title);
            DrawerArrowDrawable arrowDrawable = new DrawerArrowDrawable(getActivity());
            arrowDrawable.setSpinEnabled(true);
            actionBar.setHomeAsUpIndicator(arrowDrawable);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getLoaderManager().initLoader(R.id.fragment_items_list, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ProductsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProductsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
    public void productWasSelected(ProductSelectedEvent event) {
        startActivity(
                new Intent(getActivity(), ProductStatisticsActivity.class)
                        .putExtra(Extras.EXTRA_PRODUCT_ID, event.getSelectedProductId())
        );
    }
}
