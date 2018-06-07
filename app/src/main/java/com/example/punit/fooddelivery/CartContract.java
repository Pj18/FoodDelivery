package com.example.punit.fooddelivery;

import android.provider.BaseColumns;

public class CartContract {


    private CartContract() {}

    public static final class CartEntry implements BaseColumns{

        public final static String TABLE_NAME="cart";
        public final static String _ID=BaseColumns._ID;
        public final static String CART_NAME="name";
        public final static String CART_QUANTITY="quantity";
        public final static String CART_PRICE="price";
    }
}
