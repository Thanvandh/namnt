package com.drumbeat.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter {
	private Context mContext;
	int mheight;
	int mlastheight;
	// Keep all Images in array
	public static String[] mTemporary = {"175","180","185","190","155","160","165","170", "135","140","145","150", "115","120","125","130", "95","100","105","110", "75","80","85","90","60","65","70",""};
	public static String[] arrayrate = {"60","65","70","75","80","85","90","95", "100","105","110","115", "120","125","130","135", "140","145","150","155", "160","165","170","175","180","185","190"};
	public static int[][] matrix = {{0,1,2,3},{4,5,6,7},{8,9,10,11},{12,13,14,15},{16,17,18,19},{20,21,22,23},{24,25,26,27}};
	
	// Constructor
	public TextAdapter(Context c, int height, int lastheight){
		mContext = c;
		mheight = height;
		mlastheight = lastheight;
	}

	@Override
	public int getCount() {
		return mTemporary.length;
	}

	@Override
	public Object getItem(int position) {
		return mTemporary[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		TextView textview = new TextView(mContext);
        textview.setText(mTemporary[position]);
        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(Color.WHITE);
        textview.setTextSize(16);
        textview.setTypeface(null, Typeface.BOLD);
        if (position == 24 || position == 25 || position == 26 || position == 27)
        	textview.setHeight(mlastheight);
        else 
        	textview.setHeight(mheight);
        //textview.setBackgroundResource(R.color.blue_item_grid);
        return textview;
	}

}
