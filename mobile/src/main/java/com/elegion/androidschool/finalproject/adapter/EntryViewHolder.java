package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.db.Contract;

/**
 * Created by Aleksandra on 22.10.15.
 */
public class EntryViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextView;
    private long mId;

    public EntryViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }

    public void bindItem(final Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity._ID));
        mTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.EntryEntity.COLUMN_PRODUCT_FK)));
    }

}
