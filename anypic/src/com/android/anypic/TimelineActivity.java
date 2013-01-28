package com.android.anypic;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.android.utils.XMLParser;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

public class TimelineActivity extends Activity {
	XMLParser parser;
	Document doc;
	String xml;
	PullToRefreshListView lv;
	TimelineRowAdapter adapter;
	ArrayList<HashMap<String, String>> menuItems;
	ProgressDialog pDialog;
	
	private String URL = "http://api.androidhive.info/list_paging/?page=1";

	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";

	// Flag for current page
	int current_page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lv = (PullToRefreshListView) findViewById(R.id.list_activity);
		lv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });

		menuItems = new ArrayList<HashMap<String, String>>();

		parser = new XMLParser();
		xml = parser.getXmlFromUrl(URL); // getting XML
		doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ID, parser.getValue(e, KEY_ID)); // id not using any where
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// LoadMore button
		Button btnLoadMore = new Button(this);
		btnLoadMore.setText("Load More");

		// Adding Load More button to lisview at bottom
		lv.addFooterView(btnLoadMore);
		
		// Getting adapter
		adapter = new TimelineRowAdapter(this, menuItems);
		lv.setAdapter(adapter);

		/**
		 * Listening to Load More button click event
		 * */
		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Starting a new async task
				new loadMoreListView().execute();
			}
		});
		
		
		/**
		 * Listening to listview single row selected
		 * **/
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
//				String name = ((TextView) view.findViewById(R.id.name))
//						.getText().toString();
//
//				// Starting new intent
//				Intent in = new Intent(getApplicationContext(),
//						SingleMenuItemActivity.class);
//				in.putExtra(KEY_NAME, name);
//				startActivity(in);
			}
		});
	}
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

//        @Override
//        protected String[] doInBackground(Void... params) {
//            // Simulates a background job.
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                ;
//            }
//            return mStrings;
//        }

        @Override
        protected void onPostExecute(String[] result) {
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put(KEY_ID, "100"); // id not using any where
			map.put(KEY_NAME, "Thanh Nam");

			// adding HashList to ArrayList
			menuItems.add(0, map);

            // Call onRefreshComplete when the list has been refreshed.
            lv.onRefreshComplete();

            super.onPostExecute(result);
        }

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
    }

	/**
	 * Async Task that send a request to url
	 * Gets new list view data
	 * Appends to list view
	 * */
	private class loadMoreListView extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// Showing progress dialog before sending http request
			pDialog = new ProgressDialog(
					TimelineActivity.this);
			pDialog.setMessage("Please wait..");
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... unused) {
			runOnUiThread(new Runnable() {
				public void run() {
					// increment current page
					current_page += 1;
					
					// Next page request
					URL = "http://api.androidhive.info/list_paging/?page=" + current_page;

					xml = parser.getXmlFromUrl(URL); // getting XML
					doc = parser.getDomElement(xml); // getting DOM element

					NodeList nl = doc.getElementsByTagName(KEY_ITEM);
					// looping through all item nodes <item>
					for (int i = 0; i < nl.getLength(); i++) {
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						Element e = (Element) nl.item(i);
						
						// adding each child node to HashMap key => value
						map.put(KEY_ID, parser.getValue(e, KEY_ID));
						map.put(KEY_NAME, parser.getValue(e, KEY_NAME));

						// adding HashList to ArrayList
						menuItems.add(map);
					}
					
					// get listview current position - used to maintain scroll position
					int currentPosition = lv.getFirstVisiblePosition();
					
					// Appending new data to menuItems ArrayList
					adapter = new TimelineRowAdapter(
							TimelineActivity.this,
							menuItems);
					lv.setAdapter(adapter);
					
					// Setting new scroll position
					lv.setSelectionFromTop(currentPosition + 1, 0);

				}
			});

			return (null);
		}
		
		
		protected void onPostExecute(Void unused) {
			// closing progress dialog
			pDialog.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_timeline, menu);
		return true;
	}

}
