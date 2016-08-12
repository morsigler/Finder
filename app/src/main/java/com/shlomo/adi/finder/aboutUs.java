package com.shlomo.adi.finder;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class aboutUs extends AppCompatActivity {

    Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
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

    public void openAdi(View view) {

        RelativeLayout adi = (RelativeLayout) findViewById(R.id.adiInnerSection);
        ImageView expandAdi = (ImageView)findViewById(R.id.expandAdi);

        if (adi.getVisibility()== View.VISIBLE){
            adi.setVisibility(adi.GONE);
            expandAdi.setBackgroundResource(R.drawable.ic_expand);
        } else {
            adi.setVisibility(adi.VISIBLE);
            expandAdi.setBackgroundResource(R.drawable.ic_expand_true);
        }
    }

    public void openMichal(View view) {

        RelativeLayout michal = (RelativeLayout) findViewById(R.id.michalInnerSection);
        ImageView expandMichal = (ImageView)findViewById(R.id.expandMichal);

        if (michal.getVisibility()== View.VISIBLE){

            michal.setVisibility(michal.GONE);
            expandMichal.setBackgroundResource(R.drawable.ic_expand);
        } else {
            michal.setVisibility(michal.VISIBLE);
            expandMichal.setBackgroundResource(R.drawable.ic_expand_true);

        }
    }

    public void openNitzan(View view) {
        RelativeLayout nitzan = (RelativeLayout) findViewById(R.id.nitzanInnerSection);
        ImageView expandNitzan = (ImageView)findViewById(R.id.expandNitzan);

        if (nitzan.getVisibility()== View.VISIBLE){
            nitzan.setVisibility(nitzan.GONE);
            expandNitzan.setBackgroundResource(R.drawable.ic_expand);
        } else {
            nitzan.setVisibility(nitzan.VISIBLE);
            expandNitzan.setBackgroundResource(R.drawable.ic_expand_true);

        }
    }
}
