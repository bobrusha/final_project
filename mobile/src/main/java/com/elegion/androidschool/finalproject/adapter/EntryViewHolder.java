package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.model.Entry;

/**
 * Created by Aleksandra on 22.10.15.
 */
public class EntryViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextView;
    private Entry mEntryModel;

    public EntryViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }

    public void bindItem(final Cursor cursor) {
        Log.v(Constants.LOG_TAG, "qq");
        Long entryId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity._ID));
        Long productId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUMN_PRODUCT_FK));
        Long priceId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUMN_PRICE_ID));
        int isBought = cursor.getInt(cursor.getColumnIndex(Contract.EntryEntity.TABLE_NAME + "." + Contract.EntryEntity.COLUM_IS_BOUGHT));
        Log.v(Constants.LOG_TAG, "In bind item " + entryId);
        mEntryModel = new Entry(entryId, productId, priceId, isBought);

        mTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.ProductEntity.COLUMN_NAME)), TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) mTextView.getText();
        if (isBought > 0) {
            spannable.setSpan(new StrikethroughSpan(), 0, mTextView.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("qq", "entry was selected");
                //TODO: longclick
            }
        });
    }

    public Entry getEntryModel() {
        return mEntryModel;
    }

}
