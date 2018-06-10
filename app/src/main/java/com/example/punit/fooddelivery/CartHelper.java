package com.example.punit.fooddelivery;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.punit.fooddelivery.CartContract.CartEntry;
public class CartHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="cart.db";
    private static final int DATABASE_VERSION=1;

    public CartHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    public void deletedb(SQLiteDatabase db){


                db.execSQL("DROP TABLE IF EXISTS '"+CartEntry.TABLE_NAME+"'");
                onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {





        String CREATE_CART_TABLE="CREATE TABLE "+CartEntry.TABLE_NAME+"("
                +CartEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +CartEntry.CART_NAME+" TEXT, "
                +CartEntry.CART_QUANTITY+" INTEGER, "
                +CartEntry.CART_PRICE+" INTEGER);";


        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {





    }






}
