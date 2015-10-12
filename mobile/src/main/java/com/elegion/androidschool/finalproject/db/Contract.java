package com.elegion.androidschool.finalproject.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Aleksandra on 12.10.15.
 */
public class Contract {
    public static final String CONTENT_AUTHORITY = "com.elegion.androidschool.finalproject.db";

    public static final Uri BASE_CONTENT = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ITEM = "item";

    public static class ItemEntity implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT.buildUpon().appendPath(PATH_ITEM).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
