package com.elegion.androidschool.finalproject;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.event.ListSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;

import java.util.ArrayList;

/**
 * Created by Aleksandra on 13.10.15.
 */
public class ListToCardAdapter extends RecyclerView.Adapter<ListToCardAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mCardView;
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView;

            mTextView = (TextView) itemView.findViewById(R.id.card_text);
        }

        public void bind() {

        }
    }

    public ListToCardAdapter(ArrayList<String> dataset) {
        mDataset = dataset;
    }

    @Override
    public ListToCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBus.getInstance().post(new ListSelectedEvent(0));
                Log.v("qq", "List was selected");
            }
        });
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
