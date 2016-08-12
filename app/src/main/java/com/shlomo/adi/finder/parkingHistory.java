package com.shlomo.adi.finder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.shlomo.adi.finder.R.layout.activity_parking_history;

public class parkingHistory extends AppCompatActivity {

    MyDatabaseHelper mDbHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;
    TextView result;
    TextView lastPlace;
    Intent nextActivity;
    String last;
    int counter;
    List<View> v;
    LayoutInflater vi;
    ViewGroup insertPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_parking_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


        //To access your database, instantiate your subclass of SQLiteOpenHelper:
        mDbHelper = new MyDatabaseHelper(this);
        dbWrite = mDbHelper.getWritableDatabase();
        dbRead = mDbHelper.getReadableDatabase();
        insertPoint = (ViewGroup) findViewById(R.id.insertPoint);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        String[] projection = {myParkingHistoryDB .TableParking.COLUMN_NAME_PLACE_ID,myParkingHistoryDB .TableParking.COLUMN_LONGITUDE, myParkingHistoryDB .TableParking.COLUMN_LATITUDE, myParkingHistoryDB .TableParking.COLUMN_ADDRESS};
        // How you want the results sorted in the resulting Curso
        String sortOrder = myParkingHistoryDB .TableParking.COLUMN_NAME_PLACE_ID + " DESC";
        Cursor c = dbRead.query(myParkingHistoryDB.TableParking.TABLE_NAME, projection, null, null, null, null, sortOrder);
        String s ="";
        c.moveToFirst();

        LayoutInflater inflator = LayoutInflater.from(getBaseContext());

        for(int i=0;i<c.getCount(); i++) {
            View view = inflator.inflate(R.layout.place_card, insertPoint, false);
            TextView placeName = (TextView) view.findViewById(R.id.placeName);
            placeName.setText(c.getString(3));
            TextView longi = (TextView) view.findViewById(R.id.longiLati);
            longi.setText("longitude: "+c.getString(1).substring(0,5)+", Latitude: "+c.getString(2).substring(0,5));
            ImageButton btn = (ImageButton) view.findViewById(R.id.buttonDelete);

            btn.setId(c.getInt(0));

            insertPoint.addView(view);
            c.moveToNext();
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parking_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_me){
            nextActivity = new Intent(this, aboutUs.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }
        else if (id == R.id.configuration){
            nextActivity = new Intent(this, configuration.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }
        else if (id == R.id.parking_lot){
            nextActivity = new Intent(this, showParkingAround.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }
        else if (id == R.id.new_space){
            nextActivity = new Intent(this, saveNewSpace.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }
        else if (id == R.id.last_space){
            nextActivity = new Intent(this, LastParkingSpace.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }
        else if (id == R.id.history){
            nextActivity = new Intent(this, parkingHistory.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }


        return super.onOptionsItemSelected(item);
    }

    public void deleteDb(View view) {

            // Define 'where' part of query.
            String selection = myParkingHistoryDB.TableParking.COLUMN_NAME_PLACE_ID + " LIKE ?";
// Specify arguments in placeholder order.
            int id=Integer.valueOf(view.getId());
            String[] selectionArgs = { String.valueOf(id) };
// Issue SQL statement.

            dbRead.delete(myParkingHistoryDB.TableParking.TABLE_NAME, selection, selectionArgs);
        finish();
        startActivity(getIntent());

    }

    public void deleteAllDb(View view) {



        dbRead.delete(myParkingHistoryDB.TableParking.TABLE_NAME, null, null);

    }
}
