package com.android.data;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class User {
	static ParseUser user;
	boolean bSignUp = false;
	boolean bLogin = false;
	boolean finishedSignUp = false;
	boolean finishedLogin = false;
	static byte[] tmp = null;

	public User() {
		user = new ParseUser();
	}
	public User(ParseUser u)
	{
		user = u;
	}
	public static ParseUser getUser()
	{
		return user;
	}
	public boolean getStatusLogin()
	{
		return user.isAuthenticated();
	}
	public String getUserDisplayName()
	{
		return user.getString("displayname");
	}
	public static String getFacebookID()
	{
		return user.getString("facebookid");
	}
	public static List getFacebookFriends()
	{
		return user.getList("facebookfriends");
	}
	public static byte[] getProfilePictureMedium()
	{
		Log.d("test","getProfilePictureMedium");
		ParseFile res = (ParseFile)user.get("profilePictureMedium");
		res.getDataInBackground(new GetDataCallback() {
		  public void done(byte[] data, ParseException e) {
		    if (e == null) {
		    	tmp =  data;
		    } else {
		      // something went wrong
		    	Log.d("test","loi downfile|" + e.toString());

		    }
		  }
		});
		return tmp;
	}
	public static byte[] getProfilePictureSmall()
	{
		Log.d("test","profilePictureSmall");
		ParseFile res = (ParseFile)user.get("profilePictureSmall");
		if (res != null)
		{
		res.getDataInBackground(new GetDataCallback() {
		  public void done(byte[] data, ParseException e) {
		    if (e == null) {
		    	tmp =  data;
		    } else {
		      // something went wrong
		    	Log.d("test","loi downfile|" + e.toString());

		    }
		  }
		});
		}
		return tmp;
	}
	
	public static void setUserDisplayName(String displayname)
	{
		user.put("displayname", displayname);
	}
	public static void setFacebookID(String facebookid)
	{
		user.put("facebookid", facebookid);
	}
	public static void setFacebookFriends(String facebookfriends)
	{
		user.put("facebookfriends", facebookfriends);
	}
	public static void setProfilePictureMedium(ParseFile profilePictureMedium)
	{
		user.put("profilePictureMedium", profilePictureMedium);
	}
	public static void setProfilePictureSmall(ParseFile profilePictureSmall)
	{
		user.put("profilePictureSmall", profilePictureSmall);
	}
	public static void saveInServer()
	{
		try {
			user.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SignUp(String Username, String Password, String Email, final Handler handler)
	{
		user.setUsername(Username);
		user.setPassword(Password);
		user.setEmail(Email);
		 
		user.signUpInBackground(new SignUpCallback( ) {
			
			@Override
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					Log.v("client", "signUpInBackground true");
					handler.sendEmptyMessage(Config.Message_SignUp_Success);
				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					Log.v("client", "signUpInBackground false " + e.toString());
					Message msg = new Message();
					Bundle bundle = new Bundle();
					msg.what = Config.Message_SignUp_Failed;
					bundle.putString("error_desc", e.toString());
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
			}
		});
		
	}
	public void Login(String Username, String Password, final Handler handler)
	{
		ParseUser.logInInBackground(Username, Password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// Hooray! The user is logged in.
					handler.sendEmptyMessage(Config.Message_Login_Success);

				} else {
					// Signup failed. Look at the ParseException to see what
					// happened.
					Message msg = new Message();
					Bundle bundle = new Bundle();
					msg.what = Config.Message_Login_Failed;
					bundle.putString("error_desc", e.toString());
					msg.setData(bundle);
					handler.sendMessage(msg);
				}

			}
		});

	}
	public void Logout()
	{
		ParseUser.logOut();
	}
}
