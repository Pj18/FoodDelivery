package com.example.punit.fooddelivery;

import android.provider.BaseColumns;

public class FoodContract {

    private FoodContract() {}

    public static final class FoodEntry implements BaseColumns{

        public final static String TABLE_NAME="Items";
        public final static String _ID=BaseColumns._ID;
        public final static String COLUMN_NAME="name";
        public final static String COLUMN_PRICE="price";
        public final static String COLUMN_IMG="imagedata";
    }
}
