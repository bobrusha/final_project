package com.elegion.androidschool.finalproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.model.Price;

/**
 * Created by Aleksandra on 24.10.15.
 */
public class AddPriceDialog extends DialogFragment {
    private EditText mEditTextPrice;
    private long mProductId;

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
                .setPositiveButton(R.string.add_price_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Price price = new Price(mProductId, Double.parseDouble(mEditTextPrice.getText().toString()));
                        MyApplication
                                .getStorIOSQLite()
                                .put()
                                .object(price)
                                .prepare()
                                .executeAsBlocking();
                    }
                })
                .setNegativeButton(R.string.add_price_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .create();
    }
}
