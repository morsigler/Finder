package com.shlomo.adi.finder;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class configuration extends AppCompatActivity {

    public static String FILE_NAME = "file1";

    Intent nextActivity;
    EditText name;
    EditText carNum;
    EditText carType;
    SharedPreferences myDBfile;
    SharedPreferences.Editor myEditor;
    MediaPlayer click;
    View myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        getSupportActionBar().setElevation(0);

        name = (EditText)findViewById(R.id.name);
        carNum = (EditText)findViewById(R.id.carNum);
        carType = (EditText)findViewById(R.id.carType);
        click = MediaPlayer.create(this, R.raw.click);
        myLayout = (View)findViewById(R.id.config_layout);

        myDBfile = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        myEditor = myDBfile.edit();

        String driver = myDBfile.getString("driver",getString(R.string.defNameMessage));
        String carNum = myDBfile.getString("carNum",getString(R.string.defCarNumMessage));
        String carType = myDBfile.getString("carType",getString(R.string.defCarTypeMessage));
        ((TextView)findViewById(R.id.name)).setText(driver);
        ((TextView)findViewById(R.id.carNum)).setText(carNum);
        ((TextView)findViewById(R.id.carType)).setText(carType);

        myLayout.setOnTouchListener(new OnSwipeTouchListener(configuration.this) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {

            }

            public void onSwipeLeft() {
                moveToVideo(myLayout);
            }

            public void onSwipeBottom() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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


    public void save(View view) {
        myEditor.putString("driver", ((EditText)findViewById(R.id.name)).getText().toString());
        myEditor.putString("carNum", ((EditText)findViewById(R.id.carNum)).getText().toString());
        myEditor.putString("carType", ((EditText)findViewById(R.id.carType)).getText().toString());
        myEditor.commit();

        click.start();

        nextActivity = new Intent(this, LastParkingSpace.class);
        startActivity(nextActivity);
    }

    protected void onStop() {
        super.onStop();
        myEditor.putString("driver", ((EditText)findViewById(R.id.name)).getText().toString());
        myEditor.putString("carNum", ((EditText)findViewById(R.id.carNum)).getText().toString());
        myEditor.putString("carType", ((EditText)findViewById(R.id.carType)).getText().toString());
        myEditor.commit();

    }

    public void moveToVideo(View view) {
        click.start();
        nextActivity = new Intent(this, videoTutorial.class);
        startActivity(nextActivity);
        overridePendingTransition(0, 0);
    }

    public void cancel(View view) {
        click.start();

        nextActivity = new Intent(this, LastParkingSpace.class);
        startActivity(nextActivity);

    }
}
