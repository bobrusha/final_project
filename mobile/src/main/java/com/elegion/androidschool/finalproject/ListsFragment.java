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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.adapter.ListToCardAdapter;
import com.elegion.androidschool.finalproject.adapter.ShoppingListViewHolder;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.event.ListSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.elegion.androidschool.finalproject.loader.ListsLoader;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.squareup.otto.Subscribe;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListToCardAdapter mAdapter;
    private Toolbar mToolbar;

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

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.RIGHT, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO: snackbar
                ShoppingListViewHolder shoppingListVH = (ShoppingListViewHolder) viewHolder;
                Long listId = shoppingListVH.getShoppingListId();
                MyApplication
                        .getStorIOSQLite()
                        .delete()
                        .byQuery(
                                DeleteQuery
                                        .builder()
                                        .table(Contract.ListEntity.TABLE_NAME)
                                        .where(Contract.ListEntity._ID + " = ?")
                                        .whereArgs(listId)
                                        .build())
                        .prepare()
                        .executeAsBlocking();
                getLoaderManager().restartLoader(LoadersId.LISTS_LOADER, null, ListsFragment.this);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateNewListActivity.class));
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_main);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        DrawerArrowDrawable arrowDrawable = new DrawerArrowDrawable(getActivity());
        arrowDrawable.setSpinEnabled(true);
        actionBar.setHomeAsUpIndicator(arrowDrawable);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LoadersId.LISTS_LOADER, null, this);
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

        if (event.isLongClick()) {
            //TODO: long click
        } else {
            startActivity(new Intent(getActivity(), EntriesActivity.class)
                    .putExtra(Extras.EXTRA_LIST_ID, event.getListId())
                    .putExtra(Extras.EXTRA_LIST_NAME, event.getListName()));
        }
    }

}
