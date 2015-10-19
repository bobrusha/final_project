package com.elegion.androidschool.finalproject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Aleksandra on 19.10.15.
 */
public class CreateNewListDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.crete_new_list_dialog_title)
                .setView(R.layout.view_add_new_list)
                .setPositiveButton(R.string.create_new_list_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: create new entry and save to db
                    }
                })
                .setNegativeButton(R.string.create_new_list_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //just quit dialog;
                    }
                });

        return builder.create();
    }
}
