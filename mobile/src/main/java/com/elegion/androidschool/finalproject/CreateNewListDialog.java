package com.elegion.androidschool.finalproject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.db.Contract;

/**
 * Created by Aleksandra on 19.10.15.
 */
public class CreateNewListDialog extends DialogFragment {
    private EditText nameField;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater()
                .inflate(R.layout.view_add_new_list, null);

        nameField = (EditText) view.findViewById(R.id.edit_text_shopping_list_name);

        builder.setTitle(R.string.crete_new_list_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.create_new_list_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListsActivity listsActivity = (ListsActivity) getActivity();
                        SQLiteDatabase db = listsActivity.getDBHelper().getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(Contract.ListEntity.COLUMN_NAME, nameField.getText().toString());
                        cv.putNull(Contract.ListEntity.COLUMN_DESCRIPTION);
                        db.insert(Contract.ListEntity.TABLE_NAME, null, cv);
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
