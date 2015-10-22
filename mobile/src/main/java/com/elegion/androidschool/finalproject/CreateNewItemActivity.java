package com.elegion.androidschool.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.db.DBOpenHelper;
import com.elegion.androidschool.finalproject.model.Item;
import com.elegion.androidschool.finalproject.model.ItemStorIOSQLiteDeleteResolver;
import com.elegion.androidschool.finalproject.model.ItemStorIOSQLiteGetResolver;
import com.elegion.androidschool.finalproject.model.ItemStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

public class CreateNewItemActivity extends AppCompatActivity {

    private EditText mItemName;
    private StorIOSQLite mStorIOSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mItemName = (EditText) findViewById(R.id.create_new_item_edit_text_item_name);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_new_item);

        DBOpenHelper mDBHelper = new DBOpenHelper(getApplicationContext());
        mStorIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDBHelper)
                .addTypeMapping(Item.class, SQLiteTypeMapping.<Item>builder()
                        .putResolver(new ItemStorIOSQLitePutResolver())
                        .getResolver(new ItemStorIOSQLiteGetResolver())
                        .deleteResolver(new ItemStorIOSQLiteDeleteResolver())
                        .build())
                .build();

        mStorIOSQLite.observeChangesInTable(Contract.ItemEntity.TABLE_NAME);
    }

    public void onClick(View v) {
        Item item = new Item(mItemName.getText().toString());
        long id = mStorIOSQLite
                .get()
                .numberOfResults()
                .withQuery(Query
                        .builder()
                        .table(Contract.ItemEntity.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
        //enumeration begins with 1
        item.setId(id + 1);
        item.setDescription("qq");

        mStorIOSQLite
                .put()
                .object(item)
                .prepare()
                .executeAsBlocking();
        //TODO:show dialog about successfully creating
        finish();
    }
}
