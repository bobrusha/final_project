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

import com.elegion.androidschool.finalproject.adapter.EntryAdapter;
import com.elegion.androidschool.finalproject.loader.EntriesLoader;


/**
 * A placeholder fragment containing a simple view.
 */
public class EntriesActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private long mListId;

    private RecyclerView mRecyclerView;
    private EntryAdapter mAdapter;

    public EntriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("qq", "onCreatedView EntriesActivity");
        return inflater.inflate(R.layout.fragment_entries, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list_entry);
        mAdapter = new EntryAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_product_to_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SelectProductActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListId = getActivity().getIntent().getLongExtra(ListsFragment.EXTRA_SELECTED_LIST_ID, 0);
        Log.v("qq", "list id = " + mListId);
        getLoaderManager().initLoader(R.id.fragment_entries, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new EntriesLoader(getActivity(), mListId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
