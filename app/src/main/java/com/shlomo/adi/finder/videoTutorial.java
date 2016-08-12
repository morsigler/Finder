package com.shlomo.adi.finder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class videoTutorial extends AppCompatActivity {

    VideoView myVideo;
    Intent nextActivity;
    MediaPlayer click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorial);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        getSupportActionBar().setElevation(0);

        click = MediaPlayer.create(this, R.raw.click);
        myVideo = (VideoView)findViewById(R.id.videoView);
        Uri myUri = Uri.parse("android.resource://" + getPackageName()+ "/" + R.raw.findertutorial);
        myVideo.setVideoURI(myUri);
        MediaController myMedia = new MediaController(this);
        myVideo.setMediaController(myMedia);
        myVideo.requestFocus();
        myVideo.start();

    }

    public void moveToConfigView(View view) {
        click.start();
        nextActivity = new Intent(this, configuration.class);
        startActivity(nextActivity);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_tutorial, menu);
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
}
