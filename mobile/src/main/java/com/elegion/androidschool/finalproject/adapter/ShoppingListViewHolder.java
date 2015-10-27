package com.elegion.androidschool.finalproject.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.androidschool.finalproject.R;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.event.ListSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;

/**
 * Created by Aleksandra on 20.10.15.
 */

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
    private View mCardView;
    private TextView mTextView;
    private long mShoppingListId;

    public ShoppingListViewHolder(View itemView) {
        super(itemView);

        mCardView = itemView;
        mTextView = (TextView) itemView.findViewById(R.id.card_text);
    }

    public void bindItem(final Cursor cursor) {
        mShoppingListId = cursor.getLong(cursor.getColumnIndex(Contract.ListEntity._ID));
        mTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.ListEntity.COLUMN_NAME)));
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBus.getInstance().post(new ListSelectedEvent(mShoppingListId, mTextView.getText().toString()));
            }
        });
        mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setSelected(true);

                MyBus.getInstance().post(new ListSelectedEvent(mShoppingListId, mTextView.getText().toString(), true));
                return true;
            }
        });
    }
}