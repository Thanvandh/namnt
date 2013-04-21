package com.drumbeat.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.drumbeat.utils.DatabaseHandler;


import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListviewLevel1 extends ActivityGroup  {
	

	String xml;
	ListView lv;
	ListviewLevel1RowAdapter adapter;
	ArrayList<HashMap<String, String>> array_name;
	//String[] values = new String[] {"Started Pack", "Break Beats", "Hit Songs 1", "Pop-rock 1" };
	String[] values;
	String[] values1 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values2 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values3 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values4 = new String[] {"Audio1", "Audio2", "Audio3"};
	
	static String KEY_NAME = "name";
	static String KEY_FOLDER = "folder";
	static String KEY_FAVORITE_MODE = "favorite_mode";
	ImageButton bt_settings;
	ImageButton bt_more;
	ImageButton bt_favorite;


	// Flag for current page
	int current_page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview1);
		 DatabaseHandler myDbHelper = new DatabaseHandler(this);
		  
		 try {
		  
		 myDbHelper.createDataBase();
		  
		 } catch (IOException ioe) {
		  
		 throw new Error("Unable to create database");
		  
		 }
		 try {
			 
			 myDbHelper.openDataBase();
			 values = myDbHelper.getListFolder();
			  
			 }catch(SQLException sqle){
			  
			 throw sqle;
			  
			 }
		 myDbHelper.close();
		  
		 
		lv = (ListView) findViewById(R.id.listview1_activity);
		
		array_name = new ArrayList<HashMap<String, String>>();
		
		for (int i=0; i<values.length; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_NAME,values[i]);
			array_name.add(map);
		}
		

		// Getting adapter
		adapter = new ListviewLevel1RowAdapter(this, array_name);
		lv.setAdapter(adapter);

		/**
		 * Listening to Load More button click event
		 * */
		

		/**
		 * Listening to listview single row selected
		 * **/
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
					long arg3) {
				// TODO Auto-generated method stub
				//MainActivity.setNameHomeSpec(values[postion]);
				Intent intent = new Intent(ListviewLevel1.this, ListviewLevel2.class);
				intent.putExtra(KEY_FOLDER, values[postion]);
				intent.putExtra(KEY_FAVORITE_MODE, false);
		        //intent.setClass(getParent(), ListviewLevel2 .class);
		        //ActivityStack activityStack = (ActivityStack) getParent();
		        //activityStack.push("SecondStackActivity", intent);
				startActivity(intent);
				
				
			}
		});
		
		bt_settings = (ImageButton) findViewById(R.id.listview1_bt_settings);
		bt_settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ListviewLevel1.this, SettingsActivity.class);
				startActivity(i);
			}
		});
		
		bt_more = (ImageButton) findViewById(R.id.listview1_bt_more_edit);
		bt_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ListviewLevel1.this, MoreActivity.class);
				startActivity(i);
			}
		});
		bt_favorite = (ImageButton) findViewById(R.id.listview1_bt_favorites);
		bt_favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(ListviewLevel1.this, FavoritesActivity.class);
//				startActivity(i);
				Intent intent = new Intent(ListviewLevel1.this, ListviewLevel2.class);
				intent.putExtra(KEY_FOLDER, "");
				intent.putExtra(KEY_FAVORITE_MODE, true);
				startActivity(intent);
			}
		});
	}
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		MainActivity.setNameHomeSpec("Drum Beats");
//		super.onResume();
//	}
	
	
	private void openQuitDialog(){
	  	  AlertDialog.Builder quitDialog 
	  	   = new AlertDialog.Builder(ListviewLevel1.this);
	  	  quitDialog.setTitle("Confirm to Quit?");
	  	  
	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					
				}
	  	  });   	  
	  	  quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	  		  public void onClick(DialogInterface dialog, int which) {
	  	    // TODO Auto-generated method stub
	  	    
	  	   }});
	  	  
	  	  quitDialog.show();
	  	 }

	@Override
	  public void onBackPressed() {
		openQuitDialog(); 
	  }
////	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_timeline, menu);
//		return true;
//	}

}
