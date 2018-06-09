package com.example.punit.fooddelivery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punit.fooddelivery.CartContract.CartEntry;

public class Checkoutlist extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        getlist();
    }

    private void getlist()
    {
        CartHelper cDBHelper=new CartHelper(this);

        SQLiteDatabase db=cDBHelper.getReadableDatabase();
        LinearLayout layout=(LinearLayout)findViewById(R.id.itemlist);
        String projection[]={CartEntry.CART_NAME,CartEntry.CART_PRICE,CartEntry.CART_QUANTITY};
        Cursor cursor=db.query(
                CartEntry.TABLE_NAME,
                projection,
                null,null,null,null,null
        );

        try
        {
            int total=0;
            TextView tt=(TextView)findViewById(R.id.totalamount);
            Toast.makeText(this,String.valueOf(cursor.getCount()),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext()){
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView=layoutInflater.inflate(R.layout.itemview,null);
                TextView iname=(TextView)addView.findViewById(R.id.iname);
                TextView iprice=(TextView)addView.findViewById(R.id.iprice);
                TextView iquantity=(TextView)addView.findViewById(R.id.iquantity);
                TextView itotal=(TextView)addView.findViewById(R.id.itotal);
                iname.setText(cursor.getString(cursor.getColumnIndex(CartEntry.CART_NAME)));
                iprice.setText("Rs."+cursor.getString(cursor.getColumnIndex(CartEntry.CART_PRICE)));
                iquantity.setText(cursor.getString(cursor.getColumnIndex(CartEntry.CART_QUANTITY)));
                int it=Integer.parseInt(cursor.getString(cursor.getColumnIndex(CartEntry.CART_PRICE)))*
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(CartEntry.CART_QUANTITY)));
                itotal.setText("Rs."+String.valueOf(it));
                total=total+it;
                layout.addView(addView);
                LinearLayout addView1=new LinearLayout(this);
                addView1.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,25
                ));
                layout.addView(addView1);


            }

            tt.setText("Rs."+String.valueOf(total));
        }
        finally
        {
            cursor.close();
        }
    }


}

