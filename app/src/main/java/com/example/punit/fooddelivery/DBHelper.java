package com.example.punit.fooddelivery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.punit.fooddelivery.FoodContract;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="item.db";
    private static final int DATABASE_VERSION=1;

    public DBHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ITEM_TABLE="CREATE TABLE "+ FoodContract.FoodEntry.TABLE_NAME+"("
                +FoodContract.FoodEntry.COLUMN_NAME+" TEXT, "
                + FoodContract.FoodEntry.COLUMN_PRICE+" TEXT, "
                + FoodContract.FoodEntry.COLUMN_IMG+" TEXT);";

        db.execSQL(CREATE_ITEM_TABLE);

        String ADD_ITEM="INSERT INTO Items VALUES(\"Veg Burger\",\"40\",\"ap\"), (\"Cheese Burger\",\"50\",\"ap\"), (\"Italian Pizza\",\"70\",\"ap\"), (\"Mexican Burger\",\"50\",\"ap\"), " +
                "(\"Chinese Burger\",\"60\",\"ap\"), (\"Margerita\",\"80\",\"ap\"), (\"Hakka Noodles\",\"80\",\"ap\"), (\"Dry Manchurian\",\"80\",\"ap\"), (\"Gravy Manchurian\",\"80\",\"ap\");";

        db.execSQL(ADD_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {



    }
}

