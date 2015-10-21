package com.elegion.androidschool.finalproject;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.adapter.ItemsAdapter;
import com.elegion.androidschool.finalproject.loader.ItemsLoader;


public class ItemsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView mItemsListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemsAdapter mItemsAdapter;

    public ItemsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mItemsListView = (RecyclerView) view.findViewById(R.id.items_list);
        mItemsListView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mItemsListView.setLayoutManager(mLayoutManager);

        mItemsAdapter = new ItemsAdapter();
        mItemsListView.setAdapter(mItemsAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateNewItemActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.fragment_items_list, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ItemsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mItemsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
