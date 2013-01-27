package com.android.anypic;


import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TabHost tabHost = getTabHost();
        
        // Tab for Photos
//        TabSpec photospec = tabHost.newTabSpec("Photos");
//        photospec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_photos_tab));
        TabSpec homespec = tabHost.newTabSpec("Home");
        homespec.setIndicator(prepareTabView("Home", R.drawable.icon_home_tab));

        Intent photosIntent = new Intent(this, HomeActivity.class);
        homespec.setContent(photosIntent);
        
        // Tab for Songs
//        TabSpec songspec = tabHost.newTabSpec("Songs");
//        // setting Title and Icon for the Tab
//        songspec.setIndicator("Songs", getResources().getDrawable(R.drawable.icon_songs_tab));
        TabSpec cameraspec = tabHost.newTabSpec("Camera");
        cameraspec.setIndicator(prepareTabView("", R.drawable.icon_camera_tab));
        

        Intent songsIntent = new Intent(this, GalleryActivity.class);
        cameraspec.setContent(songsIntent);
        
        // Tab for Videos
//        TabSpec videospec = tabHost.newTabSpec("Videos");
//        videospec.setIndicator("Videos", getResources().getDrawable(R.drawable.icon_videos_tab));
        TabSpec timelinespec = tabHost.newTabSpec("Timeline");
        timelinespec.setIndicator(prepareTabView("Timeline", R.drawable.icon_timeline_tab));
        Intent videosIntent = new Intent(this, TimelineActivity.class);
        timelinespec.setContent(videosIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(homespec); // Adding photos tab
        tabHost.addTab(cameraspec); // Adding songs tab
        tabHost.addTab(timelinespec); // Adding videos tab
//        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
//        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
//        }
        //tabHost.setCurrentTab(0);
        
	}
	private View prepareTabView(String text, int resId) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs, null);
        ImageView iv = (ImageView) view.findViewById(R.id.TabImageView);
        TextView tv = (TextView) view.findViewById(R.id.TabTextView);
        iv.setImageResource(resId);
        tv.setText(text);
        return view;
   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
