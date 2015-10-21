package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.R;

/**
 * Created by Aleksandra on 13.10.15.
 */
public class ListToCardAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {
    private Cursor mCursor;

    public Cursor swapCursor(@NonNull Cursor cursor) {
        final Cursor oldCursor = mCursor;
        mCursor = cursor;
        notifyDataSetChanged();
        Log.v("qq", "Cursor was swapped");
        return oldCursor;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list, parent, false);

        // TODO: set the view's size, margins, paddings and layout parameters
        return new ShoppingListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.bindItem(mCursor);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

}
