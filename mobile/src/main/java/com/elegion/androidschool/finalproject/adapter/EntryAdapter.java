package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.R;

/**
 * Created by Aleksandra on 22.10.15.
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryViewHolder> {
    private Cursor mCursor;

    public Cursor swapCursor(@NonNull Cursor cursor) {
        final Cursor oldCursor = mCursor;
        mCursor = cursor;
        notifyDataSetChanged();
        Log.v("qq", "In EntryAdapter cursor was swapped");
        return oldCursor;
    }
    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_entry, parent, false);
        return new EntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.bindItem(mCursor);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

}
