package com.shlomo.adi.finder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class showParkingAround extends AppCompatActivity implements LocationListener {

    LocationManager manager;
    WebView myView;
    Intent nextActivity;
    String longitude;
    String latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parking_around);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 100, this);
        myView = (WebView)findViewById (R.id.webView3);
        myView.setWebViewClient(new WebViewClient());
        WebSettings settings = myView.getSettings();
        settings.setJavaScriptEnabled(true);


        SharedPreferences myLocationDBfile = getSharedPreferences(saveNewSpace.LOCATION_FILE,MODE_PRIVATE);
         longitude = myLocationDBfile.getString("longitude","34.7598007");
        latitude = myLocationDBfile.getString("latitude","32.0487058");

        Location l = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is turned off", Toast.LENGTH_LONG).show();
        }
        else {
            String str = "https://www.google.com/maps/search/parking lots/@" + latitude + "," + longitude + ",17z";

            myView.loadUrl(str);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_parking_around, menu);
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

    @Override
    public void onLocationChanged(Location location) {
        String str = "https://www.google.com/maps/search/parking lots/@" + latitude + "," + longitude + ",17z";

        myView.loadUrl(str);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
