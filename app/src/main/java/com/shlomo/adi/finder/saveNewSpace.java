package com.shlomo.adi.finder;

import android.Manifest;
import android.location.GpsStatus;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shlomo.adi.finder.parkingHistory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class saveNewSpace extends AppCompatActivity implements LocationListener {

    public static String LOCATION_FILE = "file2";


    WebView webView;
    SharedPreferences myLocationDBfile;
    SharedPreferences.Editor myLocationEditor;
    MediaPlayer click;
    Intent nextActivity;
    Button update;
    Button save;
    MyDatabaseHelper mDbHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;
    String longitude;
    String latitude;
    String address;
    String myAddress;
    LocationManager locationManager;
    Location myLocation;

    long lastKnownLocationTimeMillis;
    boolean isGpsFix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_new_space);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        myLocationDBfile = getSharedPreferences(LOCATION_FILE, MODE_PRIVATE);
        myLocationEditor = myLocationDBfile.edit();

        click = MediaPlayer.create(this, R.raw.click);
        update = (Button) findViewById(R.id.locationButton);
        save = (Button) findViewById(R.id.saveAndReturnbutton);
        webView = (WebView) findViewById(R.id.webView1);
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        isGpsFix = false;
        lastKnownLocationTimeMillis = 0;

        //To access your database, instantiate your subclass of SQLiteOpenHelper:
        mDbHelper = new MyDatabaseHelper(this);
        // Gets the data repository in write mode
        dbWrite = mDbHelper.getWritableDatabase();
        // Gets the data repository in write mode
        dbRead = mDbHelper.getReadableDatabase();


        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
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


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 3, 0, this);
        //locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplicationContext(), R.string.Gps_turned_off, Toast.LENGTH_LONG).show();
        } else {

            if (myLocation == null) {
                Toast.makeText(getApplicationContext(), R.string.Gps_seraching, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),R.string.Go_out, Toast.LENGTH_SHORT).show();
            } else {
                longitude = Double.toString(myLocation.getLongitude());
                latitude = Double.toString(myLocation.getLatitude());
                String str = "https://www.google.com/maps/@" + latitude + "," + longitude + ",17z";
                webView.loadUrl(str);
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_new_space, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_me) {
            nextActivity = new Intent(this, aboutUs.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        } else if (id == R.id.configuration) {
            nextActivity = new Intent(this, configuration.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        } else if (id == R.id.parking_lot) {
            nextActivity = new Intent(this, showParkingAround.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        } else if (id == R.id.new_space) {
            nextActivity = new Intent(this, saveNewSpace.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        } else if (id == R.id.last_space) {
            nextActivity = new Intent(this, LastParkingSpace.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        } else if (id == R.id.history) {
            nextActivity = new Intent(this, parkingHistory.class);
            startActivity(nextActivity);
            overridePendingTransition(0, 0);
        }


        return super.onOptionsItemSelected(item);
    }


    public void saveNewPlace(View view) {

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


        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplicationContext(), R.string.Gps_turned_off, Toast.LENGTH_LONG).show();
        }
            else{
                if (myLocation == null) {
                    Toast.makeText(getApplicationContext(), R.string.Gps_seraching, Toast.LENGTH_LONG).show();
                }
                    else {
                        longitude = Double.toString(myLocation.getLongitude());
                        latitude = Double.toString(myLocation.getLatitude());

                        myLocationEditor.putString("longitude", longitude);
                        myLocationEditor.putString("latitude", latitude);
                        myLocationEditor.commit();

                        address = getAddress(myLocation.getLatitude(), myLocation.getLongitude());

                        click.start();
                        update.setTextColor(getApplication().getResources().getColor(R.color.white));
                        save.setTextColor(getApplication().getResources().getColor(R.color.button));

                        ContentValues values = new ContentValues();
                        values.put(myParkingHistoryDB.TableParking.COLUMN_LONGITUDE, longitude);
                        values.put(myParkingHistoryDB.TableParking.COLUMN_LATITUDE, latitude);
                        values.put(myParkingHistoryDB.TableParking.COLUMN_ADDRESS, address);

                        // Insert the new row, returning the primary key value of the new row

                        dbWrite.insert(myParkingHistoryDB.TableParking.TABLE_NAME, null, values);

                        nextActivity = new Intent(this, LastParkingSpace.class);
                        startActivity(nextActivity);
                    }
                }

    }


    public void update(View view) {

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
        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplicationContext(), R.string.Gps_turned_off, Toast.LENGTH_LONG).show();
        }
        else {
            if (myLocation == null) {
                Toast.makeText(getApplicationContext(), R.string.Gps_seraching, Toast.LENGTH_LONG).show();
            } else {
                longitude = Double.toString(myLocation.getLongitude());
                latitude = Double.toString(myLocation.getLatitude());

                String str = "https://www.google.com/maps/@" + latitude + "," + longitude + ",17z";
                webView.loadUrl(str);


                click.start();
                update.setTextColor(getApplication().getResources().getColor(R.color.button));
            }
        }

    }

    protected void onStop() {
        super.onStop();


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

        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (myLocation != null) {
            double longitude = myLocation.getLongitude();
            double latitude = myLocation.getLatitude();

            myLocationEditor.putString("longitude", Double.toString(longitude));
            myLocationEditor.putString("latitude", Double.toString(latitude));
            myLocationEditor.commit();

        }
    }


    @Override
    public void onLocationChanged(Location location) {

        if (location == null){
            Toast.makeText(getApplicationContext(),R.string.Gps_seraching, Toast.LENGTH_LONG).show();
        }else {
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();
            String str = "https://www.google.com/maps/@" + latitude + "," + longitude + ",17z";
            webView.loadUrl(str);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        switch (i) {
            case GpsStatus.GPS_EVENT_STARTED:
                // Do Something with mStatus info
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                // Do Something with mStatus info
                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
                longitude = Double.toString(myLocation.getLongitude());
                latitude = Double.toString(myLocation.getLatitude());

                String str = "https://www.google.com/maps/@" + latitude + "," + longitude + ",17z";
                webView.loadUrl(str);
                break;

            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                // Do Something with mStatus info
                break;
        }

    }

    @Override
    public void onProviderEnabled(String s) {
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
        onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
    }

    @Override
    public void onProviderDisabled(String s) {


    }

    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                myAddress = getApplication().getResources().getString(R.string.no_name_location);

            } else {
                if (addresses.size() > 0) {
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    myAddress = cityName + ", " + stateName;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myAddress;
    }


}
