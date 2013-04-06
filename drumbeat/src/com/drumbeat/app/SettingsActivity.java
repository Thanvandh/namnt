package com.drumbeat.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends Activity {
	Button bt_countoff_off;
	Button bt_countoff_onebar;
	Button bt_countoff_twobar;
	int countoff = 1;
	
	Button bt_random_off;
	Button bt_random_on;
	int random = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		bt_countoff_off = (Button) findViewById(R.id.bt_countoff);
		bt_countoff_onebar = (Button) findViewById(R.id.bt_onebar);
		bt_countoff_twobar = (Button) findViewById(R.id.bt_twobar);

		bt_countoff_off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setcountoff_off();
			}
		});
		bt_countoff_onebar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setcountoff_onebar();
			}
		});
		bt_countoff_twobar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setcountoff_twobar();
			}
		});
		
		bt_random_off = (Button) findViewById(R.id.bt_randomoff);
		bt_random_on = (Button) findViewById(R.id.bt_randomon);

		bt_random_off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setrandom_off();
			}
		});
		bt_random_on.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setrandom_on();
			}
		});
		setcountoff_off();
		setrandom_off();
	}

	public void setcountoff_off() {
		countoff = 1;
		bt_countoff_off.setBackgroundResource(R.color.blue);
		bt_countoff_onebar.setBackgroundResource(R.color.white);
		bt_countoff_twobar.setBackgroundResource(R.color.white);
	}

	public void setcountoff_onebar() {
		countoff = 2;
		bt_countoff_off.setBackgroundResource(R.color.white);
		bt_countoff_onebar.setBackgroundResource(R.color.blue);
		bt_countoff_twobar.setBackgroundResource(R.color.white);
	}

	public void setcountoff_twobar() {
		countoff = 3;
		bt_countoff_off.setBackgroundResource(R.color.white);
		bt_countoff_onebar.setBackgroundResource(R.color.white);
		bt_countoff_twobar.setBackgroundResource(R.color.blue);
	}
	
	public void setrandom_off() {
		random = 1;
		bt_random_off.setBackgroundResource(R.color.blue);
		bt_random_on.setBackgroundResource(R.color.white);
	}

	public void setrandom_on() {
		random = 2;
		bt_random_off.setBackgroundResource(R.color.white);
		bt_random_on.setBackgroundResource(R.color.blue);
	}

	private void openQuitDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				SettingsActivity.this);
		quitDialog.setTitle("Confirm to Quit?");

		quitDialog.setPositiveButton("OK, Quit!",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ListviewLevel2.stopMusic();
						FavoritesActivity.stopMusic();
						finish();

					}
				});
		quitDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		quitDialog.show();
	}

	@Override
	public void onBackPressed() {
		openQuitDialog();
	}
}