package com.elegion.androidschool.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.db.DBOpenHelper;
import com.elegion.androidschool.finalproject.model.Product;
import com.elegion.androidschool.finalproject.model.ProductStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.ProductStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.ProductStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

public class CreateNewProductActivity extends AppCompatActivity {

    private EditText mItemName;
    private StorIOSQLite mStorIOSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_product);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mItemName = (EditText) findViewById(R.id.create_new_item_edit_text_item_name);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_new_item);

        DBOpenHelper mDBHelper = new DBOpenHelper(getApplicationContext());
        mStorIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDBHelper)
                .addTypeMapping(Product.class, SQLiteTypeMapping.<Product>builder()
                        .putResolver(new ProductStorIOSQLitePutResolver())
                        .getResolver(new ProductStorIOSQLiteGetResolver())
                        .deleteResolver(new ProductStorIOSQLiteDeleteResolver())
                        .build())
                .build();

        mStorIOSQLite.observeChangesInTable(Contract.ProductEntity.TABLE_NAME);
    }

    public void onClick(View v) {
        Product product = new Product(mItemName.getText().toString());
        long id = mStorIOSQLite
                .get()
                .numberOfResults()
                .withQuery(Query
                        .builder()
                        .table(Contract.ProductEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        //enumeration begins with 1
        product.setId(id + 1);
        product.setDescription("qq");

        mStorIOSQLite
                .put()
                .object(product)
                .prepare()
                .executeAsBlocking();
        //TODO:show dialog about successfully creating
        finish();
    }
}
