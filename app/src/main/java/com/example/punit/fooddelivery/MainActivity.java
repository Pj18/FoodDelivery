package com.example.punit.fooddelivery;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punit.fooddelivery.FoodContract.FoodEntry;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CartHelper cDBHelper=new CartHelper(this);
    //DBHelper mDBHelper=new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SQLiteDatabase db=cDBHelper.getWritableDatabase();
        cDBHelper.deletedb(db);


        info();

    }

    private void info()
    {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        LinearLayout layout = (LinearLayout) findViewById(R.id.homepage1);
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
                "Random()",
                "3"

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent i=new Intent(MainActivity.this,Checkoutlist.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.eatables) {
            Intent intent=new Intent(MainActivity.this,foodlistc.class);
            startActivity(intent);
        } else if (id == R.id.beverages) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private DBHelper mDBHelper = new DBHelper(this);





}
