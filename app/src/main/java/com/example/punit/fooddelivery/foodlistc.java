package com.example.punit.fooddelivery;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class foodlistc extends AppCompatActivity {

    DBHelper mDBHelper=new DBHelper(this);
    CartHelper cDBHelper=new CartHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);
        info();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.carticon) {
            Intent i=new Intent(foodlistc.this,Checkoutlist.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    private void info()
    {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        LinearLayout layout = (LinearLayout) findViewById(R.id.homepage);
        String projection[] = {
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_PRICE,
                FoodContract.FoodEntry.COLUMN_IMG
        };

        Cursor cursor = db.query(
                FoodContract.FoodEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try

        {

            while (cursor.moveToNext()) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.eatables, null);
                String NameF = cursor.getString(cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME));
                String PriceF = cursor.getString(cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_PRICE));
                String IMGF = cursor.getString(cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_IMG));
                final TextView t1 = (TextView) addView.findViewById(R.id.foodname);
                final TextView t2 = (TextView) addView.findViewById(R.id.foodprice);
                ImageView i1 = (ImageView) addView.findViewById(R.id.foodimage);
                Button addb=(Button)addView.findViewById(R.id.addbutton);
                Button decb=(Button)addView.findViewById(R.id.decreasebutton);
                Button addcart=(Button)addView.findViewById(R.id.addtocartbutton);
                final TextView itemquant=(TextView)addView.findViewById(R.id.quantitybutton);


                addb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int num=Integer.parseInt(itemquant.getText().toString());

                        num++;
                        itemquant.setText(String.valueOf(num));
                    }
                });
                decb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int num=Integer.parseInt(itemquant.getText().toString());
                        if(num>0)
                        {
                            num--;
                            itemquant.setText(String.valueOf(num));
                        }
                    }
                });
                addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.parseInt(itemquant.getText().toString())>0){
                            SQLiteDatabase db=cDBHelper.getWritableDatabase();
                            //Toast.makeText(MainActivity.this,itemquant.getText().toString(),Toast.LENGTH_LONG).show();
                            ContentValues values=new ContentValues();
                            values.put(CartContract.CartEntry.CART_NAME,t1.getText().toString());
                            values.put(CartContract.CartEntry.CART_PRICE,Integer.parseInt(t2.getText().toString()));
                            values.put(CartContract.CartEntry.CART_QUANTITY,Integer.parseInt(itemquant.getText().toString()));
                            int id=(int)db.update(CartContract.CartEntry.TABLE_NAME,values, CartContract.CartEntry.CART_NAME+"=?",new String[] {t1.getText().toString()});
                            if(id==0)
                            {
                                db.insertWithOnConflict(CartContract.CartEntry.TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_IGNORE);

                            }
                        }
                    }
                });
                t1.setText(NameF);
                t2.setText(PriceF);
                i1.setImageResource(getResources().getIdentifier(""+IMGF, "drawable", getPackageName()));
                layout.addView(addView);

                LinearLayout addView1=new LinearLayout(this);
                addView1.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,25
                ));
                layout.addView(addView1);
            }
        }finally

        {
            cursor.close();
        }
    }
}
