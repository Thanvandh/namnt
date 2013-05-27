package com.android.drumbeat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MoreDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_detail, menu);
		return true;
	}

}
