package com.android.anypic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.android.utils.XMLParser;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
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
	final static int LIMIT_ACTIVITY = 5;
	final static String KEY_FROM_USER_NAME = "fromUser_name";
	final static String KEY_FROM_USER_AVATAR_URL = "fromUser_avatar_url";
	final static String KEY_TO_USER_NAME = "toUser_name";
	final static String KEY_TO_USER_AVATAR_URL = "toUser_avatar_url";
	final static String KEY_CONTENT = "content";

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

		ParseQuery query_activity = new ParseQuery("activity");
		query_activity.addDescendingOrder("createdAt");
		query_activity.setLimit(LIMIT_ACTIVITY);
		query_activity.findInBackground(new FindCallback() {
			public void done(List<ParseObject> List, ParseException e) {
				if (e == null) {
					for (int i = 0; i < List.size(); i++) {
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						Log.d("test", "objectid " + List.get(i).getObjectId());
						// id not using any where
						ParseUser fromuser = new ParseUser();
						fromuser = (ParseUser) List.get(i).get("fromUser");
						try {
							fromuser = fromuser.fetch();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// ParseQuery query =
						// ParseUser.getQuery().whereEqualTo("objectId",
						// fromuser_id);

						String fromUser_name = null;
						String fromUser_avatar_url = null;
						String content = null;
						fromUser_name = fromuser.getString("displayname");
						ParseFile res = (ParseFile) fromuser
								.get("profilePictureMedium");
						fromUser_avatar_url = res.getUrl();
						
						ParseUser touser = new ParseUser();
						touser = (ParseUser) List.get(i).get("toUser");
						try {
							touser = touser.fetch();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// ParseQuery query =
						// ParseUser.getQuery().whereEqualTo("objectId",
						// fromuser_id);

						String toUser_name = null;
						String toUser_avatar_url = null;
						toUser_name = touser.getString("displayname");
						ParseFile res_to_user = (ParseFile) fromuser
								.get("profilePictureMedium");
						toUser_avatar_url = res_to_user.getUrl();
						content = List.get(i).getString("content");
						

						// ParseFile res = (ParseFile)
						// photoList.get(i).get("image");
						map.put(KEY_FROM_USER_NAME, fromUser_name);
						map.put(KEY_FROM_USER_AVATAR_URL, fromUser_avatar_url);
						map.put(KEY_TO_USER_NAME, toUser_name);
						map.put(KEY_TO_USER_AVATAR_URL, toUser_avatar_url);
						map.put(KEY_CONTENT, content);
						// map.put(AVATAR_URL, avatar_url);
						// adding HashList to ArrayList
						menuItems.add(map);
						// Getting adapter
						adapter = new TimelineRowAdapter(TimelineActivity.this,
								menuItems);
						lv.setAdapter(adapter);
					}

				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});

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
				// String name = ((TextView) view.findViewById(R.id.name))
				// .getText().toString();
				//
				// // Starting new intent
				// Intent in = new Intent(getApplicationContext(),
				// SingleMenuItemActivity.class);
				// in.putExtra(KEY_NAME, name);
				// startActivity(in);
			}
		});
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		// @Override
		// protected String[] doInBackground(Void... params) {
		// // Simulates a background job.
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// ;
		// }
		// return mStrings;
		// }

		@Override
		protected void onPostExecute(String[] result) {
			menuItems.clear();
			current_page = 0;
			
			ParseQuery query_activity = new ParseQuery("activity");
			query_activity.addDescendingOrder("createdAt");
			query_activity.setLimit(LIMIT_ACTIVITY);
			query_activity.findInBackground(new FindCallback() {
				public void done(List<ParseObject> List, ParseException e) {
					if (e == null) {
						for (int i = 0; i < List.size(); i++) {
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();
							// adding each child node to HashMap key => value
							Log.d("test", "objectid " + List.get(i).getObjectId());
							// id not using any where
							ParseUser fromuser = new ParseUser();
							fromuser = (ParseUser) List.get(i).get("fromUser");
							try {
								fromuser = fromuser.fetch();
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							// ParseQuery query =
							// ParseUser.getQuery().whereEqualTo("objectId",
							// fromuser_id);

							String fromUser_name = null;
							String fromUser_avatar_url = null;
							String content = null;
							fromUser_name = fromuser.getString("displayname");
							ParseFile res = (ParseFile) fromuser
									.get("profilePictureMedium");
							fromUser_avatar_url = res.getUrl();
							
							ParseUser touser = new ParseUser();
							touser = (ParseUser) List.get(i).get("toUser");
							try {
								touser = touser.fetch();
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							// ParseQuery query =
							// ParseUser.getQuery().whereEqualTo("objectId",
							// fromuser_id);

							String toUser_name = null;
							String toUser_avatar_url = null;
							toUser_name = touser.getString("displayname");
							ParseFile res_to_user = (ParseFile) fromuser
									.get("profilePictureMedium");
							toUser_avatar_url = res_to_user.getUrl();
							content = List.get(i).getString("content");
							
							

							// ParseFile res = (ParseFile)
							// photoList.get(i).get("image");
							map.put(KEY_FROM_USER_NAME, fromUser_name);
							map.put(KEY_FROM_USER_AVATAR_URL, fromUser_avatar_url);
							map.put(KEY_TO_USER_NAME, toUser_name);
							map.put(KEY_TO_USER_AVATAR_URL, toUser_avatar_url);
							map.put(KEY_CONTENT, content);
							// map.put(AVATAR_URL, avatar_url);
							// adding HashList to ArrayList
							menuItems.add(map);
							// Getting adapter
							adapter = new TimelineRowAdapter(TimelineActivity.this,
									menuItems);
							lv.setAdapter(adapter);
						}

					} else {
						Log.d("score", "Error: " + e.getMessage());
					}
				}
			});

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
	 * Async Task that send a request to url Gets new list view data Appends to
	 * list view
	 * */
	private class loadMoreListView extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// Showing progress dialog before sending http request
			pDialog = new ProgressDialog(TimelineActivity.this);
			pDialog.setMessage("Please wait..");
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... unused) {
			runOnUiThread(new Runnable() {
				public void run() {
					// increment current page
					current_page += LIMIT_ACTIVITY;

					// Next page request
					ParseQuery query_activity = new ParseQuery("activity");
					query_activity.addDescendingOrder("createdAt");
					query_activity.setLimit(LIMIT_ACTIVITY);
					query_activity.setSkip(current_page);
					query_activity.findInBackground(new FindCallback() {
						public void done(List<ParseObject> List, ParseException e) {
							if (e == null) {
								for (int i = 0; i < List.size(); i++) {
									// creating new HashMap
									HashMap<String, String> map = new HashMap<String, String>();
									// adding each child node to HashMap key => value
									Log.d("test", "objectid " + List.get(i).getObjectId());
									// id not using any where
									ParseUser fromuser = new ParseUser();
									fromuser = (ParseUser) List.get(i).get("fromUser");
									try {
										fromuser = fromuser.fetch();
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									// ParseQuery query =
									// ParseUser.getQuery().whereEqualTo("objectId",
									// fromuser_id);

									String fromUser_name = null;
									String fromUser_avatar_url = null;
									String content = null;
									fromUser_name = fromuser.getString("displayname");
									ParseFile res = (ParseFile) fromuser
											.get("profilePictureMedium");
									fromUser_avatar_url = res.getUrl();
									
									ParseUser touser = new ParseUser();
									touser = (ParseUser) List.get(i).get("toUser");
									try {
										touser = touser.fetch();
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									// ParseQuery query =
									// ParseUser.getQuery().whereEqualTo("objectId",
									// fromuser_id);

									String toUser_name = null;
									String toUser_avatar_url = null;
									toUser_name = touser.getString("displayname");
									ParseFile res_to_user = (ParseFile) fromuser
											.get("profilePictureMedium");
									toUser_avatar_url = res_to_user.getUrl();
									
									content = List.get(i).getString("content");

									// ParseFile res = (ParseFile)
									// photoList.get(i).get("image");
									map.put(KEY_FROM_USER_NAME, fromUser_name);
									map.put(KEY_FROM_USER_AVATAR_URL, fromUser_avatar_url);
									map.put(KEY_TO_USER_NAME, toUser_name);
									map.put(KEY_TO_USER_AVATAR_URL, toUser_avatar_url);
									map.put(KEY_CONTENT, content);
									// map.put(AVATAR_URL, avatar_url);
									// adding HashList to ArrayList
									menuItems.add(map);
									// Getting adapter
									adapter = new TimelineRowAdapter(TimelineActivity.this,
											menuItems);
									lv.setAdapter(adapter);
								}

							} else {
								Log.d("score", "Error: " + e.getMessage());
							}
						}
					});

					// get listview current position - used to maintain scroll
					// position
					int currentPosition = lv.getFirstVisiblePosition();

					// Appending new data to menuItems ArrayList
					adapter = new TimelineRowAdapter(TimelineActivity.this,
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
