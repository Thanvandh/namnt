package com.android.anypic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.utils.ImageLoader;
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
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;

public class GalleryActivity extends Activity {
	String object_id;
	String my_name;
	String photo_url;
	String avatar_url;
	String current_user_id;
	String fromuser_id;
	ImageView img_avatar;
	TextView txt_my_name;
	ImageView img_picture;
	View layout_focus;
	private ImageLoader imageLoader;
	PullToRefreshListView lv;
	GalleryRowAdapter adapter;
	ArrayList<HashMap<String, String>> menuItems;
	ArrayList<String> pictureUrl;
	ProgressDialog pDialog;
	final static int LIMIT_COMMENT = 5;
	final static String KEY_FROM_USER_NAME = "fromUser_name";
	final static String KEY_FROM_USER_AVATAR_URL = "fromUser_avatar_url";
	final static String KEY_CONTENT = "content";
	Button bt_send_comment;
	ImageButton bt_liked;
	EditText text_box_comment;
	Gallery likedpeople;
	
	
	int current_page = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		Intent in = getIntent();
		Bundle bundle = in.getExtras();
		object_id = bundle.getString(HomeActivity.OBJECT_ID);
		my_name = bundle.getString(HomeActivity.MY_NAME);
		photo_url = bundle.getString(HomeActivity.PHOTO_URL);
		avatar_url = bundle.getString(HomeActivity.AVATAR_URL);
		current_user_id = bundle.getString(HomeActivity.CURRENT_USER_ID);
		
		img_avatar = (ImageView) findViewById(R.id.img_gallery_avatar_pic);
		img_picture = (ImageView) findViewById(R.id.gallery_picture);
		txt_my_name = (TextView) findViewById(R.id.gallery_displayname);
		imageLoader=new ImageLoader(getApplicationContext());
		
		txt_my_name.setText(my_name);
		imageLoader.DisplayImage(avatar_url, img_avatar);
		imageLoader.DisplayImage(photo_url, img_picture);
		likedpeople = (Gallery) findViewById(R.id.GalleryLikedPeople);

		
		
//		likedpeople.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			public void onItemSelected(AdapterView<?> parent, View v,
//					int position, long id) {
//				mySelection.setText(" selected option: " + position );
//				
//			}
//
//			public void onNothingSelected(AdapterView<?> parent) {
//				mySelection.setText("Nothing selected");
//				
//			}
//
//
//		});
		
		lv = (PullToRefreshListView) findViewById(R.id.list_gallery_post);
		lv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });

		menuItems = new ArrayList<HashMap<String, String>>();
		pictureUrl = new ArrayList<String>();

//		parser = new XMLParser();
//		xml = parser.getXmlFromUrl(URL); // getting XML
//		doc = parser.getDomElement(xml); // getting DOM element
//
//		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
//		// looping through all item nodes <item>
//				for (int i = 0; i < nl.getLength(); i++) {
//					// creating new HashMap
//					HashMap<String, String> map = new HashMap<String, String>();
//					Element e = (Element) nl.item(i);
//					// adding each child node to HashMap key => value
//					map.put(KEY_ID, parser.getValue(e, KEY_ID)); // id not using any where
//					map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
//
//					// adding HashList to ArrayList
//					menuItems.add(map);
//				}
		
		ParseQuery query_activity = new ParseQuery("activity");
		ParseQuery query_photo = new ParseQuery("photo");
		ParseObject photo;
		try {
			photo = query_photo.get(object_id);
			query_activity.whereEqualTo("photo", photo);
			query_activity.addDescendingOrder("createdAt");
			query_activity.setLimit(LIMIT_COMMENT);
			query_activity.findInBackground(new FindCallback() {
			    public void done(List<ParseObject> List, ParseException e) {
			        if (e == null) {
			            Log.d("test", "Retrieved " + List.size() + " photos");
			            for (int i = 0; i < List.size(); i++) {
							// creating new HashMap
			            	String type = List.get(i).getString("type");
			            	if (type.equals("comment"))
			            	{
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
								//ParseQuery query = ParseUser.getQuery().whereEqualTo("objectId", fromuser_id);
								
								String fromUser_name = null;
								String fromUser_avatar_url = null;
								String content = null;
								fromUser_name = fromuser.getString("displayname");     
							    ParseFile res = (ParseFile)fromuser.get("profilePictureSmall");
							    fromUser_avatar_url = res.getUrl();
							    content = List.get(i).getString("content");
								
								//ParseFile res = (ParseFile) photoList.get(i).get("image");
								map.put(KEY_FROM_USER_NAME, fromUser_name); 
								map.put(KEY_FROM_USER_AVATAR_URL, fromUser_avatar_url); 
								map.put(KEY_CONTENT, content);
								//map.put(AVATAR_URL, avatar_url);
								// adding HashList to ArrayList
								menuItems.add(map);
								// Getting adapter
								adapter = new GalleryRowAdapter(GalleryActivity.this, menuItems);
								lv.setAdapter(adapter);	
			            	} else if (type.equalsIgnoreCase("liked"))
			            	{
			            		ParseUser fromuser = new ParseUser();
								fromuser = (ParseUser) List.get(i).get("fromUser");
								try {
									fromuser = fromuser.fetch();
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								//ParseQuery query = ParseUser.getQuery().whereEqualTo("objectId", fromuser_id);
								
								String fromUser_name = null;
								String fromUser_avatar_url = null;
								String content = null;
								fromUser_name = fromuser.getString("displayname");     
							    ParseFile res = (ParseFile)fromuser.get("profilePictureSmall");
							    fromUser_avatar_url = res.getUrl();
							    pictureUrl.add(fromUser_avatar_url);
							    likedpeople.setAdapter(new ImageAdapter(GalleryActivity.this));
			            		
			            	}
						}
			            
			        } else {
			            Log.d("score", "Error: " + e.getMessage());
			        }
			    }
			});
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		

		
		

		// LoadMore button
		Button btnLoadMore = new Button(this);
		btnLoadMore.setText("Load More");

		// Adding Load More button to lisview at bottom
		lv.addFooterView(btnLoadMore);
		
		

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
		
		text_box_comment = (EditText) findViewById(R.id.text_box_comment);
		//text_box_comment.clearFocus();
		layout_focus = (LinearLayout) findViewById(R.id.list_gallery_layout_focus);
		layout_focus.setFocusableInTouchMode(true);
		layout_focus.requestFocus();
		
		bt_send_comment = (Button)findViewById(R.id.bt_send_comment);
		bt_send_comment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = text_box_comment.getText().toString();
				if (content != null){
					post_comment(content);
					text_box_comment.clearFocus();
					text_box_comment.clearComposingText();
				}
				
			}
		});
		bt_liked = (ImageButton)findViewById(R.id.bt_like_picture);
		bt_liked.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				post_liked();
				
			}
		});
		
	}
	public void post_liked(){
		ParseObject activity = new ParseObject("activity");
		ParseQuery query_photo = new ParseQuery("photo");
		query_photo.whereEqualTo("objectId", object_id);
		try {
			ParseObject photo = query_photo.find().get(0);
			ParseObject user = (ParseObject)photo.get("user");
			//String toUser = user.getObjectId();
			activity.put("fromUser", ParseUser.getCurrentUser());
			activity.put("toUser", user);
			activity.put("photo", photo);
			activity.put("type", "liked");
			activity.saveInBackground();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void post_comment(String content){
		ParseObject activity = new ParseObject("activity");
		ParseQuery query_photo = new ParseQuery("photo");
		query_photo.whereEqualTo("objectId", object_id);
		try {
			ParseObject photo = query_photo.find().get(0);
			ParseObject user = (ParseObject)photo.get("user");
			//String toUser = user.getObjectId();
			activity.put("fromUser", ParseUser.getCurrentUser());
			activity.put("toUser", user);
			activity.put("photo", photo);
			activity.put("content", content);
			activity.put("type", "comment");
			activity.saveInBackground();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

//      @Override
//      protected String[] doInBackground(Void... params) {
//          // Simulates a background job.
//          try {
//              Thread.sleep(2000);
//          } catch (InterruptedException e) {
//              ;
//          }
//          return mStrings;
//      }

      @Override
      protected void onPostExecute(String[] result) {
      	HashMap<String, String> map = new HashMap<String, String>();
      	//map.put(OBJECT_ID, "100"); // id not using any where
			//map.put(KEY_NAME, "Thanh Nam");

			// adding HashList to ArrayList
			menuItems.clear();
			pictureUrl.clear();
			current_page = 0;

          // Call onRefreshComplete when the list has been refreshed.
			
			ParseQuery query_activity = new ParseQuery("activity");
			ParseQuery query_photo = new ParseQuery("photo");
			ParseObject photo;
			try {
				photo = query_photo.get(object_id);
				query_activity.whereEqualTo("photo", photo);
				query_activity.addDescendingOrder("createdAt");
				query_activity.setLimit(LIMIT_COMMENT);
				query_activity.findInBackground(new FindCallback() {
				    public void done(List<ParseObject> List, ParseException e) {
				        if (e == null) {
				            Log.d("test", "Retrieved " + List.size() + " photos");
				            for (int i = 0; i < List.size(); i++) {
				            	String type = List.get(i).getString("type");
				            	if (type.equalsIgnoreCase("comment"))
				            	{
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
									//ParseQuery query = ParseUser.getQuery().whereEqualTo("objectId", fromuser_id);
									
									String fromUser_name = null;
									String fromUser_avatar_url = null;
									String content = null;
									fromUser_name = fromuser.getString("displayname");     
								    ParseFile res = (ParseFile)fromuser.get("profilePictureSmall");
								    fromUser_avatar_url = res.getUrl();
								    content = List.get(i).getString("content");
									
									//ParseFile res = (ParseFile) photoList.get(i).get("image");
									map.put(KEY_FROM_USER_NAME, fromUser_name); 
									map.put(KEY_FROM_USER_AVATAR_URL, fromUser_avatar_url); 
									map.put(KEY_CONTENT, content);
									//map.put(AVATAR_URL, avatar_url);
									// adding HashList to ArrayList
									menuItems.add(map);
									// Getting adapter
									adapter = new GalleryRowAdapter(GalleryActivity.this, menuItems);
									lv.setAdapter(adapter);	
				            	} else if (type.equalsIgnoreCase("liked"))
				            	{
				            		ParseUser fromuser = new ParseUser();
									fromuser = (ParseUser) List.get(i).get("fromUser");
									try {
										fromuser = fromuser.fetch();
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									//ParseQuery query = ParseUser.getQuery().whereEqualTo("objectId", fromuser_id);
									
									String fromUser_name = null;
									String fromUser_avatar_url = null;
									String content = null;
									fromUser_name = fromuser.getString("displayname");     
								    ParseFile res = (ParseFile)fromuser.get("profilePictureSmall");
								    fromUser_avatar_url = res.getUrl();
								    pictureUrl.add(fromUser_avatar_url);
								    likedpeople.setAdapter(new ImageAdapter(GalleryActivity.this));
				            		
				            	}
							}
				            
				        } else {
				            Log.d("score", "Error: " + e.getMessage());
				        }
				    }
				});
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
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
					GalleryActivity.this);
			pDialog.setMessage("Please wait..");
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... unused) {
			runOnUiThread(new Runnable() {
				public void run() {
					// increment current page
					current_page += LIMIT_COMMENT;
					
					// Next page request
//					URL = "http://api.androidhive.info/list_paging/?page=" + current_page;
//
//					xml = parser.getXmlFromUrl(URL); // getting XML
//					doc = parser.getDomElement(xml); // getting DOM element
//
//					NodeList nl = doc.getElementsByTagName(KEY_ITEM);
//					// looping through all item nodes <item>
//					for (int i = 0; i < nl.getLength(); i++) {
//						// creating new HashMap
//						HashMap<String, String> map = new HashMap<String, String>();
//						Element e = (Element) nl.item(i);
//						
//						// adding each child node to HashMap key => value
//						map.put(KEY_ID, parser.getValue(e, KEY_ID));
//						map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
//
//						// adding HashList to ArrayList
//						menuItems.add(map);
//					}
					
					ParseQuery query_activity = new ParseQuery("activity");
					ParseQuery query_photo = new ParseQuery("photo");
					ParseObject photo;
					try {
						photo = query_photo.get(object_id);
						query_activity.whereEqualTo("photo", photo);
						query_activity.addDescendingOrder("createdAt");
						query_activity.setLimit(LIMIT_COMMENT);
						query_activity.setSkip(current_page);
						query_activity.findInBackground(new FindCallback() {
						    public void done(List<ParseObject> List, ParseException e) {
						        if (e == null) {
						            Log.d("test", "Retrieved " + List.size() + " photos");
						            for (int i = 0; i < List.size(); i++) {
						            	String type = List.get(i).getString("type");
						            	if (type.equalsIgnoreCase("comment"))
						            	{
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
											//ParseQuery query = ParseUser.getQuery().whereEqualTo("objectId", fromuser_id);
											
											String fromUser_name = null;
											String fromUser_avatar_url = null;
											String content = null;
											fromUser_name = fromuser.getString("displayname");     
										    ParseFile res = (ParseFile)fromuser.get("profilePictureSmall");
										    fromUser_avatar_url = res.getUrl();
										    content = List.get(i).getString("content");
											
											//ParseFile res = (ParseFile) photoList.get(i).get("image");
											map.put(KEY_FROM_USER_NAME, fromUser_name); 
											map.put(KEY_FROM_USER_AVATAR_URL, fromUser_avatar_url); 
											map.put(KEY_CONTENT, content);
											//map.put(AVATAR_URL, avatar_url);
											// adding HashList to ArrayList
											menuItems.add(map);
											// Getting adapter
											adapter = new GalleryRowAdapter(GalleryActivity.this, menuItems);
											lv.setAdapter(adapter);	
						            	} else if (type.equalsIgnoreCase("liked"))
						            	{
						            		ParseUser fromuser = new ParseUser();
											fromuser = (ParseUser) List.get(i).get("fromUser");
											try {
												fromuser = fromuser.fetch();
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											//ParseQuery query = ParseUser.getQuery().whereEqualTo("objectId", fromuser_id);
											
											String fromUser_name = null;
											String fromUser_avatar_url = null;
											String content = null;
											fromUser_name = fromuser.getString("displayname");     
										    ParseFile res = (ParseFile)fromuser.get("profilePictureSmall");
										    fromUser_avatar_url = res.getUrl();
										    pictureUrl.add(fromUser_avatar_url);
										    likedpeople.setAdapter(new ImageAdapter(GalleryActivity.this));
						            		
						            	}
									}
						            
						        } else {
						            Log.d("score", "Error: " + e.getMessage());
						        }
						    }
						});
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					

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
		getMenuInflater().inflate(R.menu.activity_gallery, menu);
		return true;
	}
	public class ImageAdapter extends BaseAdapter {
		/** The parent context */
		private Context myContext;
		// Put some images to project-folder: /res/drawable/
		// format: jpg, gif, png, bmp, ...
//		private int[] myImageIds = { R.drawable.image1, R.drawable.image2,
//				       R.drawable.image3 };

		/** Simple Constructor saving the 'parent' context. */
		public ImageAdapter(Context c) {
			this.myContext = c;
		}

		// inherited abstract methods - must be implemented
		// Returns count of images, and individual IDs
		public int getCount() {
			int res = 0;
			if (pictureUrl != null)
				res = pictureUrl.size();
			return res;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}
		// Returns a new ImageView to be displayed,
		@Override
		public View getView(int position, View convertView, 
				ViewGroup parent) {

			// Get a View to display image data 					
			ImageView iv = new ImageView(this.myContext);
			//iv.setImageResource(R.drawable.button_settings);
			imageLoader.DisplayImage(pictureUrl.get(position), iv);

			// Image should be scaled somehow
			//iv.setScaleType(ImageView.ScaleType.CENTER);
			//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);			
			//iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			//iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			//iv.setScaleType(ImageView.ScaleType.FIT_XY);
			//iv.setScaleType(ImageView.ScaleType.FIT_END);
			iv.setAdjustViewBounds(true);
            iv.setLayoutParams(new Gallery.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			// Set the Width & Height of the individual images
			//iv.setLayoutParams(new Gallery.LayoutParams(150, 150));

			return iv;
		}


	}// ImageAdapter

}
