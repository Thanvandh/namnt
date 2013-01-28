package com.android.anypic;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeRowAdapter extends BaseAdapter {
    
	 private Activity activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    
		    
	    public HomeRowAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    public int getCount() {
	        return data.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        Log.v("test", "vao day kieu gi " + position);
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.list_row_home, null);

	        TextView name = (TextView)vi.findViewById(R.id.list_home_row_displayname); // title
	        final ImageView photo = (ImageView)vi.findViewById(R.id.list_home_row_bottom);
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);
	        
//	        ParseQuery query = new ParseQuery("photo");
//	        query.getInBackground(item.get(HomeActivity.OBJECT_ID), new GetCallback() {
//				
//				@Override
//				public void done(ParseObject object, ParseException e) {
//					// TODO Auto-generated method stub
//					if (e == null) {
//		        	      // object will be your game score
//						ParseFile res = (ParseFile)object.get("image");
//						if (res != null)
//						{
//						res.getDataInBackground(new GetDataCallback() {
//						  public void done(byte[] data, ParseException e) {
//						    if (e == null) {
//						    	Log.d("test","download file thanh cong");
//						    	//byte[] b = getPictureLargeForFacebookId(mfacebookid);
//						    	ByteArrayInputStream in = new ByteArrayInputStream(data);
//						    	BufferedInputStream f = new BufferedInputStream(in); 
//						    	Bitmap bmp = BitmapFactory.decodeStream(f);
//						    	//Bitmap bmp=BitmapFactory.decodeByteArray(data,0,data.length);
//						        //Drawable d =new BitmapDrawable(getResources(),bmp);
//						    	//Drawable d = getResources().getDrawable(R.drawable.profile);
//						    	photo.setImageBitmap(bmp);
//						        
//						    } else {
//						      // something went wrong
//						    	Log.d("test","loi downfile|" + e.toString());
//
//						    }
//						  }
//						});
//						}
//		        	    } else {
//		        	      // something went wrong
//		        	    }
//				}
//			}); 
	
	        
	        
	        //Setting all values in listview
	        name.setText(item.get(HomeActivity.OBJECT_ID));
	        return vi;
	    }
}