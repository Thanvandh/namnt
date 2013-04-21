package com.drumbeat.app;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MoreActivity extends Activity {
	ImageButton bt_settings;
	ImageButton bt_favorite;
	Button bt_back;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_layout);
        bt_favorite = (ImageButton) findViewById(R.id.more_bt_favorites);
		bt_favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MoreActivity.this, ListviewLevel2.class);
				intent.putExtra(ListviewLevel1.KEY_FOLDER, "");
				intent.putExtra(ListviewLevel1.KEY_FAVORITE_MODE, true);
				startActivity(intent);
			}
		});
		bt_back = (Button) findViewById(R.id.more_bt_logo);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MYRIADPRO-BOLD.OTF");  
		bt_back.setTypeface(font);
		
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ListviewLevel2.folder.length() > 0){
					//Log.d("test","test|" + folder);
					Intent intent = new Intent(MoreActivity.this, ListviewLevel2.class);
					intent.putExtra(ListviewLevel1.KEY_FOLDER, ListviewLevel2.folder);
					intent.putExtra(ListviewLevel1.KEY_FAVORITE_MODE, false);
					startActivity(intent);
				} else {
					Intent i = new Intent(MoreActivity.this, ListviewLevel1.class);
					startActivity(i);
				}
			}
		});
		
		bt_settings = (ImageButton) findViewById(R.id.more_bt_settings);
		
		bt_settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MoreActivity.this, SettingsActivity.class);
				startActivity(i);
			}
		});
        
    }
//    private void openQuitDialog(){
//	  	  AlertDialog.Builder quitDialog 
//	  	   = new AlertDialog.Builder(MoreActivity.this);
//	  	  quitDialog.setTitle("Confirm to Quit?");
//	  	  
//	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					ListviewLevel2.stopMusic();
//					FavoritesActivity.stopMusic();
//					finish();
//					
//				}
//	  	  });   	  
//	  	  quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//	  		  public void onClick(DialogInterface dialog, int which) {
//	  	    // TODO Auto-generated method stub
//	  	    
//	  	   }});
//	  	  
//	  	  quitDialog.show();
//	  	 }
//
//	@Override
//	  public void onBackPressed() {
//		openQuitDialog(); 
//	  }
}