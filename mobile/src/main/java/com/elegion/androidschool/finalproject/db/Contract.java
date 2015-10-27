package com.elegion.androidschool.finalproject.db;

import android.provider.BaseColumns;

/**
 * Created by Aleksandra on 12.10.15.
 */
public class Contract {

    public static class ProductEntity implements BaseColumns {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
    }

    public static class ListEntity implements BaseColumns {
        public static final String TABLE_NAME = "list";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_MARKET_ID = "market_id";
    }

    public static class EntryEntity implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_LITS_FK = "list_id";
        public static final String COLUMN_PRODUCT_FK = "product_id";
        public static final String COLUM_IS_BOUGHT = "is_bought";
        public static final String COLUMN_PRICE_ID = "price_id";
    }

    public static class PriceEntity implements BaseColumns {
        public static final String TABLE_NAME = "price";
        public static final String COLUMN_PRODUCT_FK = "poduct_id";
        public static final String COLUMN_VALUE = "value";
    }

    public static class MarketEntity implements BaseColumns {
        public static final String TABLE_NAME = "market";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }
}
