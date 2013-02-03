package com.android.anypic;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.utils.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TimelineRowAdapter extends BaseAdapter {
    
	 private Activity activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    public ImageLoader imageLoader; 
	    
		    
	    public TimelineRowAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.list_row_timeline, null);

	        TextView name = (TextView)vi.findViewById(R.id.list_timeline_row_displayname); // title
	        ImageView avatar = (ImageView)vi.findViewById(R.id.list_timeline_row_pic);
	        TextView content = (TextView)vi.findViewById(R.id.list_timeline_row_action);
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);
	        
	        //Setting all values in listview
	        name.setText(item.get(TimelineActivity.KEY_FROM_USER_NAME));
	        content.setText(item.get(TimelineActivity.KEY_CONTENT));
	        imageLoader.DisplayImage(item.get(TimelineActivity.KEY_FROM_USER_AVATAR_URL), avatar);
	        return vi;
	    }
}