package com.drumbeat.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.drumbeat.utils.DatabaseHandler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListviewLevel2RowAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	boolean [] favorites;

	public ListviewLevel2RowAdapter(Activity a,
			ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		favorites = new boolean[getCount()];
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

	public boolean getfavorite(String folder, String file) {
		boolean res = false;
		DatabaseHandler myDbHelper = new DatabaseHandler(activity.getApplicationContext());
		myDbHelper.openDataBase();
		res = myDbHelper.getfavorite(folder, file);
		myDbHelper.close();
		return res;
	}

	public void addfavorite(String folder, String file) {
		DatabaseHandler myDbHelper = new DatabaseHandler(activity.getApplicationContext());
		myDbHelper.openDataBase();
		myDbHelper.addfavorite(folder, file);
		myDbHelper.close();
	}

	public void removefavorite(String folder, String file) {
		DatabaseHandler myDbHelper = new DatabaseHandler(activity.getApplicationContext());
		myDbHelper.openDataBase();
		myDbHelper.removefavorite(folder, file);
		myDbHelper.close();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.row_listview2, null);

		TextView number = (TextView) vi.findViewById(R.id.row_listview2_number);
		TextView name = (TextView) vi.findViewById(R.id.row_listview2_name); // title
		final ImageView bt_favorite = (ImageView) vi
				.findViewById(R.id.row_listview2_favorites);

		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);
		final String folder = item.get(ListviewLevel2.KEY_FOLDER);
		final String file = item.get(ListviewLevel2.KEY_NAME);
		final int fposistion = position;
		favorites[fposistion] = getfavorite(folder, file);
		if (favorites[position])
			bt_favorite.setImageResource(R.drawable.fav_star_on);
		else
			bt_favorite.setImageResource(R.drawable.fav_star_off);
		bt_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (favorites[fposistion]) {
					//Log.d("test", "off" + bfavorite );
					bt_favorite.setImageResource(R.drawable.fav_star_off);
					//bfavorite = false;
					favorites[fposistion] = false;
					removefavorite(folder, file);
				} else {
					bt_favorite.setImageResource(R.drawable.fav_star_on);
					favorites[fposistion] = true;
					//bfavorite = true;
					addfavorite(folder, file);
				}
			}
		});
		// Setting all values in listview
		number.setText(String.valueOf(position + 1));
		name.setText(item.get(ListviewLevel2.KEY_NAME));

		return vi;
	}
}