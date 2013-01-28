package com.android.anypic;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.android.utils.ImageLoader;
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
	    public ImageLoader imageLoader; 
	    
		    
	    public HomeRowAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        imageLoader=new ImageLoader(activity.getApplicationContext());
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
	        ImageView photo = (ImageView)vi.findViewById(R.id.list_home_row_bottom);
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);
	        
	        imageLoader.DisplayImage(item.get(HomeActivity.PHOTO_URL), photo);
	
	        
	        
	        //Setting all values in listview
	        name.setText(item.get(HomeActivity.OBJECT_ID));
	        return vi;
	    }
}