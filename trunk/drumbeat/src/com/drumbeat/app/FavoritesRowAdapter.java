package com.drumbeat.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.drumbeat.utils.DatabaseHandler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoritesRowAdapter extends BaseAdapter {
    
	 private Activity activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    
		    
	    public FavoritesRowAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.row_favorites, null);

	        TextView name = (TextView)vi.findViewById(R.id.row_favorites_name); // title
	        TextView folder = (TextView)vi.findViewById(R.id.row_favorites_folder);
	        
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);
	        
	        //Setting all values in listview
	        name.setText(item.get(FavoritesActivity.KEY_NAME));
	        folder.setText(item.get(FavoritesActivity.KEY_FOLDER));
	        
	        return vi;
	    }
}