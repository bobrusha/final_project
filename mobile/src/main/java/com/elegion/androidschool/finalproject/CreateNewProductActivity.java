package com.elegion.androidschool.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.model.Product;

public class CreateNewProductActivity extends AppCompatActivity {

    private EditText mItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_product);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mItemName = (EditText) findViewById(R.id.create_new_item_edit_text_item_name);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_new_item);

    }

    public void onClick(View v) {
        Product product = new Product(mItemName.getText().toString());
        product.setDescription("qq");

        MyApplication
                .getStorIOSQLite()
                .put()
                .object(product)
                .prepare()
                .executeAsBlocking();
        //TODO:show dialog about successfully creating
        finish();
    }
}
