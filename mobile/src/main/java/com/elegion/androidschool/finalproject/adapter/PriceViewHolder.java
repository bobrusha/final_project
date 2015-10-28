package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.db.Contract;

/**
 * Created by Aleksandra on 28.10.15.
 */
public class PriceViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextView;

    public PriceViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }

    public void bindItem(Cursor cursor) {
        mTextView.setText("" + cursor.getDouble(cursor.getColumnIndex(Contract.PriceEntity.COLUMN_VALUE)));
    }
}
