package com.android.anypic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
import org.json.JSONObject;

import com.android.data.Config;
import com.android.data.User;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;


import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	Button btSignUp;
	Button btLogin;
	Button btLogout;
	private TextView greeting;
	//LoginButton btFacebook_Login;
	Button btFacebook_Login;
	//private UserInfoChangedCallback userInfoChangedCallback;
	private ProgressDialog mProgressDialog;
	ParseUser parse_user;
	String mfacebookid;
	String mdisplayname;
	//private GraphUser facebook_user;
	//facebook id co quan 473769649348309
	//key hash co quan 3KuOTz+q0bYAuYpP3+nPk3d2KKE=
	//facebook id home 493005900750680
	//key hash home WnQe2vskUu3ylTsSqwcaxLzvkNE=
	

	final int REQUESTCODE_HOME_ACTIVITY = 259;
	
	static final int Message_Put_PictureSmall = 1;
	static final int Message_Put_PicutreMedium = 2;
	static final int Message_Save_All = 3;
	static final int Message_Start_Home_Activity = 4;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		final String sLoading = getString(R.string.loading);
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.android.anypic", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		Parse.initialize(this, "6UGj4uL8HU43HNS0R2XVlRlPh5CN5lXGtOJOUM70",
				"L7A6SE6RwIn8AjruYFac1jOLhgkIFa4lnm5J52Gb");
		ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
//		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
//		greeting = (TextView) findViewById(R.id.txt_greeting);
//		btSignUp = (Button) findViewById(R.id.btSignUp);
//		btSignUp.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				// mProgressDialog = ProgressDialog.show(Login.this, "",
//				// sLoading, true);
//				// Thread progress = new Thread()
//				// {
//				// @Override
//				// public void run() {
//				// user.SignUp("namnt", "123456", "boy_blue_heart@yahoo.com",
//				// handler);
//				// super.run();
//				//
//				// }
//				// };
//				// progress.start();
//				parse_user.SignUp("namnt", "123456",
//						"boy_blue_heart@yahoo.com", handler);
//				mProgressDialog = ProgressDialog.show(Login.this, "", sLoading,
//						true);
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//		btLogin = (Button) findViewById(R.id.btLogin);
//		btLogin.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				parse_user.Login("namnt", "123456", handler);
//				;
//				mProgressDialog = ProgressDialog.show(Login.this, "", sLoading,
//						true);
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//		btLogout = (Button) findViewById(R.id.btLogout);
//		btLogout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				String slogout = getString(R.string.logout_success);
//				parse_user.Logout();
//				Toast.makeText(Login.this, slogout, Toast.LENGTH_SHORT).show();
//				// TODO Auto-generated method stub
//
//			}
//		});
		btFacebook_Login = (Button) findViewById(R.id.bt_facebooklogin);
		btFacebook_Login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mProgressDialog = ProgressDialog.show(Login.this, "", getString(R.string.loading), true);
				ParseFacebookUtils.logIn(Arrays.asList("email", Permissions.Friends.ABOUT_ME),Login.this, new LogInCallback() {
					
					@Override
					public void done(ParseUser user, com.parse.ParseException e) {
						// TODO Auto-generated method stub
						if (user == null) {
						      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
						      if (mProgressDialog.isShowing()) {
									mProgressDialog.dismiss();
						      }
						      Toast.makeText(Login.this, "The user cancelled the Facebook login.", Toast.LENGTH_LONG).show();
						    } else if (user.isNew()) {
						    	parse_user = user;
						      Log.d("MyApp", "User signed up and logged in through Facebook!");
						      //ParseFacebookUtils.getSession(

						        try {
									JSONObject result = new JSONObject(ParseFacebookUtils.getFacebook()
									    .request("me"));
									mfacebookid = result.getString("id");
									mdisplayname = result.getString("name");
									parse_user.put("displayname",mdisplayname);
									parse_user.put("facebookid",mfacebookid);
									parse_user.saveInBackground(new SaveCallback() {
										
										@Override
										public void done(com.parse.ParseException e) {
											// TODO Auto-generated method stub
											handler.sendEmptyMessage(Message_Put_PictureSmall);
											
										}
									});
//									
									
									
									//user.put("profilePictureSmall", profilePictureSmall);
									//user.put("profilePictureMedium", profilePictureMedium);

									
								} catch (MalformedURLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						      
						    } else {
						      Log.d("MyApp", "User logged in through Facebook!");
						      if (mProgressDialog.isShowing()) {
									mProgressDialog.dismiss();
								}
								Intent i = new Intent(Login.this, MainActivity.class);
//								i.putExtra("facebookid", facebookid);
//								i.putExtra("facebook_user", displayname);
								startActivity(i);
								finish();
//						      Bundle args = new Bundle();
//						        try {
////									JSONObject result = new JSONObject(ParseFacebookUtils.getFacebook()
////									    .request("me"));
////									//JSONObject info = result.getJSONObject("location");
////									String facebookid = result.getString("id");
////									String displayname = result.getString("name");
////									Log.d("MyApp", "User logged in through Facebook + " + facebookid + displayname);
//									if (mProgressDialog.isShowing()) {
//										mProgressDialog.dismiss();
//									}
//									Intent i = new Intent(Login.this, HomeActivity.class);
////									i.putExtra("facebookid", facebookid);
////									i.putExtra("facebook_user", displayname);
//									startActivity(i);
//									finish();
//								} catch (MalformedURLException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								} catch (JSONException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								} catch (IOException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
						    }
					}
				});
//				ParseFacebookUtils.logIn(this, new LogInCallback() {
//					  @Override
//					  public void done(ParseUser user, ParseException err) {
//					    if (user == null) {
//					      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
//					    } else if (user.isNew()) {
//					      Log.d("MyApp", "User signed up and logged in through Facebook!");
//					    } else {
//					      Log.d("MyApp", "User logged in through Facebook!");
//					    }
//					  }
//					});
				
			}
		});
		
//		btFacebook_Login = (LoginButton) findViewById(R.id.bt_facebooklogin);
//		btFacebook_Login
//				.setUserInfoChangedCallback(new UserInfoChangedCallback() {
//
//					@Override
//					public void onUserInfoFetched(GraphUser user) {
//						// TODO Auto-generated method stub
//						if (user != null)
//							Log.d("test", "user khac null");
//						else
//							Log.d("test", "user null");
//						Login.this.facebook_user = user;
//						updateUI();
//
//					}
//				});
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
//        if (requestCode == REQUESTCODE_HOME_ACTIVITY)
//        	onDestroy();
    }
    private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// LeftNavBar bar =
			// (LeftNavBarService.instance()).getLeftNavBar((Activity)
			// MainShow.this);
			// bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.leftnav_bar_background_dark));
			
			if (msg.what == Message_Put_PictureSmall) {
				Log.d("test", "Message_Put_PictureSmall");
				byte[] dataSmall = getPictureForFacebookId(mfacebookid);
				final ParseFile profilePictureSmall = new ParseFile("profilePictureSmall.jpg", dataSmall);
				profilePictureSmall.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(com.parse.ParseException e) {
						// TODO Auto-generated method stub
						Log.d("test", "picture small");
						parse_user.put("profilePictureSmall", profilePictureSmall);
						handler.sendEmptyMessage(Message_Put_PicutreMedium);
					}
				});
				
					//parse_user.Login(facebook_user.getId() + "namnt", facebook_user.getId() + "namnt", handler);


			}
			if (msg.what == Message_Put_PicutreMedium) {
				Log.d("test", "Message_Put_PicutreMedium");
//				Drawable d = getResources().getDrawable(R.drawable.profile); // the drawable (Captain Obvious, to the rescue!!!)
//				BitmapDrawable bd = (BitmapDrawable) d;
//				Bitmap bitmap = bd.getBitmap();
//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//				byte[] dataMedium = stream.toByteArray();


				byte[] dataMedium = getPictureLargeForFacebookId(mfacebookid);
				final ParseFile profilePictureMedium = new ParseFile("profilePictureMedium1.jpg", dataMedium);
				profilePictureMedium.saveInBackground(new SaveCallback() {
					
					@SuppressLint("ParserError")
					@Override
					public void done(com.parse.ParseException e) {
						// TODO Auto-generated method stub
						Log.d("test", "picture medium");
						parse_user.put("profilePictureMedium", profilePictureMedium);
						handler.sendEmptyMessage(Message_Save_All);
					}
				});

			}
			if (msg.what == Message_Save_All) {
				//Log.d("test", "Login Success " + parse_user.getUserDisplayName());
				
//				Toast.makeText(Login.this, sLoginSuccess, Toast.LENGTH_LONG)
//						.show();
				
				parse_user.saveInBackground(new SaveCallback() {
					
					@SuppressLint("ParserError")
					@Override
					public void done(com.parse.ParseException e) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(Message_Start_Home_Activity);
					}
				});

				
				

			}
			if (msg.what == Message_Start_Home_Activity) {
				
					if (mProgressDialog.isShowing()) {
						mProgressDialog.dismiss();
					}
					Intent i = new Intent(Login.this, MainActivity.class);
					i.putExtra("facebookid", mfacebookid);
					i.putExtra("facebook_user", mdisplayname);
					startActivity(i);
//					try {
//						ParseFacebookUtils.getFacebook().logout(Login.this);
//					} catch (MalformedURLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					finish();
	

			}
		}
	};
    
//	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
////        if (pendingAction != PendingAction.NONE &&
////                (exception instanceof FacebookOperationCanceledException ||
////                exception instanceof FacebookAuthorizationException)) {
////                new AlertDialog.Builder(HelloFacebookSampleActivity.this)
////                    .setTitle(R.string.cancelled)
////                    .setMessage(R.string.permission_not_granted)
////                    .setPositiveButton(R.string.ok, null)
////                    .show();
////            pendingAction = PendingAction.NONE;
////        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
////            //handlePendingAction();
////        }
//        updateUI();
//    }
//	private void updateUI() {
//		Session session = Session.getActiveSession();
//		boolean enableButtons = (session != null && session.isOpened());
//		Log.v("test", "updateUI" + enableButtons);
//
//		if (enableButtons && facebook_user != null) {
//			//profilePictureView.setProfileId(facebook_user.getId());
//			//greeting.setText(facebook_user.getName());
////			facebook_user.
////			parse_user.Logout();
//			Log.d("test", "facebook_user" + facebook_user.getId());
////			if (!parse_user.getStatusLogin())
////			{
////				Log.d("test", "status_false" );
////				parse_user.Login(facebook_user.getId() + "namnt", facebook_user.getId() + "namnt", handler);
////				mProgressDialog = ProgressDialog.show(Login.this, "", getString(R.string.loading), true);
////			}
////			else
////			{
////				Log.d("test", "status_true" );
//				Intent i = new Intent(Login.this, HomeActivity.class);
//				i.putExtra("facebookid", facebook_user.getId());
//				i.putExtra("facebook_user", facebook_user.getName());
//				startActivity(i);
//				session.close();
//				finish();
////			}
////			Intent i = new Intent(this, HomeActivity.class);
////			startActivity(i);
//		} else {
//			//parse_user.Logout();
//			//profilePictureView.setProfileId(null);
//			//greeting.setText(null);
//		}
//	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
