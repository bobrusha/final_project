package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.R;

/**
 * Created by Aleksandra on 25.10.15.
 */
public class MarketsAdapter extends RecyclerView.Adapter<MarketViewHolder> {
    private Cursor mCursor;

    public Cursor swapCursor(@NonNull Cursor cursor) {
        final Cursor oldCursor = mCursor;
        mCursor = cursor;
        notifyDataSetChanged();
        Log.v("qq", "In MarketsAdapter cursor was swapped");
        return oldCursor;
    }

    @Override
    public MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_market, parent, false);
        return new MarketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.bindItem(mCursor);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

}
