package com.example.androidtablayout;

import android.R.anim;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        // Tab for Photos
//        TabSpec photospec = tabHost.newTabSpec("Photos");
//        photospec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_photos_tab));
        TabSpec photospec = tabHost.newTabSpec("Home");
        photospec.setIndicator("Home", getResources().getDrawable(R.drawable.icon_home_tab));

        Intent photosIntent = new Intent(this, PhotosActivity.class);
        photospec.setContent(photosIntent);
        
        // Tab for Songs
//        TabSpec songspec = tabHost.newTabSpec("Songs");
//        // setting Title and Icon for the Tab
//        songspec.setIndicator("Songs", getResources().getDrawable(R.drawable.icon_songs_tab));
        TabSpec songspec = tabHost.newTabSpec("Camera");
        songspec.setIndicator("", getResources().getDrawable(R.drawable.icon_camera_tab));
        

        Intent songsIntent = new Intent(this, SongsActivity.class);
        songspec.setContent(songsIntent);
        
        // Tab for Videos
//        TabSpec videospec = tabHost.newTabSpec("Videos");
//        videospec.setIndicator("Videos", getResources().getDrawable(R.drawable.icon_videos_tab));
        TabSpec videospec = tabHost.newTabSpec("Timeline");
        videospec.setIndicator("Timeline", getResources().getDrawable(R.drawable.icon_timeline_tab));
        Intent videosIntent = new Intent(this, VideosActivity.class);
        videospec.setContent(videosIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(videospec); // Adding videos tab
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(android.R.color.transparent);
        }
    }
}