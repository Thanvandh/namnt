package com.namnt.danhngon;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.namnt.utils.LocalDatabase;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ViewPager awesomePager;
	private int NUM_AWESOME_VIEWS = 20;
	private Context cxt;
	private AwesomePagerAdapter awesomeAdapter;
	private Button mBtRandom;
	private Button mBtShare;
	List<Quote> mListQuote;
	private int mIntPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cxt = this;
		LocalDatabase localdatabase = new LocalDatabase(this);
		try {
			localdatabase.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  

		 try {
			 
			 localdatabase.openDataBase();
			 mListQuote = localdatabase.getListQuotes();
			 NUM_AWESOME_VIEWS = mListQuote.size();
			 
			  
			 }catch(SQLException sqle){
			  
			 throw sqle;
			  
			 }
		 localdatabase.close();
	        
	    awesomeAdapter = new AwesomePagerAdapter();
	    awesomePager = (ViewPager) findViewById(R.id.quotepager);
	    awesomePager.setAdapter(awesomeAdapter);
	    mBtRandom = (Button) findViewById(R.id.bt_random);
	    mBtShare = (Button) findViewById(R.id.bt_share);
	    
	    mBtRandom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mIntPosition = new Random().nextInt(NUM_AWESOME_VIEWS);
				//Toast.makeText(MainActivity.this, "position " + mIntPosition, Toast.LENGTH_LONG).show();
				awesomePager.setCurrentItem(mIntPosition);
				awesomeAdapter.notifyDataSetChanged();
				//awesomePager.invalidate();
				
			}
		});
	    mBtShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				new AlertDialog.Builder(MainActivity.this)
//		           .setMessage("Yuyu tam thoi chua lam chuc nang nay!")
//		           .setCancelable(false)
//		           .setPositiveButton("OK", null)
//		           .show();
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			    sharingIntent.setType("text/plain");
			    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mListQuote.get(mIntPosition).getDanhngon());
			    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
			    startActivity(Intent.createChooser(sharingIntent, "Share using"));
				
			}
		});
		
	}
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	           .setMessage("Are you sure you want to exit?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    MainActivity.this.finish();
	               }
	           })
	           .setNegativeButton("No", null)
	           .show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 private class AwesomePagerAdapter extends PagerAdapter{

			
			@Override
			public int getCount() {
				//int totalItem = mListQuote.size();
				return NUM_AWESOME_VIEWS;
			}
			
		    /**
		     * Create the page for the given position.  The adapter is responsible
		     * for adding the view to the container given here, although it only
		     * must ensure this is done by the time it returns from
		     * {@link #finishUpdate(android.view.ViewGroup)}.
		     *
		     * @param collection The containing View in which the page will be shown.
		     * @param position The page position to be instantiated.
		     * @return Returns an Object representing the new page.  This does not
		     * need to be a View, but can be some other container of the page.
		     */
			@Override
			public Object instantiateItem(ViewGroup collection, int position) {
				TextView tv = new TextView(cxt);
				mIntPosition = position;
				tv.setText(mListQuote.get(mIntPosition).getDanhngon());
				tv.setTextColor(Color.BLACK);
				tv.setTextSize(30);
				
				collection.addView(tv,0);
				
				return tv;
			}

		    /**
		     * Remove a page for the given position.  The adapter is responsible
		     * for removing the view from its container, although it only must ensure
		     * this is done by the time it returns from {@link #finishUpdate(android.view.ViewGroup)}.
		     *
		     * @param collection The containing View from which the page will be removed.
		     * @param position The page position to be removed.
		     * @param view The same object that was returned by
		     * {@link #instantiateItem(android.view.View, int)}.
		     */
			@Override
			public void destroyItem(ViewGroup collection, int position, Object view) {
				collection.removeView((TextView) view);
			}


	        /**
	         * Determines whether a page View is associated with a specific key object
	         * as returned by instantiateItem(ViewGroup, int). This method is required
	         * for a PagerAdapter to function properly.
	         * @param view Page View to check for association with object
	         * @param object Object to check for association with view
	         * @return
	         */
			@Override
			public boolean isViewFromObject(View view, Object object) {
				return (view==object);
			}

			
		    /**
		     * Called when the a change in the shown pages has been completed.  At this
		     * point you must ensure that all of the pages have actually been added or
		     * removed from the container as appropriate.
		     * @param arg0 The containing View which is displaying this adapter's
		     * page views.
		     */
			@Override
			public void finishUpdate(ViewGroup arg0) {}
			

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {}

			@Override
			public Parcelable saveState() {
				return null;
			}

			@Override
			public void startUpdate(ViewGroup arg0) {}
	    	
	    }

}
