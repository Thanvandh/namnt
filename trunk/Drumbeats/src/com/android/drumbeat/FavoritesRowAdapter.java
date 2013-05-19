package com.android.drumbeat;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.drumbeat.utils.DatabaseHandler;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FavoritesRowAdapter extends BaseAdapter {
    
	 private MainActivity activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    private boolean meditable;
	    
		    
	    public FavoritesRowAdapter(MainActivity a, ArrayList<HashMap<String, String>> d, boolean editable) {
	        activity = a;
	        data=d;
	        meditable = editable;
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
	    public void removefavorite(int position) {
			activity.openRemoveDialog(position);
		}
	    
	    
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.row_main_body_list_favorite_file, null);

	        TextView name = (TextView)vi.findViewById(R.id.row_main_body_list_favorite_file_name); // title
	        TextView folder = (TextView)vi.findViewById(R.id.row_main_body_list_favorite_file_folder);
	        ImageView bt_delete = (ImageView)vi.findViewById(R.id.row_main_body_list_favorite_file_bt_delete);
	        ImageView bt_sort = (ImageView)vi.findViewById(R.id.row_main_body_list_favorite_file_bt_sort);
	        
	        if (meditable){
	        	bt_delete.setVisibility(View.VISIBLE);
	        	bt_sort.setVisibility(View.VISIBLE);
	        } else {
	        	bt_delete.setVisibility(View.GONE);
	        	bt_sort.setVisibility(View.GONE);
	        }
	        final int fposistion = position;
	        bt_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					removefavorite(fposistion);
				}
			});
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);
	        
	        //Setting all values in listview
	        name.setText(item.get(MainActivity.KEY_NAME));
	        folder.setText(item.get(MainActivity.KEY_FOLDER));
	        
	        return vi;
	    }
}