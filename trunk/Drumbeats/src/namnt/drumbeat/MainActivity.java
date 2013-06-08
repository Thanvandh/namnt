package namnt.drumbeat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mobeta.android.dslv.DragSortListView;

import namnt.drumbeat.utils.*;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	ImageView splash_screen;
	RelativeLayout main_app;

	RelativeLayout main_header;
	ImageButton bt_settings;
	ImageButton bt_more;
	ImageButton bt_favorite;
	ImageButton bt_back;
	ImageView img_logo;
	TextView main_header_text;
	final int state_main_body_home = 1;
	final int state_main_body_file = 2;
	final int state_main_body_favorite = 3;
	final int state_main_body_settings = 4;
	final int state_main_body_more = 5;
	boolean state_main_body_file_active = false;

	int state_main_body = state_main_body_home;
	static String KEY_FOLDER = "folder";
	static String KEY_NAME = "name";

	ListView main_body_list;
	RelativeLayout main_body_settings;
	RelativeLayout main_body_more;
	DragSortListView main_body_favorite;
	ListView main_body_more_listview;
	FileRowAdapter filerowadapter;

	// main footer
	RelativeLayout main_footer;
	Button bt_tempo;
	Button bt_random_console;
	Button bt_play;
	SeekBar volumeProgress;
	TextView playsong;
	//MediaPlayer mp;

	// tempo view
	//AnimatorSet animatorSet;
	AlphaAnimation fade_out;
	boolean justTouchDown;
	RelativeLayout tempo_view;
	Button bt_tempo_view_done;
	Button bt_tempo_view_play;
	Button bt_tempo_view_random;
	RelativeLayout tempo_view_layout_grid;
	GridView tempo_view_grid;
	int lastPos;
	int item_selected;
	String srate;
	TextView tempo_view_text_on_top;
	ImageButton bt_tempo_up;
	ImageButton bt_tempo_down;

	// settings body
	Button bt_countoff_off;
	Button bt_countoff_onebar;
	Button bt_countoff_twobar;

	Button bt_rate_app;
	Button bt_tell_friend;
	Button bt_feedback;
	int countoff;
	ToggleButton bt_random;
	boolean random;
	static SharedPreferences preferences;

	// favorite body
	ArrayList<HashMap<String, String>> array_list_favorite_file;
	FavoritesRowAdapter row_favorite_adapter;
	ArrayList<Song> favorite_songs;
	boolean state_edit = false;
	DatabaseHandler myDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main_header = (RelativeLayout) findViewById(R.id.main_header);
		bt_settings = (ImageButton) findViewById(R.id.main_header_bt_settings);
		bt_favorite = (ImageButton) findViewById(R.id.main_header_bt_favorites);
		bt_more = (ImageButton) findViewById(R.id.main_header_bt_more_edit);
		bt_back = (ImageButton) findViewById(R.id.main_header_bt_back);
		img_logo = (ImageView) findViewById(R.id.main_header_bt_logo);
		main_header_text = (TextView) findViewById(R.id.main_header_logo_text);

		main_body_list = (ListView) findViewById(R.id.main_body_listview);
		main_body_settings = (RelativeLayout) findViewById(R.id.main_body_settings);
		main_body_more = (RelativeLayout) findViewById(R.id.main_body_more);
		main_body_favorite = (DragSortListView) findViewById(R.id.main_body_listview_favorite);
		main_body_favorite.setRemoveListener(null);

		// more
		main_body_more_listview = (ListView) findViewById(R.id.main_body_more_listview);
		MoreRowAdapter moreadapter = new MoreRowAdapter(this);
		main_body_more_listview.setAdapter(moreadapter);
		main_body_more_listview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Intent i = new Intent(MainActivity.this,
								MoreDetail.class);
						i.putExtra("position", position);
						//i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);

					}
				});

		// main footer
		srate = getResources().getString(R.string.string_default_rate);
		main_footer = (RelativeLayout) findViewById(R.id.main_footer);
		bt_play = (Button) findViewById(R.id.main_footer_bt_play);
		bt_random_console = (Button) findViewById(R.id.main_footer_bt_random);
		bt_tempo = (Button) findViewById(R.id.main_footer_bt_tempo);
		playsong = (TextView) findViewById(R.id.main_footer_song_name);
		volumeProgress = (SeekBar) findViewById(R.id.main_footer_volumeProgressBar);

		// splash
		splash_screen = (ImageView) findViewById(R.id.splashscreen);
		main_app = (RelativeLayout) findViewById(R.id.main_app);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/* Create an Intent that will start the Menu-Activity. */
				splash_screen.setVisibility(View.GONE);
				main_app.setVisibility(View.VISIBLE);
			}
		}, 5000);

		bt_tempo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTempoView();

			}
		});
		bt_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (DrumbeatsMediaPlayer.bplay)
					stopMusic();
				else
					playMusic();
			}
		});
		
		bt_random_console.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setrandom();
			}
		});

		// tempo view
		tempo_view = (RelativeLayout) findViewById(R.id.tempo_view);
		bt_tempo_view_done = (Button) findViewById(R.id.tempo_view_footer_bt_done);
		bt_tempo_view_play = (Button) findViewById(R.id.tempo_view_footer_bt_playrate);
		bt_tempo_view_random = (Button) findViewById(R.id.tempo_view_footer_bt_randomrate);
		tempo_view_layout_grid = (RelativeLayout) findViewById(R.id.tempo_view_layout_grid_view);
		tempo_view_grid = (GridView) findViewById(R.id.tempo_view_grid_view);
		tempo_view_text_on_top = (TextView) findViewById(R.id.tempo_view_text_on_top);

		bt_tempo_down = (ImageButton) findViewById(R.id.tempo_view_header_decrease);
		bt_tempo_up = (ImageButton) findViewById(R.id.tempo_view_header_increase);

		bt_tempo_view_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (DrumbeatsMediaPlayer.bplay)
					stopMusic();
				else
					playMusic();
			}
		});
		bt_tempo_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("test", "selecteddown|" + item_selected);
				if (item_selected != 24 && item_selected != 27) {
					tempo_view_grid.getChildAt(item_selected)
							.setBackgroundResource(
									R.drawable.item_grid_selector);
					int number = 0;
					for (int i = 0; i < TextAdapter.arrayrate.length; i++) {
						if (srate.equalsIgnoreCase(TextAdapter.arrayrate[i])) {
							number = i;
							break;
						}
					}
					srate = TextAdapter.arrayrate[number - 1];
					tempo_view_text_on_top.setText(srate);
					Log.d("test", "selecteddownrate|" + srate);
					for (int i = 0; i < TextAdapter.mTemporary.length; i++) {
						if (srate.equalsIgnoreCase(TextAdapter.mTemporary[i])) {
							item_selected = i;
							break;
						}
					}
					Log.d("test", "selecteddown|" + item_selected);
					tempo_view_grid.getChildAt(item_selected)
							.setBackgroundResource(R.drawable.item_press);

					if (item_selected == 24) {
						bt_tempo_down.setEnabled(false);
						bt_tempo_up.setEnabled(true);
					} else if (item_selected == 3) {
						bt_tempo_down.setEnabled(true);
						bt_tempo_up.setEnabled(false);
					} else {
						bt_tempo_down.setEnabled(true);
						bt_tempo_up.setEnabled(true);
					}
					if (DrumbeatsMediaPlayer.bplay)
						playMusic();
				}

			}
		});
		bt_tempo_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("test", "selectedup|" + item_selected);
				if (item_selected != 3 && item_selected != 27) {
					tempo_view_grid.getChildAt(item_selected)
							.setBackgroundResource(
									R.drawable.item_grid_selector);
					int number = 0;
					for (int i = 0; i < TextAdapter.arrayrate.length; i++) {
						if (srate.equalsIgnoreCase(TextAdapter.arrayrate[i])) {
							number = i;
							break;
						}
					}
					srate = TextAdapter.arrayrate[number + 1];
					tempo_view_text_on_top.setText(srate);
					Log.d("test", "selecteduprate|" + srate);
					for (int i = 0; i < TextAdapter.mTemporary.length; i++) {
						if (srate.equalsIgnoreCase(TextAdapter.mTemporary[i])) {
							item_selected = i;
							break;
						}
					}
					Log.d("test", "selectedup|" + item_selected);
					tempo_view_grid.getChildAt(item_selected)
							.setBackgroundResource(R.drawable.item_press);
					if (item_selected == 24) {
						bt_tempo_down.setEnabled(false);
						bt_tempo_up.setEnabled(true);
					} else if (item_selected == 3) {
						bt_tempo_down.setEnabled(true);
						bt_tempo_up.setEnabled(false);
					} else {
						bt_tempo_down.setEnabled(true);
						bt_tempo_up.setEnabled(true);
					}
					if (DrumbeatsMediaPlayer.bplay)
						playMusic();
				}

			}
		});

		bt_tempo_view_random.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setrandom();
			}
		});

		bt_tempo_view_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tempo_view.setVisibility(View.GONE);
				main_header.setVisibility(View.VISIBLE);
				if (state_main_body == state_main_body_file) {
					showMainBodyFile(DrumbeatsMediaPlayer.mfolder);
				} else if (state_main_body == state_main_body_favorite) {
					showMainBodyFavorite();
				} else
				showMainBody(state_main_body);
				setRate();
			}
		});

		int statusBarHeight = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			statusBarHeight = getResources().getDimensionPixelSize(resourceId);
		}

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		tempo_view_layout_grid.getLayoutParams().height = metrics.heightPixels
				- statusBarHeight
				- getResources().getDimensionPixelSize(
						R.dimen.main_header_height)
				- getResources().getDimensionPixelSize(
						R.dimen.tempo_view_footer_height)
				+ getResources().getDimensionPixelSize(
						R.dimen.tempo_spacing_bottom);
		;
		tempo_view_layout_grid.setLayoutParams(tempo_view_layout_grid
				.getLayoutParams());
		// int itemHeight = tempo_view_grid.getHeight()/7;
		// item_selected =
		// Integer.valueOf(getResources().getString(R.string.string_default_item_selected));
		// tempo_view_grid.setAdapter(new
		// TextAdapter(this,itemHeight,itemHeight,item_selected));
		// int h = metrics.heightPixels;
		// int h2 = metrics.widthPixels;
		int h = tempo_view_layout_grid.getLayoutParams().height;
		int gridheight = tempo_view_layout_grid.getLayoutParams().height / 7;// getResources().getDimensionPixelSize(R.dimen.tempo_view_grid_item_height);
		RelativeLayout tempo_footer = (RelativeLayout) findViewById(R.id.tempo_view_footer);
		tempo_footer.getLayoutParams().height = getResources()
				.getDimensionPixelSize(R.dimen.tempo_view_footer_height)
				+ tempo_view_layout_grid.getLayoutParams().height
				- gridheight
				* 7;
		tempo_footer.setLayoutParams(tempo_footer.getLayoutParams());
		tempo_view_layout_grid.getLayoutParams().height = gridheight * 7;
		tempo_view_layout_grid.setLayoutParams(tempo_view_layout_grid
				.getLayoutParams());

		item_selected = Integer.valueOf(getResources().getString(
				R.string.string_default_item_selected));
		tempo_view_grid.setAdapter(new TextAdapter(this, gridheight,
				gridheight, item_selected));
		// tempo_view_text_on_top.setLayoutParams(new
		// AbsoluteLayout.LayoutParams(200,200, 200,200));
		
		//animatorSet = new AnimatorSet();
		fade_out = new AlphaAnimation(1.0f, 0.0f);
		fade_out.setDuration(5000);
		fade_out.setAnimationListener(new AnimationListener()
		{
		    public void onAnimationStart(Animation arg0)
		    {
		    }
		    public void onAnimationRepeat(Animation arg0)
		    {
		    }

		    public void onAnimationEnd(Animation arg0)
		    {
		    	tempo_view_text_on_top.setVisibility(View.GONE);
		    }
		});
		
		tempo_view_grid.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					//animatorSet.end();
					justTouchDown = false;
					tempo_view_text_on_top.clearAnimation();
					
					int x = (int) event.getX();
					int y = (int) event.getY();
					int row = (y)
							/ (tempo_view_layout_grid.getLayoutParams().height / 7);
					if (row > 6)
						row = 6;
					int col = x / (tempo_view_layout_grid.getWidth() / 4);
					// Log.d("test", "width|" + tempo_view_grid.getWidth() +
					// "|height|" + tempo_view_grid.getHeight());
					// Log.d("x-value",""+x);
					lastPos = TextAdapter.matrix[row][col];
					if (lastPos != 27) {
						tempo_view_grid.requestFocusFromTouch();
						tempo_view_grid.setSelection(lastPos);
						// tempo_view_grid.setSelection(lastPos);
						srate = TextAdapter.mTemporary[lastPos];
						tempo_view_text_on_top.setVisibility(View.VISIBLE);
		//				tempo_view_text_on_top.setAlpha(1.0f);
						tempo_view_text_on_top.setText(srate);
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					justTouchDown = true;
					int x = (int) event.getX();
					int y = (int) event.getY();
					int row = (y)
							/ (tempo_view_layout_grid.getLayoutParams().height / 7);
					if (row > 6)
						row = 6;
					int col = x / (tempo_view_layout_grid.getWidth() / 4);
					// Log.d("x-value",""+x);
					// Log.d("Y-value",""+y);

					// if (col == 3 && row == 6) {
					// return true;
					// }

					lastPos = TextAdapter.matrix[row][col];
					// gridView.setSelection(lastPos);
					if (lastPos != 27) {
						tempo_view_grid.requestFocusFromTouch();
						tempo_view_grid.setSelection(lastPos);
						srate = TextAdapter.mTemporary[lastPos];
//						tempo_view_text_on_top.setVisibility(View.VISIBLE);
//						tempo_view_text_on_top.setAlpha(1.0f);
						tempo_view_text_on_top.setText(srate);
						return true;
					} else {
						tempo_view_grid.getChildAt(lastPos)
								.setBackgroundResource(
										android.R.color.transparent);
						return true;
					}

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// tempo_view_grid.setSelection(lastPos);
					// tempo_view_grid.requestFocusFromTouch();
					// tempo_view_grid.getChildAt(pos).setBackgroundResource(R.drawable.item_press);
					// if (item_selected == 3) {
					// // item_selected = lastPos;
					// return true;
					// }
					if (lastPos != 27) {
						tempo_view_grid.getChildAt(item_selected)
								.setBackgroundResource(
										R.drawable.item_grid_selector);
						item_selected = lastPos;
						tempo_view_grid.getChildAt(item_selected)
								.setBackgroundResource(R.drawable.item_press);
						if (item_selected == 24) {
							bt_tempo_down.setEnabled(false);
							bt_tempo_up.setEnabled(true);
						} else if (item_selected == 3) {
							bt_tempo_down.setEnabled(true);
							bt_tempo_up.setEnabled(false);
						} else {
							bt_tempo_down.setEnabled(true);
							bt_tempo_up.setEnabled(true);
						}
						// srate = TextAdapter.mTemporary[lastPos];
						tempo_view_text_on_top.setVisibility(View.GONE);
						
						if(!justTouchDown) {
							tempo_view_text_on_top.startAnimation(fade_out);
						}
						 
//						if(!justTouchDown) {
//							ObjectAnimator animateFaceout = ObjectAnimator.ofFloat(tempo_view_text_on_top, "alpha", 0);
//							animateFaceout.setDuration(5000);
//							animatorSet.play(animateFaceout);
//							animatorSet.start();
//						}

						if (DrumbeatsMediaPlayer.bplay)
							playMusic();
					}
					return true;
				}
				return false;
			}

		});

		// settings body
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		// favorite body
		myDbHelper = new DatabaseHandler(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// preferences.edit().putString("passwordKey",
		// editText.getText().toString()).commit();
		bt_countoff_off = (Button) findViewById(R.id.main_body_settings_bt_countoff);
		bt_countoff_onebar = (Button) findViewById(R.id.main_body_settings_bt_onebar);
		bt_countoff_twobar = (Button) findViewById(R.id.main_body_settings_bt_twobar);
		bt_rate_app = (Button) findViewById(R.id.main_body_settings_bt_ratethisapp);
		bt_tell_friend = (Button) findViewById(R.id.main_body_settings_bt_tellafriend);
		bt_feedback = (Button) findViewById(R.id.main_body_settings_bt_sendfeeback);

		bt_random = (ToggleButton) findViewById(R.id.main_body_settings_bt_random_tempo);

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

		bt_rate_app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		bt_tell_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
				i.putExtra(Intent.EXTRA_SUBJECT, "Drum Beats+ App for Android");
				i.putExtra(Intent.EXTRA_TEXT,
						"Hey, check out this app.:\nhttp://bit.ly/GetBeatsPlus");
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(MainActivity.this,
							"There are no email clients installed.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		bt_feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "android@ninebuzz.com" });
				i.putExtra(Intent.EXTRA_SUBJECT, "Drum Beats+ Support");
				i.putExtra(Intent.EXTRA_TEXT, "");
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(MainActivity.this,
							"There are no email clients installed.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		bt_random.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				preferences.edit().putBoolean("random", bt_random.isChecked())
						.commit();
			}
		});

		bt_settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state_main_body = state_main_body_settings;
				showMainBody(state_main_body);
			}
		});
		bt_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state_main_body = state_main_body_favorite;
				showMainBody(state_main_body);
			}
		});
		bt_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (state_main_body != state_main_body_favorite) {
					state_main_body = state_main_body_more;
					showMainBody(state_main_body);
				} else {
					if (!state_edit) {
						state_edit = true;
						bt_more.setImageResource(R.drawable.edit_done_button);
						showMainBodyFavoriteEdit();
					} else {
						state_edit = false;
						bt_more.setImageResource(R.drawable.edit_button);
						showMainBodyFavorite();
					}

				}
			}
		});
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (state_main_body == state_main_body_file) {
					state_main_body = state_main_body_home;
					showMainBody(state_main_body);
				} else {
					if (state_main_body_file_active) {
						state_main_body = state_main_body_file;
						showMainBodyFile(DrumbeatsMediaPlayer.mfolder);
					} else {
						state_main_body = state_main_body_home;
						showMainBody(state_main_body);
					}
				}
			}
		});
		
		main_header_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (state_main_body == state_main_body_file) {
					state_main_body = state_main_body_home;
					showMainBody(state_main_body);
				} else {
					if (state_main_body_file_active) {
						state_main_body = state_main_body_file;
						showMainBodyFile(DrumbeatsMediaPlayer.mfolder);
					} else {
						state_main_body = state_main_body_home;
						showMainBody(state_main_body);
					}
				}
			}
		});

		showMainBody(state_main_body);
		setRate();
		initControlsvolume();
		playsong.setText("• " + DrumbeatsMediaPlayer.mfolder.toUpperCase() + " - " + DrumbeatsMediaPlayer.mfilename.toUpperCase() + " •");

	}

	public void showMainBody(int state) {
		switch (state) {
		case state_main_body_home:
			showMainBodyHome();
			break;
		case state_main_body_favorite:
			showMainBodyFavorite();
			break;
		case state_main_body_settings:
			showMainBodySettings();
			break;
		case state_main_body_more:
			showMainBodyMore();
			break;
		default:
			break;
		}
	}

	public void setRate() {
		bt_tempo.setText(srate);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		initControlsvolume();
		setButtonPlay(DrumbeatsMediaPlayer.bplay);
		super.onResume();
	}

	private void initControlsvolume() {
		try {
			final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			volumeProgress.setMax(audioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
			volumeProgress.setProgress(audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));

			volumeProgress
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
						}

						@Override
						public void onProgressChanged(SeekBar arg0,
								int progress, boolean arg2) {
							audioManager.setStreamVolume(
									AudioManager.STREAM_MUSIC, progress, 0);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<String> getListFolder(){
		String[] folder = new String [12];
	    String[] state = new String[12];
	    
	    folder[0] = getResources().getString(R.string.string_more_text_folder_name0);
	    folder[1] = getResources().getString(R.string.string_more_text_folder_name1);
	    folder[2] = getResources().getString(R.string.string_more_text_folder_name2);
	    folder[3] = getResources().getString(R.string.string_more_text_folder_name3);
	    folder[4] = getResources().getString(R.string.string_more_text_folder_name4);
        folder[5] = getResources().getString(R.string.string_more_text_folder_name5);
        folder[6] = getResources().getString(R.string.string_more_text_folder_name6);
        folder[7] = getResources().getString(R.string.string_more_text_folder_name7);
        folder[8] = getResources().getString(R.string.string_more_text_folder_name8);
        folder[9] = getResources().getString(R.string.string_more_text_folder_name9);
        folder[10] = getResources().getString(R.string.string_more_text_folder_name10);
        folder[11] = getResources().getString(R.string.string_more_text_folder_name11);
        
        
        state[0] = preferences.getString(folder[0], getResources().getString(R.string.string_more_text_folder_name_status0));
        state[1] = preferences.getString(folder[1], getResources().getString(R.string.string_more_text_folder_name_status1));
        state[2] = preferences.getString(folder[2], getResources().getString(R.string.string_more_text_folder_name_status2));
        state[3] = preferences.getString(folder[3], getResources().getString(R.string.string_more_text_folder_name_status3));
        state[4] = preferences.getString(folder[4], getResources().getString(R.string.string_more_text_folder_name_status4));
        state[5] = preferences.getString(folder[5], getResources().getString(R.string.string_more_text_folder_name_status5));
        state[6] = preferences.getString(folder[6], getResources().getString(R.string.string_more_text_folder_name_status6));
        state[7] = preferences.getString(folder[7], getResources().getString(R.string.string_more_text_folder_name_status7));
        state[8] = preferences.getString(folder[8], getResources().getString(R.string.string_more_text_folder_name_status8));
        state[9] = preferences.getString(folder[9], getResources().getString(R.string.string_more_text_folder_name_status9));
        state[10] = preferences.getString(folder[10], getResources().getString(R.string.string_more_text_folder_name_status10));
        state[11] = preferences.getString(folder[11], getResources().getString(R.string.string_more_text_folder_name_status11));
        
        ArrayList<String> Listfolder = new ArrayList<String>();
        for (int i = 0; i <12; i++){
        	//Log.d("test", "folder " + folder[i] + " state " + state[i]);
        	if (state[i].equalsIgnoreCase("1"))
        		Listfolder.add(folder[i]);
        }
        return Listfolder;
        
        
	    
	}

	public void showMainBodyHome() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
		bt_more.setImageResource(R.drawable.morebutton_white);
		img_logo.setVisibility(View.VISIBLE);
		bt_back.setVisibility(View.GONE);
		main_header_text.setVisibility(View.GONE);
		state_main_body_file_active = false;

		main_body_settings.setVisibility(View.GONE);
		main_body_more.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.GONE);
		main_body_list.setVisibility(View.VISIBLE);
		main_footer.setVisibility(View.VISIBLE);

		//final String array_folder[] = getFolder();
		final ArrayList<String> listfolder = getListFolder();
		DrumbeatsMediaPlayer.mfolder = listfolder.get(0);
		String[] array_file = getFolderFile(DrumbeatsMediaPlayer.mfolder);
		if (array_file != null && array_file.length > 0)
			DrumbeatsMediaPlayer.mfilename = array_file[0];
		ArrayList<HashMap<String, String>> array_list_folder = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < listfolder.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_FOLDER, listfolder.get(i));
			array_list_folder.add(map);
		}

		// Getting adapter
		HomeRowAdapter adapter = new HomeRowAdapter(this, array_list_folder);
		main_body_list.setAdapter(adapter);
		main_body_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				// TODO Auto-generated method stub
				DrumbeatsMediaPlayer.mfolder = listfolder.get(postion);
				state_main_body = state_main_body_file;
				showMainBodyFile(DrumbeatsMediaPlayer.mfolder);

			}
		});
	}

	private String[] getFolder() {
		AssetManager assetManager = getAssets();
		String[] files = null;
		try {
			files = assetManager.list("music");
		} catch (IOException e) {
			return null;
		}
		return files;
	}

	public void showMainBodyFile(String folder) {
		bt_settings.setImageResource(R.drawable.settingsbutton_white);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
		bt_more.setImageResource(R.drawable.morebutton_white);
		img_logo.setVisibility(View.GONE);
		bt_back.setVisibility(View.VISIBLE);
		main_header_text.setVisibility(View.VISIBLE);
		main_body_settings.setVisibility(View.GONE);
		main_body_more.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.GONE);
		main_body_list.setVisibility(View.VISIBLE);
		main_footer.setVisibility(View.VISIBLE);
		state_main_body_file_active = true;
		main_header_text.setText(folder);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		main_header_text.setTypeface(font);

		final String[] array_file = getFolderFile(folder);
		ArrayList<HashMap<String, String>> array_list_file = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < array_file.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_FOLDER, folder);
			map.put(KEY_NAME, array_file[i]);
			array_list_file.add(map);
		}

		// Getting adapter
		filerowadapter = new FileRowAdapter(this, array_list_file);
		main_body_list.setAdapter(filerowadapter);
		main_body_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				// TODO Auto-generated method stub
				DrumbeatsMediaPlayer.mfilename = array_file[postion];
				playMusic();

			}
		});
	}

	private String[] getFolderFile(String folder) {
		AssetManager assetManager = getAssets();
		String[] files = null;
		try {
			files = assetManager.list("music/" + folder);
		} catch (IOException e) {
			return null;
		}
		return files;
	}
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                	Song tmp = favorite_songs.get(from);
        			favorite_songs.remove(from);
        			favorite_songs.add(to, tmp);
        			myDbHelper.openDataBase();
        			myDbHelper.updatealloderfavorite(favorite_songs);
        			myDbHelper.close();
        			array_list_favorite_file.remove(from);
        			HashMap<String, String> map = new HashMap<String, String>();
        			map.put(KEY_FOLDER, tmp.getFolder());
        			map.put(KEY_NAME, tmp.getName());
        			array_list_favorite_file.add(to, map);
        			row_favorite_adapter.notifyDataSetChanged();
                }
            };


	public void showMainBodyFavorite() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white_pressed);
		bt_more.setImageResource(R.drawable.edit_button);
		img_logo.setVisibility(View.GONE);
		bt_back.setVisibility(View.VISIBLE);
		main_header_text.setVisibility(View.VISIBLE);
		main_header_text.setText(R.string.favorite);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		main_header_text.setTypeface(font);

		main_body_settings.setVisibility(View.GONE);
		main_body_more.setVisibility(View.GONE);
		main_body_list.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.VISIBLE);
		main_footer.setVisibility(View.VISIBLE);

		state_edit = false;

		try {

			myDbHelper.openDataBase();
			favorite_songs = myDbHelper.getFavariteSong();
			myDbHelper.close();
			if (favorite_songs != null) {
				array_list_favorite_file = new ArrayList<HashMap<String, String>>();

				for (int i = 0; i < favorite_songs.size(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(KEY_FOLDER, favorite_songs.get(i).getFolder());
					map.put(KEY_NAME, favorite_songs.get(i).getName());
					array_list_favorite_file.add(map);
				}

				// Getting adapter
				row_favorite_adapter = new FavoritesRowAdapter(this,
						array_list_favorite_file, false, 0);
				main_body_favorite.setAdapter(row_favorite_adapter);
				main_body_favorite.setDropListener(null);
				main_body_favorite.setSmoothScrollbarEnabled(true);
			} else {
				main_body_favorite.setAdapter(null);
			}
			main_body_favorite
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int postion, long arg3) {
							// TODO Auto-generated method stub
							DrumbeatsMediaPlayer.mfolder = favorite_songs.get(postion).getFolder();
							DrumbeatsMediaPlayer.mfilename = favorite_songs.get(postion).getName();
							playMusic();

						}
					});

		} catch (SQLException sqle) {

			throw sqle;

		}

	}

	public void showMainBodyFavoriteEdit() {
		try {

			myDbHelper.openDataBase();
			favorite_songs = myDbHelper.getFavariteSong();
			myDbHelper.close();
			if (favorite_songs != null) {
				array_list_favorite_file = new ArrayList<HashMap<String, String>>();

				for (int i = 0; i < favorite_songs.size(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(KEY_FOLDER, favorite_songs.get(i).getFolder());
					map.put(KEY_NAME, favorite_songs.get(i).getName());
					array_list_favorite_file.add(map);
				}

				// Getting adapter
				int marginRight = getResources()
						.getDimensionPixelSize(
								R.dimen.row_main_body_list_favorite_file_folder_margin_right_editmode);
				row_favorite_adapter = new FavoritesRowAdapter(this,
						array_list_favorite_file, true, marginRight);
				//main_body_favorite.setDropListener(mDropListener);
				// main_body_favorite.setRemoveListener(mRemoveListener);
				//main_body_favorite.setDragListener(mDragListener);
				main_body_favorite.setDropListener(onDrop);
				main_body_favorite.setAdapter(row_favorite_adapter);
				//main_body_favorite.setmove(true);
				main_body_favorite.setSmoothScrollbarEnabled(true);
			} else {
				main_body_favorite.setAdapter(null);
			}
			main_body_favorite
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int postion, long arg3) {
							// TODO Auto-generated method stub

						}
					});

		} catch (SQLException sqle) {

			throw sqle;

		}

	}

	public void openRemoveDialog(final int position) {
		removefavorite(position);
		favorite_songs.remove(position);
		array_list_favorite_file.remove(position);
		row_favorite_adapter.notifyDataSetChanged();
	}

	public void removefavorite(int position) {
		myDbHelper.openDataBase();
		myDbHelper.removefavorite(favorite_songs.get(position).getFolder(),
				favorite_songs.get(position).getName());
		myDbHelper.close();
	}

	private String[] getFavoriteFile() {

		// String[] files = null;
		String[] files = { "audio 1", " bai hay", "thu thoi", "the nhi",
				" bai hay", "thu thoi", "the nhi", " bai hay", "thu thoi",
				"the nhi" };

		return files;
	}

//	private DropListener mDropListener = new DropListener() {
//		public void onDrop(int from, int to) {
//			Song tmp = favorite_songs.get(from);
//			favorite_songs.remove(from);
//			favorite_songs.add(to, tmp);
//			myDbHelper.openDataBase();
//			myDbHelper.updatealloderfavorite(favorite_songs);
//			myDbHelper.close();
//			array_list_favorite_file.remove(from);
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put(KEY_FOLDER, tmp.getFolder());
//			map.put(KEY_NAME, tmp.getName());
//			array_list_favorite_file.add(to, map);
//			row_favorite_adapter.notifyDataSetChanged();
//			// objectlistchannel.onDrop(from, to);
//			// Log.v("movechannel", "From" + from + "To" + to);
//			// int temp = iodernumber.get(from);
//			// iodernumber.remove(from);
//			// iodernumber.add(to,temp);
//			// mObjectInfo.updateallodernumber(iodernumber, m_mode);
//			// GetInformation(0,izoomlevel, m_mode);
//			// myListView.invalidateViews();
//		}
//	};
	//
	// private RemoveListener mRemoveListener = new RemoveListener() {
	// public void onRemove(int which) {
	// if (which < 0 || which > array_favorite_file.length) return;
	// // iodernumber.remove(which);
	// // myListView.invalidateViews();
	// }
	// };

//	private DragListener mDragListener = new DragListener() {
//
//		// int backgroundColor;
//		int defaultBackgroundColor;
//
//		public void onDrag(int x, int y, ListView listView) {
//			// TODO Auto-generated method stub
//		}
//
//		public void onStartDrag(View itemView) {
//			itemView.setVisibility(View.INVISIBLE);
//			defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
//			itemView.setBackgroundResource(R.color.blue);
//			// ImageView iv = (ImageView)itemView.findViewById(R.id.icon);
//			// if (iv != null) iv.setVisibility(View.INVISIBLE);
//		}
//
//		public void onStopDrag(View itemView) {
//			itemView.setVisibility(View.VISIBLE);
//			itemView.setBackgroundColor(defaultBackgroundColor);
//			// ImageView iv =
//			// (ImageView)itemView.findViewById(R.id.iconchannel);
//			// if (iv != null) iv.setVisibility(View.VISIBLE);
//		}
//
//	};

	public void showMainBodySettings() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white_pressed);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
		bt_more.setImageResource(R.drawable.morebutton_white);
		img_logo.setVisibility(View.GONE);
		bt_back.setVisibility(View.VISIBLE);
		main_header_text.setVisibility(View.VISIBLE);
		main_header_text.setText(R.string.settings);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		main_header_text.setTypeface(font);

		main_body_list.setVisibility(View.GONE);
		main_body_more.setVisibility(View.GONE);
		main_footer.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.GONE);
		main_body_settings.setVisibility(View.VISIBLE);

		countoff = preferences.getInt("countoff", 0);
		random = preferences.getBoolean("random", false);

		bt_random.setChecked(random);
		switch (countoff) {
		case 0:
			setcountoff_off();
			break;
		case 1:
			setcountoff_onebar();
			break;
		case 2:
			setcountoff_twobar();
			break;

		default:
			break;
		}
	}

	// for settings body
	public void setcountoff_off() {
		// countoff = 1;
		preferences.edit().putInt("countoff", 0).commit();
		bt_countoff_off.setBackgroundResource(R.drawable.off_button_pressed);
		bt_countoff_onebar.setBackgroundResource(R.drawable.onebar_button);
		bt_countoff_twobar.setBackgroundResource(R.drawable.twobars_button);
	}

	public void setcountoff_onebar() {
		// countoff = 2;
		preferences.edit().putInt("countoff", 1).commit();
		bt_countoff_off.setBackgroundResource(R.drawable.off_button);
		bt_countoff_onebar
				.setBackgroundResource(R.drawable.onebar_button_pressed);
		bt_countoff_twobar.setBackgroundResource(R.drawable.twobars_button);
	}

	public void setcountoff_twobar() {
		// countoff = 3;
		preferences.edit().putInt("countoff", 2).commit();
		bt_countoff_off.setBackgroundResource(R.drawable.off_button);
		bt_countoff_onebar.setBackgroundResource(R.drawable.onebar_button);
		bt_countoff_twobar
				.setBackgroundResource(R.drawable.twobars_button_pressed);
	}

	public void showMainBodyMore() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
		bt_more.setImageResource(R.drawable.morebutton_white_pressed);
		img_logo.setVisibility(View.GONE);
		bt_back.setVisibility(View.VISIBLE);
		main_header_text.setVisibility(View.VISIBLE);
		main_header_text.setText(R.string.more);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		main_header_text.setTypeface(font);

		main_body_list.setVisibility(View.GONE);
		main_body_settings.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.GONE);
		main_footer.setVisibility(View.GONE);
		main_body_more.setVisibility(View.VISIBLE);

	}

	public void showTempoView() {

		main_header.setVisibility(View.GONE);
		main_body_list.setVisibility(View.GONE);
		main_body_settings.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.GONE);
		main_footer.setVisibility(View.GONE);
		main_body_more.setVisibility(View.GONE);
		tempo_view.setVisibility(View.VISIBLE);

	}

	public void playMusic() {
		
		int maxCount = preferences.getInt("countoff", 0);
		

		Log.d("test", "count " + maxCount);
		if (maxCount > 0 && !DrumbeatsMediaPlayer.bplay){
			DrumbeatsMediaPlayer.bplay = true;
			setButtonPlay(DrumbeatsMediaPlayer.bplay);
			playsong.setText("• " + DrumbeatsMediaPlayer.mfolder.toUpperCase() + " - "
					+ DrumbeatsMediaPlayer.mfilename.toUpperCase() + " •");
			String filename[] = getFileName(DrumbeatsMediaPlayer.mfolder, DrumbeatsMediaPlayer.mfilename);
			int rate = 0;
			for (int i = 0; i < TextAdapter.arrayrate.length; i++) {
				if (srate.equalsIgnoreCase(TextAdapter.arrayrate[i])) {
					rate = i;
					break;
				}
			}
			String precount = filename[rate].substring(filename[rate].length() - 10);
			playPrecount(precount, maxCount);
		} else {
			DrumbeatsMediaPlayer.bplay = true;
			setButtonPlay(DrumbeatsMediaPlayer.bplay);
			playsong.setText("• " + DrumbeatsMediaPlayer.mfolder.toUpperCase() + " - "
					+ DrumbeatsMediaPlayer.mfilename.toUpperCase() + " •");
			playMusic(DrumbeatsMediaPlayer.mfolder, DrumbeatsMediaPlayer.mfilename);
		}
		if (state_main_body == state_main_body_file)
			filerowadapter.notifyDataSetChanged();
		else if (state_main_body == state_main_body_favorite)
			row_favorite_adapter.notifyDataSetChanged();
	}

	private String[] getFileName(String folder, String folderfile) {
		AssetManager assetManager = getAssets();
		String[] files = null;
		try {
			files = assetManager.list("music/" + folder + "/" + folderfile);
		} catch (IOException e) {
			return null;
		}
		return files;
	}

	public void playPrecount(String file, final int maxCount) {
		if (DrumbeatsMediaPlayer.mp != null) {
			if (DrumbeatsMediaPlayer.mp.isPlaying()) {
				DrumbeatsMediaPlayer.mp.stop();
				// mp.release();
			}
		} else
			DrumbeatsMediaPlayer.mp = new MediaPlayer();
		
		Log.d("test", "countmax " + maxCount);
		AssetFileDescriptor fileplay;
		try {
			fileplay = getResources().getAssets().openFd(
					"precount/" + file);
			DrumbeatsMediaPlayer.mp.reset();
			DrumbeatsMediaPlayer.mp.setDataSource(fileplay.getFileDescriptor(),
					fileplay.getStartOffset(), fileplay.getLength());
			DrumbeatsMediaPlayer.mp.prepare();
			DrumbeatsMediaPlayer.mp.setLooping(false);
			DrumbeatsMediaPlayer.mp.start();
			DrumbeatsMediaPlayer.mp.setOnCompletionListener(new OnCompletionListener(){
				  int count = 0; // initialize somewhere before, not sure if this will work here
				  
				  @Override
				  public void onCompletion(MediaPlayer mediaPlayer) {
				    if(count < maxCount-1) {
				      count++;
				      mediaPlayer.seekTo(0);
				      mediaPlayer.start();
				    } else {
				    	playMusic(DrumbeatsMediaPlayer.mfolder, DrumbeatsMediaPlayer.mfilename);	
				    }
				}});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playMusic(String folder, String file) {
		if ((file == null )|| (file.length() == 0))
			return;
		if (DrumbeatsMediaPlayer.mp != null) {
			if (DrumbeatsMediaPlayer.mp.isPlaying()) {
				DrumbeatsMediaPlayer.mp.stop();
				// mp.release();

			}
		} else
			DrumbeatsMediaPlayer.mp = new MediaPlayer();
		final String filename[] = getFileName(folder, file);
		int rate = 0;
		for (int i = 0; i < TextAdapter.arrayrate.length; i++) {
			if (srate.equalsIgnoreCase(TextAdapter.arrayrate[i])) {
				rate = i;
				break;
			}
		}
		AssetFileDescriptor fileplay;
		try {
			fileplay = getResources().getAssets().openFd(
					"music/" + folder + "/" + file + "/" + filename[rate]);
			DrumbeatsMediaPlayer.mp.reset();
			DrumbeatsMediaPlayer.mp.setDataSource(fileplay.getFileDescriptor(),
					fileplay.getStartOffset(), fileplay.getLength());
			DrumbeatsMediaPlayer.mp.prepare();
			DrumbeatsMediaPlayer.mp.setLooping(true);
			DrumbeatsMediaPlayer.mp.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stopMusic() {
		DrumbeatsMediaPlayer.bplay = false;
		setButtonPlay(DrumbeatsMediaPlayer.bplay);
//		playsong.setText("");
		if (DrumbeatsMediaPlayer.mp != null) {
			if (DrumbeatsMediaPlayer.mp.isPlaying()) {
				DrumbeatsMediaPlayer.mp.stop();
				// mp.release();

			}
		}
		if (state_main_body == state_main_body_file)
			filerowadapter.notifyDataSetChanged();
		else if (state_main_body == state_main_body_favorite)
			row_favorite_adapter.notifyDataSetChanged();

	}

	public void setButtonPlay(boolean b) {
		if (b) {
			bt_play.setBackgroundResource(R.drawable.btn_stop);
			bt_tempo_view_play.setBackgroundResource(R.drawable.btn_stop);
		} else {
			bt_play.setBackgroundResource(R.drawable.btn_play);
			bt_tempo_view_play.setBackgroundResource(R.drawable.btn_play);
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
						if (DrumbeatsMediaPlayer.mp != null) {
						if (DrumbeatsMediaPlayer.mp.isPlaying()) {
							DrumbeatsMediaPlayer.mp.stop();
							DrumbeatsMediaPlayer.mp.release();
							// mp.release();

						}
						}
					
						
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

	public void setrandom() {
		final ArrayList<String> listfolder = getListFolder();
		Random randomFolder = new Random();
		int k = randomFolder.nextInt(listfolder.size() - 1);
		DrumbeatsMediaPlayer.mfolder = listfolder.get(k);
		
		boolean brandom = preferences.getBoolean("random", false);
		if (brandom) {
			
			tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_grid_selector);
			String file[] = getFolderFile(DrumbeatsMediaPlayer.mfolder);
			if (file == null)
				return;
			Random random = new Random();
			int i = random.nextInt(TextAdapter.arrayrate.length);
			srate = TextAdapter.arrayrate[i];
			for (int j = 0; j < TextAdapter.mTemporary.length; j++) {
				if (srate.equalsIgnoreCase(TextAdapter.mTemporary[j])) {
					item_selected = j;
					break;
				}
			}
			
			tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_press);
			// playMusic();
			setRate();
			int j = random.nextInt(file.length);
			DrumbeatsMediaPlayer.mfilename = file[j];
//			playMusic();
		} else {
			String file[] = getFolderFile(DrumbeatsMediaPlayer.mfolder);
			if (file == null)
				return;
			Random random = new Random();
			int j = random.nextInt(file.length);
			DrumbeatsMediaPlayer.mfilename = file[j];
//			playMusic();
		}
		
		DrumbeatsMediaPlayer.bplay = true;
		setButtonPlay(DrumbeatsMediaPlayer.bplay);
		playsong.setText("• " + DrumbeatsMediaPlayer.mfolder.toUpperCase() + " - " + DrumbeatsMediaPlayer.mfilename.toUpperCase() + " •");
		playMusic(DrumbeatsMediaPlayer.mfolder, DrumbeatsMediaPlayer.mfilename);
		if (state_main_body == state_main_body_file)
			filerowadapter.notifyDataSetChanged();
		else if (state_main_body == state_main_body_favorite)
			row_favorite_adapter.notifyDataSetChanged();

	}

	@Override
	public void onBackPressed() {
		openQuitDialog();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		Log.d("test","volumn");
	    switch (keyCode) {
	    case KeyEvent.KEYCODE_VOLUME_UP: {
	    	audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			volumeProgress.setProgress(audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));
			return super.onKeyDown(keyCode, event);
	    }
	    case KeyEvent.KEYCODE_VOLUME_DOWN: {
	    	audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			volumeProgress.setProgress(audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));
			return super.onKeyDown(keyCode, event);
	    }
	    default:
	    	return super.onKeyDown(keyCode, event);
	    }
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//

}
