package com.shlomo.adi.finder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.support.v7.widget.ActionBarOverlayLayout.*;


public class LastParkingSpace extends AppCompatActivity {

    Intent nextActivity;
    LocationManager manager;
    WebView myView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_parking_space);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);


        manager =(LocationManager)getSystemService(this.LOCATION_SERVICE);
        myView = (WebView)findViewById (R.id.webView2);
        myView.setWebViewClient(new WebViewClient());
        WebSettings settings = myView.getSettings();
        settings.setJavaScriptEnabled(true);

        SharedPreferences myDBfile = getSharedPreferences(configuration.FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myDBfile.edit();

        SharedPreferences myLocationDBfile = getSharedPreferences(saveNewSpace.LOCATION_FILE,MODE_PRIVATE);
        SharedPreferences.Editor myLocationEditor = myLocationDBfile.edit();

        String driver = myDBfile.getString("driver",getString(R.string.defNameMessage));
        String carNum = myDBfile.getString("carNum",getString(R.string.defCarNumMessage));
        String carType = myDBfile.getString("carType",getString(R.string.defCarTypeMessage));

        ((TextView)findViewById(R.id.driver)).setText(driver+", "+carNum+", "+carType);


        String longitude = myLocationDBfile.getString("longitude","34.7598007");
        String latitude = myLocationDBfile.getString("latitude","32.0487058");

        Geocoder geocoder = new Geocoder(this,Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude) , Double.parseDouble(longitude),1);
            if(addresses.isEmpty()){
                ((TextView)findViewById(R.id.longitude)).setText("Waiting for Location");

            }
            else {
                if (addresses.size() > 0) {
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    ((TextView)findViewById(R.id.longitude)).setText(cityName+", "+stateName);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String str = "https://www.google.com/maps/@"+latitude+","+longitude+",17z";
        myView.loadUrl(str);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_last_parking_space, menu);

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
       else if (id == R.id.history){
           nextActivity = new Intent(this, parkingHistory.class);
           startActivity(nextActivity);
           overridePendingTransition(0, 0);
       }

        return super.onOptionsItemSelected(item);
    }
}
