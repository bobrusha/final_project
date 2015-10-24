package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.event.EntrySelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;

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

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("qq", "entry was selected");
                MyBus.getInstance().post(new EntrySelectedEvent(mId));
            }
        });
    }

}
