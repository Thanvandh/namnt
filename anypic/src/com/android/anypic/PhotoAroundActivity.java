package com.android.anypic;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.android.data.Config;
import com.android.data.User;
import com.android.utils.GPSTracker;
import com.android.utils.ImageLoader;
import com.android.utils.XMLParser;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoAroundActivity extends Activity {
//	ImageView img_profile;
//	TextView myname;
	//View home_header;
	private ProgressDialog mProgressDialog;
	String mfacebookid;
	String mfacebook_user;
	ParseUser parse_user;
	XMLParser parser;
	Document doc;
	String xml;
	PullToRefreshListView lv;
	HomeRowAdapter adapter;
	ArrayList<HashMap<String, String>> menuItems;
	ProgressDialog pDialog;
	String avatar_url;
	String displayname;
	String current_user_id;
	private ImageLoader imageLoader;
	GPSTracker gps;
	ParseGeoPoint point;
	
	private String URL = "http://api.androidhive.info/list_paging/?page=1";

	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String OBJECT_ID = "objectid";
	static final String PHOTO_URL = "image";
	static final String MY_NAME = "myname";
	static final String AVATAR_URL = "avatar_url";
	static final String CURRENT_USER_ID = "current_user_id";
	static final String TIME_AGO = "time_ago";
	static final String KEY_NAME = "name";
	final int LIMIT_PHOTO = 3;
	final double maxDistance = 1;
	// Flag for current page
	int current_page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        home_header = inflater.inflate(R.layout.activity_home_header, null);
        imageLoader=new ImageLoader(getApplicationContext());
        
//        Intent intent1 = getIntent();
        //mfacebookid = i.getExtras().getString("facebookid");
        //mfacebook_user = i.getExtras().getString("facebook_user");
        parse_user = ParseUser.getCurrentUser();
//        parse_user.Login(mfacebookid + "namnt", mfacebookid + "namnt", handler);
		//mProgressDialog = ProgressDialog.show(HomeActivity.this, "", getString(R.string.loading), true);
//		Log.d("test","vao day ko");
        //update_home_header();
        current_user_id = parse_user.getObjectId();
        
		lv = (PullToRefreshListView) findViewById(R.id.list_home);
		lv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
		//lv.addHeaderView(home_header);
		menuItems = new ArrayList<HashMap<String, String>>();

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
		gps = new GPSTracker(PhotoAroundActivity.this);

		// check if GPS enabled		
        if(gps.canGetLocation()){
        	
        	double latitude = gps.getLatitude();
        	double longitude = gps.getLongitude();
        	point = new ParseGeoPoint(latitude, longitude);
        	
        	// \n is for new line
        	//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
        }else{
        	// can't get location
        	// GPS or Network is not enabled
        	// Ask user to enable GPS/network in settings
        	gps.showSettingsAlert();
        }
		
		ParseQuery query_photo = new ParseQuery("photo");
		query_photo.addDescendingOrder("createdAt");
		query_photo.whereWithinKilometers("location", point, maxDistance);
		query_photo.setLimit(LIMIT_PHOTO);
		query_photo.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> photoList, ParseException e) {
		        if (e == null) {
		            Log.d("test", "Retrieved " + photoList.size() + " photos");
		            for (int i = 0; i < photoList.size(); i++) {
						// creating new HashMap
		            	HashMap<String, String> map = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						// id not using any where
						
						ParseFile res = (ParseFile) photoList.get(i).get("image");
						long now = System.currentTimeMillis();
						long created = photoList.get(i).getUpdatedAt().getTime();
						String time_ago =  (String) DateUtils.getRelativeTimeSpanString(created, now, DateUtils.MINUTE_IN_MILLIS); 
						map.put(TIME_AGO, time_ago);
						map.put(OBJECT_ID, photoList.get(i).getObjectId());
						map.put(PHOTO_URL, res.getUrl()); 
						ParseUser user_photo = new ParseUser();
						user_photo = (ParseUser) photoList.get(i).get("user");
						try {
							user_photo = user_photo.fetch();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// ParseQuery query =
						// ParseUser.getQuery().whereEqualTo("objectId",
						// fromuser_id);

						String user_photo_name = null;
						String user_photo_avatar_url = null;
						String content = null;
						user_photo_name = user_photo.getString("displayname");
						ParseFile res_user_avatar = (ParseFile) user_photo
								.get("profilePictureMedium");
						user_photo_avatar_url = res_user_avatar.getUrl();
						map.put(MY_NAME, user_photo_name);
						map.put(AVATAR_URL, user_photo_avatar_url);
						// adding HashList to ArrayList
						menuItems.add(map);
						// Getting adapter
						
					}
		            adapter = new HomeRowAdapter(PhotoAroundActivity.this, menuItems);
					lv.setAdapter(adapter);
		            
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});
		
//		photo.getParseObject("post").fetchIfNeededInBackground(new GetCallback() {
//			  public void done(ParseObject object, ParseException e) {
//			    String title = post.getString("title");
//			  }
//			});
//		ParseObject photo = new ParseObject("photo");
//		ParseRelation relation_photo = photo.getRelation("user");
//		
//		relation_photo.add(parse_user);
//		relation_photo.getQuery().findInBackground(new FindCallback() {
//
//			@Override
//			public void done(List<ParseObject> arg0, ParseException arg1) {
//				// TODO Auto-generated method stub
//				if (arg1 != null) {
//			        // There was an error
//			      } else {
//			    	  
//			        // results have all the Posts the current user liked.
//			    	  for (int i = 0; i < arg0.size(); i++)
//			    	  {
//			    		  String objectid = arg0.get(i).getObjectId();
//			    		  Log.d("test", "objectid: " + objectid);
//			    	  }
//			    		  
//			      }
//			}
//		});
		
		

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
		
		
		/**
		 * Listening to listview single row selected
		 * **/
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 HashMap<String, String> item = new HashMap<String, String>();
			     item = menuItems.get((int)id);
			     
				// getting values from selected ListItem
//				String name = ((TextView) view.findViewById(R.id.name))
//						.getText().toString();
//
//				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						GalleryActivity.class);
				in.putExtra(PhotoAroundActivity.OBJECT_ID, item.get(PhotoAroundActivity.OBJECT_ID));
				in.putExtra(PhotoAroundActivity.MY_NAME, item.get(PhotoAroundActivity.MY_NAME));
				in.putExtra(PhotoAroundActivity.PHOTO_URL, item.get(PhotoAroundActivity.PHOTO_URL));
				in.putExtra(PhotoAroundActivity.AVATAR_URL, item.get(PhotoAroundActivity.AVATAR_URL));
				in.putExtra(PhotoAroundActivity.CURRENT_USER_ID, current_user_id);
				startActivity(in);
			}
		});
        
        

    }
//    public void update_home_header()
//    {
//    	ImageView img_profile = (ImageView)home_header.findViewById(R.id.img_profile);
//        TextView myname = (TextView)home_header.findViewById(R.id.txt_profilename);
//    	displayname = parse_user.getString("displayname");
//        myname.setText(displayname);       
//        ParseFile res = (ParseFile)parse_user.get("profilePictureMedium");
//        avatar_url = res.getUrl();
//        imageLoader.DisplayImage(avatar_url, img_profile);
////		if (res != null)
////		{
////		res.getDataInBackground(new GetDataCallback() {
////		  public void done(byte[] data, ParseException e) {
////		    if (e == null) {
////		    	//byte[] b = getPictureLargeForFacebookId(mfacebookid);
////		    	ByteArrayInputStream in = new ByteArrayInputStream(data);
////		    	BufferedInputStream f = new BufferedInputStream(in); 
////		    	avatar = BitmapFactory.decodeStream(f);
////		    	//Bitmap bmp=BitmapFactory.decodeByteArray(data,0,data.length);
////		        //Drawable d =new BitmapDrawable(getResources(),bmp);
////		    	//Drawable d = getResources().getDrawable(R.drawable.profile);
////		    	img_profile.setImageBitmap(avatar);
////		        
////		    } else {
////		      // something went wrong
////		    	Log.d("test","loi downfile|" + e.toString());
////
////		    }
////		  }
////		});
////		}
//
//    }
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
        	//map.put(OBJECT_ID, "100"); // id not using any where
			//map.put(KEY_NAME, "Thanh Nam");
        	// Getting adapter
			adapter = new HomeRowAdapter(PhotoAroundActivity.this, menuItems);
			lv.setAdapter(adapter);
			
            lv.onRefreshComplete();

            super.onPostExecute(result);
        }

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			// adding HashList to ArrayList
						menuItems.clear();
						current_page = 0;
						//update_home_header();
			            // Call onRefreshComplete when the list has been refreshed.
			        	ParseQuery query_photo = new ParseQuery("photo");
			    		query_photo.addDescendingOrder("createdAt");
			    		query_photo.whereWithinKilometers("location", point, maxDistance);
			    		query_photo.setLimit(LIMIT_PHOTO);
			    		try {
							List<ParseObject> photoList = query_photo.find();
							for (int i = 0; i < photoList.size(); i++) {
	    						// creating new HashMap
	    						HashMap<String, String> map = new HashMap<String, String>();
	    						// adding each child node to HashMap key => value
	    						// id not using any where
	    						
	    						ParseFile res = (ParseFile) photoList.get(i).get("image");
	    						long now = System.currentTimeMillis();
	    						long created = photoList.get(i).getUpdatedAt().getTime();
	    						String time_ago =  (String) DateUtils.getRelativeTimeSpanString(created, now, DateUtils.MINUTE_IN_MILLIS); 
	    						map.put(TIME_AGO, time_ago);
	    						map.put(OBJECT_ID, photoList.get(i).getObjectId()); 
	    						map.put(PHOTO_URL, res.getUrl()); 
	    						ParseUser user_photo = new ParseUser();
	    						user_photo = (ParseUser) photoList.get(i).get("user");
	    						try {
	    							user_photo = user_photo.fetch();
	    						} catch (ParseException e1) {
	    							// TODO Auto-generated catch block
	    							e1.printStackTrace();
	    						}
	    						// ParseQuery query =
	    						// ParseUser.getQuery().whereEqualTo("objectId",
	    						// fromuser_id);

	    						String user_photo_name = null;
	    						String user_photo_avatar_url = null;
	    						String content = null;
	    						user_photo_name = user_photo.getString("displayname");
	    						ParseFile res_user_avatar = (ParseFile) user_photo
	    								.get("profilePictureMedium");
	    						user_photo_avatar_url = res_user_avatar.getUrl();
	    						map.put(MY_NAME, user_photo_name);
	    						map.put(AVATAR_URL, user_photo_avatar_url);
	    						// adding HashList to ArrayList
	    						menuItems.add(map);
	    						
	    					}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		
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
					PhotoAroundActivity.this);
			pDialog.setMessage("Please wait..");
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... unused) {
			runOnUiThread(new Runnable() {
				public void run() {
					// increment current page
					current_page += LIMIT_PHOTO;
					
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
					ParseQuery query_photo = new ParseQuery("photo");
					query_photo.addDescendingOrder("createdAt");
					query_photo.whereWithinKilometers("location", point, maxDistance);
					query_photo.setLimit(LIMIT_PHOTO);
					query_photo.setSkip(current_page);
					query_photo.findInBackground(new FindCallback() {
					    public void done(List<ParseObject> photoList, ParseException e) {
					        if (e == null) {
					            Log.d("test", "Retrieved " + photoList.size() + " photos");
					            for (int i = 0; i < photoList.size(); i++) {
									// creating new HashMap
					            	HashMap<String, String> map = new HashMap<String, String>();
		    						// adding each child node to HashMap key => value
		    						// id not using any where
		    						
		    						ParseFile res = (ParseFile) photoList.get(i).get("image");
		    						long now = System.currentTimeMillis();
		    						long created = photoList.get(i).getUpdatedAt().getTime();
		    						String time_ago =  (String) DateUtils.getRelativeTimeSpanString(created, now, DateUtils.MINUTE_IN_MILLIS); 
		    						map.put(TIME_AGO, time_ago);
		    						map.put(OBJECT_ID, photoList.get(i).getObjectId()); 
		    						map.put(PHOTO_URL, res.getUrl()); 
		    						ParseUser user_photo = new ParseUser();
		    						user_photo = (ParseUser) photoList.get(i).get("user");
		    						try {
		    							user_photo = user_photo.fetch();
		    						} catch (ParseException e1) {
		    							// TODO Auto-generated catch block
		    							e1.printStackTrace();
		    						}
		    						// ParseQuery query =
		    						// ParseUser.getQuery().whereEqualTo("objectId",
		    						// fromuser_id);

		    						String user_photo_name = null;
		    						String user_photo_avatar_url = null;
		    						String content = null;
		    						user_photo_name = user_photo.getString("displayname");
		    						ParseFile res_user_avatar = (ParseFile) user_photo
		    								.get("profilePictureMedium");
		    						user_photo_avatar_url = res_user_avatar.getUrl();
		    						map.put(MY_NAME, user_photo_name);
		    						map.put(AVATAR_URL, user_photo_avatar_url);

									// adding HashList to ArrayList
									menuItems.add(map);
									// get listview current position - used to maintain scroll position
									
								}
					            int currentPosition = lv.getFirstVisiblePosition();
								
								// Appending new data to menuItems ArrayList
								adapter = new HomeRowAdapter(
										PhotoAroundActivity.this,
										menuItems);
								lv.setAdapter(adapter);
								
								// Setting new scroll position
								lv.setSelectionFromTop(currentPosition + 1, 0);
					            
					        } else {
					            Log.d("score", "Error: " + e.getMessage());
					        }
					    }
					});
					
					

				}
			});

			return (null);
		}
		
		
		protected void onPostExecute(Void unused) {
			// closing progress dialog
			pDialog.dismiss();
		}
	}
    public byte[] getPictureForFacebookId(String facebookId) {

	    Drawable picture = null;
	    InputStream openStream = null;
	    byte[] data = null;
	    String imageURL = "https://graph.facebook.com/" + facebookId  + "/picture";
	    final HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        HttpConnectionParams.setConnectionTimeout(httpParameters, 7000);

        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        HttpConnectionParams.setSoTimeout(httpParameters, 10000);

        final HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse response;
		try {
			response = client.execute(new HttpGet(imageURL));
			final HttpEntity entity = response.getEntity();
	        //Getting the input stream of the imae content.
	        final InputStream imageContentStream = entity.getContent();
	        data = IOUtils.toByteArray(imageContentStream);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //Getting the response entity.
        
//	    int contentLength = 0;
//	    byte[] data = null;
//	    try {
//	    	URL u = new URL("https://graph.facebook.com/" + facebookId  + "/picture");
//			contentLength = u.openConnection().getContentLength();
//			openStream = u.openStream();
//	    } catch (Exception e) {        
//	     e.printStackTrace();
//	     return null;
//
//	    }
//	    data = new byte[contentLength];
//	    try {
//			openStream.read(data);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    try {
//			openStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	    return data;
	}
	public byte[] getPictureLargeForFacebookId(String facebookId) {

		Drawable picture = null;
	    InputStream openStream = null;
	    byte[] data = null;
	    String imageURL = "https://graph.facebook.com/" + facebookId  + "/picture?type=large";
	    final HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        HttpConnectionParams.setConnectionTimeout(httpParameters, 7000);

        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        HttpConnectionParams.setSoTimeout(httpParameters, 10000);

        final HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse response;
		try {
			response = client.execute(new HttpGet(imageURL));
			final HttpEntity entity = response.getEntity();
	        //Getting the input stream of the imae content.
	        final InputStream imageContentStream = entity.getContent();
	        data = IOUtils.toByteArray(imageContentStream);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//	    Drawable picture = null;
//	    InputStream openStream = null;
//	    int contentLength = 0;
//	    byte[] data = null;
//	    try {
//	    	URL u = new URL("https://graph.facebook.com/" + facebookId + "/picture?type=large");
//			contentLength = u.openConnection().getContentLength();
//			openStream = u.openStream();
//	    } catch (Exception e) {        
//	     e.printStackTrace();
//	     return null;
//
//	    }
//	    data = new byte[contentLength];
//	    try {
//			openStream.read(data);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    try {
//			openStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	    return data;
	}
	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// LeftNavBar bar =
			// (LeftNavBarService.instance()).getLeftNavBar((Activity)
			// MainShow.this);
			// bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.leftnav_bar_background_dark));
			String sSignUpSuccess = getString(R.string.signup_success);
			String sSignUpFailed = getString(R.string.signup_failed);
			String sLoginSuccess = getString(R.string.login_success);
			String sLoginFailed = getString(R.string.login_failed);
			if (msg.what == Config.Message_SignUp_Success) {
				Log.d("test", "SignUp Success");
				byte[] dataSmall = getPictureForFacebookId(mfacebookid);
				ParseFile profilePictureSmall = new ParseFile("profilePictureSmall.jpg", dataSmall);
				try {
					profilePictureSmall.save();
				} catch (com.parse.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] dataMedium = getPictureLargeForFacebookId(mfacebookid);
				ParseFile profilePictureMedium = new ParseFile("profilePictureMedium.jpg", dataMedium);
				try {
					profilePictureMedium.save();
				} catch (com.parse.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String displayname = mfacebook_user;
//				parse_user.setUserDisplayName(displayname);
//				parse_user.setFacebookID(mfacebookid);
//				parse_user.setProfilePictureSmall(profilePictureSmall);
//				parse_user.setProfilePictureMedium(profilePictureMedium);
//				parse_user.saveInServer();
				
//				Toast.makeText(Login.this, sLoginSuccess, Toast.LENGTH_LONG)
//						.show();
				
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
//				updateUI();
				//parse_user.Login(facebook_user.getId() + "namnt", facebook_user.getId() + "namnt", handler);


			}
			if (msg.what == Config.Message_SignUp_Failed) {
				Log.d("test", "SignUp Failed");
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
				String error_desc = msg.getData().getString("error_desc");
				Toast.makeText(PhotoAroundActivity.this, sSignUpFailed + ":" + error_desc,
						Toast.LENGTH_LONG).show();

			}
			if (msg.what == Config.Message_Login_Success) {
				//Log.d("test", "Login Success " + parse_user.getUserDisplayName());
				
//				Toast.makeText(Login.this, sLoginSuccess, Toast.LENGTH_LONG)
//						.show();
				
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
//				updateUI();

				
				

			}
			if (msg.what == Config.Message_Login_Failed) {
				Log.d("test", "SignUp Failed");
//				if (mProgressDialog.isShowing()) {
//					mProgressDialog.dismiss();
//				}
				String email="email@com.vn";
//				try {
//					email = facebook_user.getInnerJSONObject().getString("email");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				//parse_user.SignUp(mfacebookid + "namnt", mfacebookid + "namnt",email ,handler);

			}
		}
	};
//	private void openQuitDialog(){
//	  	  AlertDialog.Builder quitDialog 
//	  	   = new AlertDialog.Builder(PhotoAroundActivity.this);
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

}
