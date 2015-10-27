package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.graphics.Paint;
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
    private long mEntryId;

    public EntryViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }

    public void bindItem(final Cursor cursor) {
        mEntryId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity._ID));
        mTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.ProductEntity.COLUMN_NAME)));
        if (cursor.getInt(cursor.getColumnIndex(Contract.EntryEntity.COLUM_IS_BOUGHT)) > 0) {
            mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("qq", "entry was selected");
                long mProductId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity.COLUMN_PRODUCT_FK));
                MyBus.getInstance().post(new EntrySelectedEvent(mEntryId, mProductId));
            }
        });
    }
}
