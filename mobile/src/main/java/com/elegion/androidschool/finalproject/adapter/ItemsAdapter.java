package com.elegion.androidschool.finalproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.R;

import java.util.ArrayList;

/**
 * Created by Aleksandra on 12.10.15.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public ItemsAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

