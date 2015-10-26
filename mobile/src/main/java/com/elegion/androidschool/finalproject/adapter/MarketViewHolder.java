package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.event.MarketSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;

/**
 * Created by Aleksandra on 25.10.15.
 */
public class MarketViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextView;
    private long mMarketId;

    public MarketViewHolder(View itemView, long marketId) {
        super(itemView);
        mTextView = (TextView) itemView;
        mMarketId = marketId;
    }

    public void bindItem(final Cursor cursor) {
        mMarketId = cursor.getLong(cursor.getColumnIndex(Contract.MarketEntity._ID));
        mTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.MarketEntity.COLUMN_NAME)));
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBus.getInstance().post(new MarketSelectedEvent(mMarketId));
            }
        });
    }
}
