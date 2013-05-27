package com.android.drumbeat;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.drumbeat.utils.DatabaseHandler;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MoreRowAdapter extends BaseAdapter {
    
	 private MainActivity activity;
	    
	    private static LayoutInflater inflater=null;
	    
	    Drawable[] logo = new Drawable [8];
	    String[] header = new String [8];
	    String[] content = new String [8];
	    String[] price = new String [8];
		    
	    public MoreRowAdapter(MainActivity a) {
	        activity = a;
	        logo[0] = activity.getResources().getDrawable(R.drawable.rnb_essentials1_icon);
	        logo[1] = activity.getResources().getDrawable(R.drawable.world_beats_icon);
	        logo[2] = activity.getResources().getDrawable(R.drawable.tambourine10pack_icon);
	        logo[3] = activity.getResources().getDrawable(R.drawable.latin_jazz_icon);
	        logo[4] = activity.getResources().getDrawable(R.drawable.heavy_metal1_icon);
	        logo[5] = activity.getResources().getDrawable(R.drawable.country1_icon);
	        logo[6] = activity.getResources().getDrawable(R.drawable.blues1_icon);
	        logo[7] = activity.getResources().getDrawable(R.drawable.artificial1_icon);
	        
	        header[0] = activity.getResources().getString(R.string.string_more_text_header0);
	        header[1] = activity.getResources().getString(R.string.string_more_text_header1);
	        header[2] = activity.getResources().getString(R.string.string_more_text_header2);
	        header[3] = activity.getResources().getString(R.string.string_more_text_header3);
	        header[4] = activity.getResources().getString(R.string.string_more_text_header4);
	        header[5] = activity.getResources().getString(R.string.string_more_text_header5);
	        header[6] = activity.getResources().getString(R.string.string_more_text_header6);
	        header[7] = activity.getResources().getString(R.string.string_more_text_header7);
	        
	        content[0] = activity.getResources().getString(R.string.string_more_text_content0);
	        content[1] = activity.getResources().getString(R.string.string_more_text_content1);
	        content[2] = activity.getResources().getString(R.string.string_more_text_content2);
	        content[3] = activity.getResources().getString(R.string.string_more_text_content3);
	        content[4] = activity.getResources().getString(R.string.string_more_text_content4);
	        content[5] = activity.getResources().getString(R.string.string_more_text_content5);
	        content[6] = activity.getResources().getString(R.string.string_more_text_content6);
	        content[7] = activity.getResources().getString(R.string.string_more_text_content7);
	        
	        price[0] = activity.getResources().getString(R.string.string_more_text_price0);
	        price[1] = activity.getResources().getString(R.string.string_more_text_price1);
	        price[2] = activity.getResources().getString(R.string.string_more_text_price2);
	        price[3] = activity.getResources().getString(R.string.string_more_text_price3);
	        price[4] = activity.getResources().getString(R.string.string_more_text_price4);
	        price[5] = activity.getResources().getString(R.string.string_more_text_price5);
	        price[6] = activity.getResources().getString(R.string.string_more_text_price6);
	        price[7] = activity.getResources().getString(R.string.string_more_text_price7);
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        
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
	            vi = inflater.inflate(R.layout.row_main_body_more_listview, null);

	        TextView textheader = (TextView)vi.findViewById(R.id.row_main_body_more_listview_text_header); // title
	        TextView textcontent = (TextView)vi.findViewById(R.id.row_main_body_more_listview_text_content);
	        TextView textprice = (TextView)vi.findViewById(R.id.row_main_body_more_listview_text_price);
	        ImageView imglogo = (ImageView)vi.findViewById(R.id.row_main_body_more_listview_logo);
	        
	        textheader.setText(header[position]);
	        textcontent.setText(content[position]);
	        textprice.setText(price[position]);
	        imglogo.setImageDrawable(logo[position]);
	        
	        return vi;
	    }



		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return logo.length;
		}
}