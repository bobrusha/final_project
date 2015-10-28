package com.elegion.androidschool.finalproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.elegion.androidschool.finalproject.event.UpdateEntries;
import com.elegion.androidschool.finalproject.model.Price;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class AddPriceDialog extends DialogFragment {
    public static final String UPDATE_QUERY = "UPDATE " + Contract.EntryEntity.TABLE_NAME +
            " SET " +
            Contract.EntryEntity.COLUMN_PRICE_ID + " = ?, " +
            Contract.EntryEntity.COLUM_IS_BOUGHT + " = ? " +
            "WHERE " + Contract.EntryEntity._ID + " = ?;";

    private EditText mEditTextPrice;
    private long mProductId;
    private long mEntryId;
    private int mSetIsBought;

    public void setSetIsBought(int setIsBought) {
        mSetIsBought = setIsBought;
    }

    public void setEntryId(long entryId) {
        mEntryId = entryId;
    }

    public void setProductId(long productId) {
        mProductId = productId;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater()
                .inflate(R.layout.view_add_price, null);

        mEditTextPrice = (EditText) view.findViewById(R.id.edit_text_price);
        return builder.setTitle(R.string.add_price_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.add_price_dialog_positive_button, new PositiveButtonOnClickListener())
                .setNegativeButton(R.string.add_price_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyBus.getInstance().post(new UpdateEntries(1));
                    }
                })
                .create();
    }

    public class PositiveButtonOnClickListener implements DialogInterface.OnClickListener {
        /**
         *
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.v(Constants.LOG_TAG, "EntryId" + mEntryId);
            Cursor cursor = MyApplication.getStorIOSQLite()
                    .get()
                    .cursor()
                    .withQuery(Query.builder()
                            .table(Contract.EntryEntity.TABLE_NAME)
                            .where(Contract.EntryEntity._ID + " = ?")
                            .whereArgs(mEntryId)
                            .limit(1)
                            .build())
                    .prepare()
                    .executeAsBlocking();
            cursor.moveToFirst();

            if (cursor.getCount() > 0 &&
                    !cursor.isNull(cursor.getColumnIndex(Contract.EntryEntity.COLUMN_PRICE_ID))) {
                Long oldPriceId = cursor.getLong(cursor.getColumnIndex(Contract.EntryEntity.COLUMN_PRICE_ID));
                Log.v("qq", "Old priceId id " + oldPriceId);
                DeleteResult deleteResult = MyApplication
                        .getStorIOSQLite()
                        .delete()
                        .byQuery(DeleteQuery.builder()
                                .table(Contract.PriceEntity.TABLE_NAME)
                                .where(Contract.PriceEntity._ID + " = ?")
                                .whereArgs(oldPriceId)
                                .build())
                        .prepare()
                        .executeAsBlocking();
                Log.v("qq", "Deleted" + deleteResult.numberOfRowsDeleted());
            }
            Price price = new Price(mProductId, Double.parseDouble(mEditTextPrice.getText().toString()));
            PutResult putResult = MyApplication
                    .getStorIOSQLite()
                    .put()
                    .object(price)
                    .prepare()
                    .executeAsBlocking();
            Long insertedPriceId = putResult.insertedId();

            MyApplication
                    .getStorIOSQLite()
                    .executeSQL()
                    .withQuery(RawQuery
                            .builder()
                            .query(UPDATE_QUERY)
                            .args(insertedPriceId, mSetIsBought, mEntryId)
                            .build())
                    .prepare()
                    .executeAsBlocking();
            MyBus.getInstance().post(new UpdateEntries(0));
        }
    }
}
