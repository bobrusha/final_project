package com.elegion.androidschool.finalproject;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elegion.androidschool.finalproject.adapter.ProductsAdapter;
import com.elegion.androidschool.finalproject.loader.ProductsLoader;


/**
 * A placeholder fragment containing a simple view.
 */
public class SelectProductActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRecyclerView;
    private ProductsAdapter mAdapter;

    public SelectProductActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.items_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ProductsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Button searchBtn = (Button) view.findViewById(R.id.btn_start_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onSearchRequested();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.fragment_items_list, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Log.v("qq", "Loader in SelectProductActivity was created");

        if (bundle != null) {
            final String query = bundle.getString(SelectProductActivity.EXTRA_QUERY);
            final String args = bundle.getString(SelectProductActivity.EXTRA_QUERY);
            return new ProductsLoader(getActivity(), query, args);
        }
        else {
            return new ProductsLoader(getActivity());
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
