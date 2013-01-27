package com.android.anypic;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

import com.android.data.Config;
import com.android.data.User;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class HomeActivity extends Activity {
	ImageView img_profile;
	TextView myname;
	private ProgressDialog mProgressDialog;
	String mfacebookid;
	String mfacebook_user;
	ParseUser parse_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        img_profile = (ImageView) findViewById(R.id.img_profile);
        myname = (TextView) findViewById(R.id.txt_profilename);
        Intent i = getIntent();
        //mfacebookid = i.getExtras().getString("facebookid");
        //mfacebook_user = i.getExtras().getString("facebook_user");
        parse_user = ParseUser.getCurrentUser();
//        parse_user.Login(mfacebookid + "namnt", mfacebookid + "namnt", handler);
		//mProgressDialog = ProgressDialog.show(HomeActivity.this, "", getString(R.string.loading), true);
//		Log.d("test","vao day ko");
		updateUI();
        
        

    }
    public void updateUI()
    {
    	String displayname = parse_user.getString("displayname");
        myname.setText(displayname);       
        ParseFile res = (ParseFile)parse_user.get("profilePictureMedium");
		if (res != null)
		{
		res.getDataInBackground(new GetDataCallback() {
		  public void done(byte[] data, ParseException e) {
		    if (e == null) {
		    	//byte[] b = getPictureLargeForFacebookId(mfacebookid);
		    	ByteArrayInputStream in = new ByteArrayInputStream(data);
		    	BufferedInputStream f = new BufferedInputStream(in); 
		    	Bitmap bmp = BitmapFactory.decodeStream(f);
		    	//Bitmap bmp=BitmapFactory.decodeByteArray(data,0,data.length);
		        Drawable d =new BitmapDrawable(getResources(),bmp);
		    	//Drawable d = getResources().getDrawable(R.drawable.profile);
		    	img_profile.setImageDrawable(d);
		        
		    } else {
		      // something went wrong
		    	Log.d("test","loi downfile|" + e.toString());

		    }
		  }
		});
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
				updateUI();
				//parse_user.Login(facebook_user.getId() + "namnt", facebook_user.getId() + "namnt", handler);


			}
			if (msg.what == Config.Message_SignUp_Failed) {
				Log.d("test", "SignUp Failed");
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
				String error_desc = msg.getData().getString("error_desc");
				Toast.makeText(HomeActivity.this, sSignUpFailed + ":" + error_desc,
						Toast.LENGTH_LONG).show();

			}
			if (msg.what == Config.Message_Login_Success) {
				//Log.d("test", "Login Success " + parse_user.getUserDisplayName());
				
//				Toast.makeText(Login.this, sLoginSuccess, Toast.LENGTH_LONG)
//						.show();
				
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
				updateUI();

				
				

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

}
