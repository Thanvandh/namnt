package com.example.drumbeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListviewLevel2 extends Activity {
	

	String xml;
	ListView lv;
	ListviewLevel2RowAdapter adapter;
	Button bt_tempo;
	ArrayList<HashMap<String, String>> array_name;
	String[] values = new String[] {"Started Pack", "Break Beats", "Hit Songs 1", "Pop-rock 1" };
	String[] values1 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values2 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values3 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values4 = new String[] {"Audio1", "Audio2", "Audio3"};
	
	static String KEY_NAME = "name";
	


	// Flag for current page
	int current_page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview2);
		lv = (ListView) findViewById(R.id.listview2_activity);
		bt_tempo = (Button) findViewById(R.id.bt_tempo);

		array_name = new ArrayList<HashMap<String, String>>();
		
		for (int i=0; i<values1.length; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_NAME,values1[i]);
			array_name.add(map);
		}
		

		// Getting adapter
		adapter = new ListviewLevel2RowAdapter(this, array_name);
		lv.setAdapter(adapter);

		/**
		 * Listening to Load More button click event
		 * */
		

		/**
		 * Listening to listview single row selected
		 * **/
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		bt_tempo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ListviewLevel2.this,TempoActivity.class);
				startActivity(i);
				
			}
		});
	}

	
//	private void openQuitDialog(){
//	  	  AlertDialog.Builder quitDialog 
//	  	   = new AlertDialog.Builder(ListviewLevel2.this);
//	  	  quitDialog.setTitle("Confirm to Quit?");
//	  	  
//	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
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
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_timeline, menu);
//		return true;
//	}

}
