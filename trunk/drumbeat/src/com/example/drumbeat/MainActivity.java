package com.example.drumbeat;



import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class MainActivity extends TabActivity {
	ImageView img_logo;
	ImageView img_favorites;
	ImageView img_settings;
	ImageView img_more;
	static TabSpec homespec;
	static Intent listview1;
	static Intent listview2;
	static TabHost tabHost;

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        tabHost = getTabHost();
	        img_logo = new ImageView(this);
	        img_logo.setImageDrawable(getResources().getDrawable(R.drawable.morebutton_white));
	        LayoutParams params_logo = new LayoutParams(30, 30);
	        img_logo.setLayoutParams(params_logo);
	        
	        img_favorites = new ImageView(this);
	        img_favorites.setImageDrawable(getResources().getDrawable(R.drawable.favoritesbutton_white));
	        LayoutParams params_favorites = new LayoutParams(30, 30);
	        img_logo.setLayoutParams(params_favorites);
	        
	        img_settings = new ImageView(this);
	        img_settings.setImageDrawable(getResources().getDrawable(R.drawable.settingsbutton_white));
	        LayoutParams params_settings = new LayoutParams(30, 30);
	        img_logo.setLayoutParams(params_settings);
	        
	        
	        img_more = new ImageView(this);
	        img_more.setImageDrawable(getResources().getDrawable(R.drawable.morebutton_white));
	        LayoutParams params_more = new LayoutParams(30, 30);
	        img_logo.setLayoutParams(params_more);
	        
	        
	        // Tab for Photos
//	        TabSpec photospec = tabHost.newTabSpec("Photos");
//	        photospec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_photos_tab));
	        homespec = tabHost.newTabSpec("Home");
	        homespec.setIndicator("DRUM BEATS", null);
	        //homespec.setIndicator(img_logo);

	        listview1 = new Intent(this, ActivityStack.class);
	        listview2 = new Intent(this, ListviewLevel2.class);
	        homespec.setContent(listview1);
	        
	        TabSpec favoritesspec = tabHost.newTabSpec("Favorites");
	        favoritesspec.setIndicator("", getResources().getDrawable(R.drawable.favoritesbutton_white));
	        //favoritesspec.setIndicator(img_favorites);

	        Intent photosIntent = new Intent(this, FavoritesActivity.class);
	        favoritesspec.setContent(photosIntent);
	        
	        // Tab for Songs
//	        TabSpec songspec = tabHost.newTabSpec("Songs");
//	        // setting Title and Icon for the Tab
//	        songspec.setIndicator("Songs", getResources().getDrawable(R.drawable.icon_songs_tab));
	        TabSpec settingsspec = tabHost.newTabSpec("Settings");
	        settingsspec.setIndicator("", getResources().getDrawable(R.drawable.settingsbutton_white));
	        //settingsspec.setIndicator(img_settings);
	        

	        Intent songsIntent = new Intent(this, SettingsActivity.class);
	        settingsspec.setContent(songsIntent);
	        
	        // Tab for Videos
//	        TabSpec videospec = tabHost.newTabSpec("Videos");
//	        videospec.setIndicator("Videos", getResources().getDrawable(R.drawable.icon_videos_tab));
	        TabSpec morespec = tabHost.newTabSpec("More");
	        morespec.setIndicator("", getResources().getDrawable(R.drawable.morebutton_white));
	        //morespec.setIndicator(img_more);
	        Intent videosIntent = new Intent(this, MoreActivity.class);
	        morespec.setContent(videosIntent);
	        
	        // Adding all TabSpec to TabHost
	        tabHost.addTab(homespec);
	        tabHost.addTab(favoritesspec); // Adding photos tab
	        tabHost.addTab(settingsspec); // Adding songs tab
	        tabHost.addTab(morespec); // Adding videos tab
	        //for (int i = 1; i < tabHost.getTabWidget().getChildCount(); i++) {
	        ((TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title)).setTextSize(24);
	        ((TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title)).setTypeface(null,Typeface.BOLD);
	        	tabHost.getTabWidget().getChildAt(0).getLayoutParams().width = 250;
	        	tabHost.getTabWidget().getChildAt(1).getLayoutParams().width = 30;
	        	tabHost.getTabWidget().getChildAt(2).getLayoutParams().width = 30;
	        	tabHost.getTabWidget().getChildAt(3).getLayoutParams().width = 30;
	        //}
	    }	 
	 	static public void setNameHomeSpec(String name){
	 		((TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title)).setText(name);
//	 		if (level == 1){
//		        homespec.setContent(listview1);
//	 		} else if (level == 2){
//		        homespec.setContent(listview2);
//	 		}
	 	}
}	 
