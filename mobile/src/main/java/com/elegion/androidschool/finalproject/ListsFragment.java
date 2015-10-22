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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.adapter.ListToCardAdapter;
import com.elegion.androidschool.finalproject.event.ListSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.elegion.androidschool.finalproject.loader.ListsLoader;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.squareup.otto.Subscribe;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_SELECTED_LIST_ID = "selected_list_id";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListToCardAdapter mAdapter;

    private StorIOSQLite mStorIOSQLite;
    public ListsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.lists_list);
        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListToCardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewListDialog dialog = new CreateNewListDialog();
                dialog.show(getFragmentManager(), "create_new_list");
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.fragment_lists, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ListsLoader(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
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
    public void listSelectedEventAvailable(ListSelectedEvent event) {
        Log.d("qq", "Bus transport event to target methods");
        event.getId();

        startActivity(new Intent(getActivity(), ProductsActivity.class));
    }
}
