package com.android.anypic;

import com.parse.Parse;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	private static final int ID_UP = 1;
	private static final int ID_DOWN = 2;
	private static final int ID_SEARCH = 3;
	private static final int ID_INFO = 4;
	private static final int ID_SIGN_OUT = 5;
	private static final int ID_PHOTO_AROUND = 6;
	ImageButton bt_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "6UGj4uL8HU43HNS0R2XVlRlPh5CN5lXGtOJOUM70",
				"L7A6SE6RwIn8AjruYFac1jOLhgkIFa4lnm5J52Gb");
		// ActionItem nextItem = new ActionItem(ID_DOWN, "Next",
		// getResources().getDrawable(R.drawable.menu_down_arrow));
		// ActionItem prevItem = new ActionItem(ID_UP, "Prev",
		// getResources().getDrawable(R.drawable.menu_up_arrow));
		ActionItem infoItem = new ActionItem(ID_INFO, "My Profile",
				getResources().getDrawable(R.drawable.menu_info));
		ActionItem searchItem = new ActionItem(ID_SEARCH, "Find Friends",
				getResources().getDrawable(R.drawable.menu_search));
		ActionItem photoaroundItem = new ActionItem(ID_PHOTO_AROUND,
				"Photo Around", getResources().getDrawable(R.drawable.menu_ok));
		ActionItem signoutItem = new ActionItem(ID_SIGN_OUT, "Sign Out",
				getResources().getDrawable(R.drawable.menu_eraser));

		// use setSticky(true) to disable QuickAction dialog being dismissed
		// after an item is clicked
		// prevItem.setSticky(true);
		// nextItem.setSticky(true);

		// create QuickAction. Use QuickAction.VERTICAL or
		// QuickAction.HORIZONTAL param to define layout
		// orientation
		final QuickAction quickAction = new QuickAction(this,
				QuickAction.VERTICAL);

		// add action items into QuickAction
		// quickAction.addActionItem(nextItem);
		// quickAction.addActionItem(prevItem);
		quickAction.addActionItem(infoItem);
		quickAction.addActionItem(searchItem);
		quickAction.addActionItem(photoaroundItem);
		quickAction.addActionItem(signoutItem);

		// Set listener for action item clicked
		quickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						// here we can filter which action item was clicked with
						// pos or actionId parameter
						if (actionId == ID_SEARCH) {
							Toast.makeText(getApplicationContext(),
									"Let's do some search action",
									Toast.LENGTH_SHORT).show();
						} else if (actionId == ID_INFO) {
							StartProfileAcitivity();
						} else if (actionId == ID_SIGN_OUT) {
							Toast.makeText(getApplicationContext(), "Sign out",
									Toast.LENGTH_SHORT).show();
						} else if (actionId == ID_PHOTO_AROUND) {
							StartPhotoAroundAcitivity();
						} else {
							Toast.makeText(getApplicationContext(),
									actionItem.getTitle() + " selected",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		// set listnener for on dismiss event, this listener will be called only
		// if QuickAction dialog was dismissed
		// by clicking the area outside the dialog.
		quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
			@Override
			public void onDismiss() {
				Toast.makeText(getApplicationContext(), "Dismissed",
						Toast.LENGTH_SHORT).show();
			}
		});
		bt_settings = (ImageButton) findViewById(R.id.bt_settings);
		bt_settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				quickAction.show(v);
			}
		});

		TabHost tabHost = getTabHost();

		// Tab for Photos
		// TabSpec photospec = tabHost.newTabSpec("Photos");
		// photospec.setIndicator("Photos",
		// getResources().getDrawable(R.drawable.icon_photos_tab));
		TabSpec homespec = tabHost.newTabSpec("Home");
		homespec.setIndicator(prepareTabView("Home", R.drawable.icon_home_tab));

		Intent photosIntent = new Intent(this, HomeActivity.class);
		homespec.setContent(photosIntent);

		// Tab for Songs
		// TabSpec songspec = tabHost.newTabSpec("Songs");
		// // setting Title and Icon for the Tab
		// songspec.setIndicator("Songs",
		// getResources().getDrawable(R.drawable.icon_songs_tab));
		TabSpec cameraspec = tabHost.newTabSpec("Camera");
		cameraspec.setIndicator(prepareTabView("", R.drawable.icon_camera_tab));

		Intent songsIntent = new Intent(this, PhotoActivity.class);
		cameraspec.setContent(songsIntent);

		// Tab for Videos
		// TabSpec videospec = tabHost.newTabSpec("Videos");
		// videospec.setIndicator("Videos",
		// getResources().getDrawable(R.drawable.icon_videos_tab));
		TabSpec timelinespec = tabHost.newTabSpec("Timeline");
		timelinespec.setIndicator(prepareTabView("Timeline",
				R.drawable.icon_timeline_tab));
		Intent videosIntent = new Intent(this, TimelineActivity.class);
		timelinespec.setContent(videosIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(homespec); // Adding photos tab
		tabHost.addTab(cameraspec); // Adding songs tab
		tabHost.addTab(timelinespec); // Adding videos tab
		// for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
		// tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
		// }
		// tabHost.setCurrentTab(0);

	}

	private View prepareTabView(String text, int resId) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs, null);
		ImageView iv = (ImageView) view.findViewById(R.id.TabImageView);
		TextView tv = (TextView) view.findViewById(R.id.TabTextView);
		iv.setImageResource(resId);
		tv.setText(text);
		return view;
	}

	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
		Log.d("Test", "chuyentab");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.my_profile_settings:
            StartProfileAcitivity();
            return true;
        case R.id.photo_around_settings:
            StartPhotoAroundAcitivity();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }

	}

	private void openQuitDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				MainActivity.this);
		quitDialog.setTitle("Confirm to Quit?");

		quitDialog.setPositiveButton("OK, Quit!",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
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

	public boolean onBackPressed(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// if(event.getAction() == KeyEvent.ACTION_DOWN)
		// {
		// switch(keyCode)
		// {
		// case KeyEvent.KEYCODE_BACK:
		// openQuitDialog();
		// return true;
		// }
		// }
		// return super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			openQuitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void StartProfileAcitivity() {
		Intent in = new Intent(getApplicationContext(), ProfileActivity.class);
		startActivity(in);
	}
	public void StartPhotoAroundAcitivity() {
		Intent in = new Intent(getApplicationContext(), PhotoAroundActivity.class);
		startActivity(in);
	}

}
