package com.elegion.androidschool.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.elegion.androidschool.finalproject.adapter.EntryAdapter;
import com.elegion.androidschool.finalproject.adapter.EntryViewHolder;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.event.EntrySelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.elegion.androidschool.finalproject.event.UpdateEntries;
import com.elegion.androidschool.finalproject.loader.EntriesLoader;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.elegion.androidschool.finalproject.loader.ProductsLoader;
import com.elegion.androidschool.finalproject.model.Entry;
import com.elegion.androidschool.finalproject.model.Product;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class EntriesActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private long mListId;

    private RecyclerView mRecyclerView;
    private EntryAdapter mEntryAdapter;
    private AutoCompleteTextView mSearchTextView;
    private ArrayAdapter<String> mSuggestionAdapter;

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
        mEntryAdapter = new EntryAdapter();
        mRecyclerView.setAdapter(mEntryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                EntryViewHolder vh = (EntryViewHolder) viewHolder;
                if (direction == ItemTouchHelper.RIGHT) {
                    if (vh.isBought() == 0) {
                        AddPriceDialog dialog = new AddPriceDialog();
                        dialog.setEntryId(vh.getEntryId());
                        dialog.setProductId(vh.getProductId());
                        dialog.setSetIsBought(vh.isBought() + 1);
                        dialog.show(getFragmentManager(), "dialog");
                    }
                    if (vh.isBought() == 1) {
                        final String updateQuery = "UPDATE " + Contract.EntryEntity.TABLE_NAME +
                                " SET " +
                                Contract.EntryEntity.COLUM_IS_BOUGHT + " = ? " +
                                "WHERE " + Contract.EntryEntity._ID + " = ?;";
                        MyApplication
                                .getStorIOSQLite()
                                .executeSQL()
                                .withQuery(
                                        RawQuery
                                                .builder()
                                                .query(updateQuery)
                                                .args(0, vh.getEntryId())
                                                .build()
                                ).prepare()
                                .executeAsBlocking();
                        getLoaderManager().restartLoader(LoadersId.ENTRY_LOADER, null, EntriesActivityFragment.this);
                    }
                }
                if (direction == ItemTouchHelper.LEFT) {
                    Entry model = vh.getEntryModel();
                    if (model.getPriceId() != null) {
                        MyApplication
                                .getStorIOSQLite()
                                .executeSQL()
                                .withQuery(RawQuery
                                                .builder()
                                                .query("DELETE FROM " + Contract.PriceEntity.TABLE_NAME + " WHERE " + Contract.PriceEntity._ID + " = ?")
                                                .args(model.getPriceId())
                                                .build()
                                ).prepare()
                                .executeAsBlocking();
                    }
                    MyApplication
                            .getStorIOSQLite()
                            .delete()
                            .object(vh.getEntryModel())
                            .prepare()
                            .executeAsBlocking();
                    getLoaderManager().restartLoader(LoadersId.ENTRY_LOADER, null, EntriesActivityFragment.this);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mSearchTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_view_find_product);
        mSuggestionAdapter = new ArrayAdapter<String>(getActivity(), R.layout.view_product);
        mSearchTextView.setAdapter(mSuggestionAdapter);

        ImageButton addButton = (ImageButton) view.findViewById(R.id.btn_add_new_entry);
        addButton.setOnClickListener(new OnSaveButtonClickListener());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListId = getActivity().getIntent().getLongExtra(Extras.EXTRA_LIST_ID, 0);
        Log.v("qq", "list id = " + mListId);
        getLoaderManager().initLoader(LoadersId.ENTRY_LOADER, null, this);
        getLoaderManager().initLoader(LoadersId.SUGGESTION_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LoadersId.ENTRY_LOADER:
                return new EntriesLoader(getActivity(), mListId);
            case LoadersId.SUGGESTION_LOADER:
                return new ProductsLoader(getActivity());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        switch (id) {
            case LoadersId.ENTRY_LOADER:
                mEntryAdapter.swapCursor(data);
                break;
            case LoadersId.SUGGESTION_LOADER:

                ArrayList<String> arr = new ArrayList<>(data.getCount());
                if (data.getCount() > 0) {
                    data.moveToFirst();

                    while (!data.isLast()) {
                        arr.add(data.getString(data.getColumnIndex(Contract.ProductEntity.COLUMN_NAME)));
                        data.moveToNext();
                    }
                    arr.add(data.getString(data.getColumnIndex(Contract.ProductEntity.COLUMN_NAME)));
                }

                mSuggestionAdapter.clear();
                mSuggestionAdapter.addAll(arr);
        }
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
    public void entryWasSelected(EntrySelectedEvent event) {
        AddPriceDialog dialog = new AddPriceDialog();
        dialog.setEntryId(event.getEntryId());
        dialog.setProductId(event.getProductId());
        dialog.show(getFragmentManager(), "dialog");
    }

    @Subscribe
    public void updateEntries(UpdateEntries event) {
        if (event.getId() == 0) {
            getLoaderManager().restartLoader(LoadersId.ENTRY_LOADER, null, EntriesActivityFragment.this);
        } else {
            mEntryAdapter.notifyDataSetChanged();
        }
    }


    public class OnSaveButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            List<Product> products = MyApplication
                    .getStorIOSQLite()
                    .get()
                    .listOfObjects(Product.class)
                    .withQuery(Query.<Product>builder()
                            .table(Contract.ProductEntity.TABLE_NAME)
                            .where(Contract.ProductEntity.COLUMN_NAME + " = ?")
                            .whereArgs(
                                    mSearchTextView
                                            .getText()
                                            .toString())
                            .build())
                    .prepare()
                    .executeAsBlocking();
            if (!products.isEmpty()) {
                Entry entry = new Entry(mListId, products.get(0).getId());
                MyApplication
                        .getStorIOSQLite()
                        .put()
                        .object(entry)
                        .prepare()
                        .executeAsBlocking();
                getLoaderManager().restartLoader(LoadersId.ENTRY_LOADER, null, EntriesActivityFragment.this);

            } else {
                startActivity(new Intent(getActivity(), CreateNewProductActivity.class)
                        .putExtra(Extras.EXTRA_PRODUCT_NAME, mSearchTextView.getText().toString()));
            }
        }
    }
}
