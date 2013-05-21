package com.android.drumbeat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.android.drumbeat.utils.DatabaseHandler;
import com.android.drumbeat.utils.Song;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
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
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	RelativeLayout main_header;
	ImageButton bt_settings;
	ImageButton bt_more;
	ImageButton bt_favorite;
	Button bt_back;
	final int state_main_body_home = 1;
	final int state_main_body_file = 2;
	final int state_main_body_favorite = 3;
	final int state_main_body_settings = 4;
	final int state_main_body_more = 5;
	
	int state_main_body = state_main_body_home;
	static String KEY_FOLDER = "folder";
	static String KEY_NAME = "name";

	ListView main_body_list;
	RelativeLayout main_body_settings;
	RelativeLayout main_body_more;
	DraggableListView main_body_favorite;
	
	//main footer
	RelativeLayout main_footer;
	Button bt_tempo;
	Button bt_random_console;
	Button bt_play;
	SeekBar volumeProgress;
	TextView playsong;
	
	//tempo view
	RelativeLayout tempo_view;
	Button bt_tempo_view_done;
	Button bt_tempo_view_play;
	Button bt_tempo_view_random;
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
	int countoff;
	ToggleButton bt_random;
	boolean random;
	SharedPreferences preferences;
	
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
		bt_back = (Button) findViewById(R.id.main_header_bt_logo);

		main_body_list = (ListView) findViewById(R.id.main_body_listview);
		main_body_settings = (RelativeLayout) findViewById(R.id.main_body_settings);
		main_body_more = (RelativeLayout) findViewById(R.id.main_body_more);
		main_body_favorite = (DraggableListView) findViewById(R.id.main_body_listview_favorite);

		// main footer
		srate = getResources().getString(R.string.string_default_rate);
		main_footer = (RelativeLayout) findViewById(R.id.main_footer);
		bt_play = (Button) findViewById(R.id.main_footer_bt_play);
		bt_random_console = (Button) findViewById(R.id.main_footer_bt_random);
		bt_tempo = (Button) findViewById(R.id.main_footer_bt_tempo);
		playsong = (TextView) findViewById(R.id.main_footer_song_name);
		
		bt_tempo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTempoView();
				
			}
		});
		
		//tempo view
		tempo_view = (RelativeLayout) findViewById(R.id.tempo_view);
		bt_tempo_view_done = (Button) findViewById(R.id.tempo_view_footer_bt_done);
		bt_tempo_view_play = (Button) findViewById(R.id.tempo_view_footer_bt_playrate);
		bt_tempo_view_random = (Button) findViewById(R.id.tempo_view_footer_bt_randomrate);
		tempo_view_grid = (GridView) findViewById(R.id.tempo_view_grid_view);
		tempo_view_text_on_top = (TextView) findViewById(R.id.tempo_view_text_on_top);
		
		bt_tempo_down = (ImageButton) findViewById(R.id.tempo_view_header_decrease);
		bt_tempo_up = (ImageButton) findViewById(R.id.tempo_view_header_increase);
		
		bt_tempo_down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("test", "selecteddown|" + item_selected);
				if (item_selected != 24){
				tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_grid_selector);
				int number = 0;
				for (int i= 0; i<TextAdapter.arrayrate.length; i++){
			 		if(srate == TextAdapter.arrayrate[i]){
			 			number = i;
			 			break;
			 		}
			 	}
				srate = TextAdapter.arrayrate[number-1];
			 	Log.d("test", "selecteddownrate|" + srate);
			 	for (int i= 0; i<TextAdapter.mTemporary.length; i++){
			 		if(srate == TextAdapter.mTemporary[i]){
			 			item_selected = i;
			 			break;
			 		}
			 	}
			 	Log.d("test", "selecteddown|" + item_selected);
			 	tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_press);
			 	
			 	
			 	if (item_selected == 24){
			 		bt_tempo_down.setEnabled(false);
			 		bt_tempo_up.setEnabled(true);
			 	} else if (item_selected == 3)
			 	{
			 		bt_tempo_down.setEnabled(true);
			 		bt_tempo_up.setEnabled(false);
			 	}						 		
			 	else{
			 		bt_tempo_down.setEnabled(true);
			 		bt_tempo_up.setEnabled(true);
			 	}
				} 
				
				
			}
		});
		bt_tempo_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("test", "selectedup|" + item_selected);
				if (item_selected != 3){
					tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_grid_selector);
					int number = 0;
					for (int i= 0; i<TextAdapter.arrayrate.length; i++){
						if(srate == TextAdapter.arrayrate[i]){
							number = i;
							break;
						}
					}
					srate = TextAdapter.arrayrate[number+1];
					Log.d("test", "selecteduprate|" + srate);
					for (int i= 0; i<TextAdapter.mTemporary.length; i++){
						if(srate == TextAdapter.mTemporary[i]){
							item_selected = i;
							break;
						}
					}
					Log.d("test", "selectedup|" + item_selected);
					tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_press);
					if (item_selected == 24){
						bt_tempo_down.setEnabled(false);
						bt_tempo_up.setEnabled(true);
					} else if (item_selected == 3)
					{
						bt_tempo_down.setEnabled(true);
						bt_tempo_up.setEnabled(false);
					}						 		
					else{
						bt_tempo_down.setEnabled(true);
						bt_tempo_up.setEnabled(true);
					}
				} 
				
				
			}
		});
		
		bt_tempo_view_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Random random = new Random();
				int j =  random.nextInt(26);
				srate = TextAdapter.arrayrate[j];
				tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_grid_selector);
				//Log.d("test","rate|" +  i + "|" +  rate + "|" + srate);
				for (int i= 0; i<TextAdapter.mTemporary.length; i++){
			 		if(srate == TextAdapter.mTemporary[i]){
			 			item_selected = i;
			 			break;
			 		}
			 	}
				tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_press);
				if (item_selected == 24){
			 		bt_tempo_down.setEnabled(false);
			 		bt_tempo_up.setEnabled(true);
			 	} else if (item_selected == 3)
			 	{
			 		bt_tempo_down.setEnabled(true);
			 		bt_tempo_up.setEnabled(false);
			 	}						 		
			 	else{
			 		bt_tempo_down.setEnabled(true);
			 		bt_tempo_up.setEnabled(true);
			 	}
				
			}
		});
		
		bt_tempo_view_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tempo_view.setVisibility(View.GONE);
				main_header.setVisibility(View.VISIBLE);
				showMainBody(state_main_body);
				setRate();
			}
		});
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		tempo_view_grid.getLayoutParams().height = metrics.heightPixels - getResources().getDimensionPixelSize(R.dimen.window_status_bar_height) - getResources().getDimensionPixelSize(R.dimen.main_header_height) - getResources().getDimensionPixelSize(R.dimen.tempo_view_footer_height);
		tempo_view_grid.setLayoutParams(tempo_view_grid.getLayoutParams());
//		int itemHeight = tempo_view_grid.getHeight()/7;
//		item_selected = Integer.valueOf(getResources().getString(R.string.string_default_item_selected));
//		tempo_view_grid.setAdapter(new TextAdapter(this,itemHeight,itemHeight,item_selected));
//		int h = getResources().getDimensionPixelSize(R.dimen.main_header_height);
//		int h2 = getResources().getDimensionPixelSize(R.dimen.tempo_view_footer_height);
		
		int gridheight = tempo_view_grid.getLayoutParams().height/7;//getResources().getDimensionPixelSize(R.dimen.tempo_view_grid_item_height);
		int lastgridheight = tempo_view_grid.getLayoutParams().height/7;//getResources().getDimensionPixelSize(R.dimen.tempo_view_grid_item_height);
		item_selected = Integer.valueOf(getResources().getString(R.string.string_default_item_selected));
		tempo_view_grid.setAdapter(new TextAdapter(this,gridheight,lastgridheight,item_selected));
		
		
		tempo_view_grid.setOnTouchListener(new OnTouchListener(){

			@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					 if(event.getAction() == MotionEvent.ACTION_MOVE){
						  int x=(int)event.getX();  
			              int y=(int)event.getY();  
			              int row = (y ) / (tempo_view_grid.getLayoutParams().height/7);	
			              if (row > 6)
			            	  row = 6;
			              int col =  x  / (tempo_view_grid.getWidth()/4);
			              //Log.d("test", "width|" + tempo_view_grid.getWidth() + "|height|" + tempo_view_grid.getHeight());
			              //Log.d("x-value",""+x);  
			              lastPos = TextAdapter.matrix[row][col];
			              tempo_view_grid.requestFocusFromTouch();
			              tempo_view_grid.setSelection(lastPos);
			              //tempo_view_grid.setSelection(lastPos);
						  srate = TextAdapter.mTemporary[lastPos];
						  tempo_view_text_on_top.setVisibility(View.VISIBLE);
					      tempo_view_text_on_top.setText(srate);
				          return true;
				        } else if(event.getAction() == MotionEvent.ACTION_DOWN){
							  int x=(int)event.getX();  
				              int y=(int)event.getY();  
				              int row = (y ) / (tempo_view_grid.getLayoutParams().height/7);	
				              if (row > 6)
				            	  row = 6;
				              int col =  x  / (tempo_view_grid.getWidth()/4);
				              //Log.d("x-value",""+x);  
				              //Log.d("Y-value",""+y);
				             
//				              if (col == 3 && row == 6) {
//				            	  return true;
//				              }
				              
				              lastPos = TextAdapter.matrix[row][col];
				              //gridView.setSelection(lastPos);
				              
				              tempo_view_grid.requestFocusFromTouch();
				              tempo_view_grid.setSelection(lastPos);
				              srate = TextAdapter.mTemporary[lastPos];
				              tempo_view_text_on_top.setVisibility(View.VISIBLE);
				              tempo_view_text_on_top.setText(srate);
							
					            return true;
					        } 
					 else if (event.getAction() == MotionEvent.ACTION_UP) {  
//						 	tempo_view_grid.setSelection(lastPos);
//						 	tempo_view_grid.requestFocusFromTouch();
						 	//tempo_view_grid.getChildAt(pos).setBackgroundResource(R.drawable.item_press);
//						 	if (item_selected == 3) {
////						 		item_selected = lastPos;
//						 		return true;
//						 	}
						 
						 	tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_grid_selector);
						 	item_selected = lastPos;
						 	tempo_view_grid.getChildAt(item_selected).setBackgroundResource(R.drawable.item_press);
						 	if (item_selected == 24){
						 		bt_tempo_down.setEnabled(false);
						 		bt_tempo_up.setEnabled(true);
						 	} else if (item_selected == 3)
						 	{
						 		bt_tempo_down.setEnabled(true);
						 		bt_tempo_up.setEnabled(false);
						 	}						 		
						 	else{
						 		bt_tempo_down.setEnabled(true);
						 		bt_tempo_up.setEnabled(true);
						 	}
							//srate = TextAdapter.mTemporary[lastPos];
							tempo_view_text_on_top.setVisibility(View.GONE);

								return true;
			              }
				        return false;
				}

			});
		
		// settings body
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		//favorite body
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
					if (!state_edit){
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
				state_main_body = state_main_body_home;
				showMainBody(state_main_body);
			}
		});

		showMainBody(state_main_body);
		setRate();
		initControlsvolume();

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
	public void setRate(){
		bt_tempo.setText(srate);
	}
	private void initControlsvolume()
    {
        try
        {
        	final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        	volumeProgress.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        	volumeProgress.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));   


        	volumeProgress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) 
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) 
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

	public void showMainBodyHome() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
		bt_more.setImageResource(R.drawable.morebutton_white);
		bt_back.setCompoundDrawablesWithIntrinsicBounds(this.getResources()
				.getDrawable(R.drawable.logo_button), null, null, null);
		bt_back.setText("");

		main_body_settings.setVisibility(View.GONE);
		main_body_more.setVisibility(View.GONE);
		main_body_favorite.setVisibility(View.GONE);
		main_body_list.setVisibility(View.VISIBLE);
		main_footer.setVisibility(View.VISIBLE);

		final String array_folder[] = getFolder();
		ArrayList<HashMap<String, String>> array_list_folder = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < array_folder.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_FOLDER, array_folder[i]);
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
				String folder = array_folder[postion];
				showMainBodyFile(folder);

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
		bt_back.setCompoundDrawablesWithIntrinsicBounds(this.getResources()
				.getDrawable(R.drawable.red_back_arrow), null, null, null);
		bt_back.setText(folder);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		bt_back.setTypeface(font);

		String[] array_file = getFolderFile(folder);
		ArrayList<HashMap<String, String>> array_list_file = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < array_file.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_FOLDER, folder);
			map.put(KEY_NAME, array_file[i]);
			array_list_file.add(map);
		}

		// Getting adapter
		FileRowAdapter adapter = new FileRowAdapter(this, array_list_file);
		main_body_list.setAdapter(adapter);
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

	public void showMainBodyFavorite() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white_pressed);
		bt_more.setImageResource(R.drawable.edit_button);
		bt_back.setCompoundDrawablesWithIntrinsicBounds(this.getResources()
				.getDrawable(R.drawable.red_back_arrow), null, null, null);
		bt_back.setText(R.string.favorite);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		bt_back.setTypeface(font);

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
			 if (favorite_songs != null){
				 array_list_favorite_file = new ArrayList<HashMap<String, String>>();

					for (int i = 0; i < favorite_songs.size(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(KEY_FOLDER, favorite_songs.get(i).getFolder());
						map.put(KEY_NAME, favorite_songs.get(i).getName());
						array_list_favorite_file.add(map);
					}

					// Getting adapter
					FavoritesRowAdapter adapter = new FavoritesRowAdapter(this,
							array_list_favorite_file, false);
					main_body_favorite.setAdapter(adapter);
					main_body_favorite.setmove(false);
					main_body_favorite.setSmoothScrollbarEnabled(true);
			 } else{
				 main_body_favorite.setAdapter(null);
			 }
			 
			 }catch(SQLException sqle){
			  
			 throw sqle;
			  
			 }

	}
	public void showMainBodyFavoriteEdit() {
		try {
			
			 myDbHelper.openDataBase();
			 favorite_songs = myDbHelper.getFavariteSong();
			 myDbHelper.close();
			 if (favorite_songs != null){
				 array_list_favorite_file = new ArrayList<HashMap<String, String>>();

					for (int i = 0; i < favorite_songs.size(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(KEY_FOLDER, favorite_songs.get(i).getFolder());
						map.put(KEY_NAME, favorite_songs.get(i).getName());
						array_list_favorite_file.add(map);
					}

					// Getting adapter
					row_favorite_adapter = new FavoritesRowAdapter(this,
							array_list_favorite_file, true);
					main_body_favorite.setDropListener(mDropListener);
					//main_body_favorite.setRemoveListener(mRemoveListener);
					main_body_favorite.setDragListener(mDragListener);
					main_body_favorite.setAdapter(row_favorite_adapter);
					main_body_favorite.setmove(true);
					main_body_favorite.setSmoothScrollbarEnabled(true);
			 } else{
				 main_body_favorite.setAdapter(null);
			 }
			 
			 }catch(SQLException sqle){
			  
			 throw sqle;
			  
			 }
		 
		

	}
	public void openRemoveDialog(final int position){
	  	  AlertDialog.Builder RemoveDialog 
	  	   = new AlertDialog.Builder(MainActivity.this);
	  	RemoveDialog.setTitle("Do you remove this song from favorite list?");
	  	  
	  	RemoveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					removefavorite(position);
					favorite_songs.remove(position);
					array_list_favorite_file.remove(position);
					row_favorite_adapter.notifyDataSetChanged();
					
					
				}
	  	  });   	  
	  	RemoveDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

	  		  public void onClick(DialogInterface dialog, int which) {
	  	    // TODO Auto-generated method stub
	  	    
	  	   }});
	  	  
	  	RemoveDialog.show();
	  	 }
	public void removefavorite(int position) {
			myDbHelper.openDataBase();
			myDbHelper.removefavorite(favorite_songs.get(position).getFolder(), favorite_songs.get(position).getName());
			myDbHelper.close();
		}
	private String[] getFavoriteFile() {

		// String[] files = null;
		String[] files = { "audio 1", " bai hay", "thu thoi", "the nhi", " bai hay", "thu thoi", "the nhi", " bai hay", "thu thoi", "the nhi" };

		return files;
	}

	private DropListener mDropListener = new DropListener() {
		public void onDrop(int from, int to) {
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
			// objectlistchannel.onDrop(from, to);
			// Log.v("movechannel", "From" + from + "To" + to);
			// int temp = iodernumber.get(from);
			// iodernumber.remove(from);
			// iodernumber.add(to,temp);
			// mObjectInfo.updateallodernumber(iodernumber, m_mode);
			// GetInformation(0,izoomlevel, m_mode);
			// myListView.invalidateViews();
		}
	};
//
//	private RemoveListener mRemoveListener = new RemoveListener() {
//		public void onRemove(int which) {
//			 if (which < 0 || which > array_favorite_file.length) return;
//			// iodernumber.remove(which);
//			// myListView.invalidateViews();
//		}
//	};

	private DragListener mDragListener = new DragListener() {

		//int backgroundColor;
		int defaultBackgroundColor;

		public void onDrag(int x, int y, ListView listView) {
			// TODO Auto-generated method stub
		}

		public void onStartDrag(View itemView) {
			itemView.setVisibility(View.INVISIBLE);
			defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
			itemView.setBackgroundResource(R.color.blue);
			// ImageView iv = (ImageView)itemView.findViewById(R.id.icon);
			// if (iv != null) iv.setVisibility(View.INVISIBLE);
		}

		public void onStopDrag(View itemView) {
			itemView.setVisibility(View.VISIBLE);
			itemView.setBackgroundColor(defaultBackgroundColor);
			// ImageView iv =
			// (ImageView)itemView.findViewById(R.id.iconchannel);
			// if (iv != null) iv.setVisibility(View.VISIBLE);
		}

	};

	public void showMainBodySettings() {
		bt_settings.setImageResource(R.drawable.settingsbutton_white_pressed);
		bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
		bt_more.setImageResource(R.drawable.morebutton_white);
		bt_back.setCompoundDrawablesWithIntrinsicBounds(this.getResources()
				.getDrawable(R.drawable.red_back_arrow), null, null, null);
		bt_back.setText(R.string.settings);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/MYRIADPRO-BOLD.OTF");
		bt_back.setTypeface(font);

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
		bt_back.setText(R.string.more);
		bt_back.setCompoundDrawablesWithIntrinsicBounds(this.getResources()
				.getDrawable(R.drawable.red_back_arrow), null, null, null);

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
	
//	public void playMediaPlayer()
//	{
//		if (mp != null){
//			if (mp.isPlaying()){
//				mp.stop();
//				//mp.release();
//				
//			}
//			}
//		else 
//			mp = new MediaPlayer();
//		
//		try {
//			if (favorite_mode){
//			  if (lastPosition >= folder_favorite.length)
//				  lastPosition = 0;
//			  final String filename[] = getFileName(folder_favorite[lastPosition],  name_favortie[lastPosition]);
//			  String precount = filename[lastPosition].substring(filename[lastPosition].length() - 10);
//			  maxCount = preferences.getInt("countoff", 0);
//				if (maxCount > 0 && !bplay){
//				file = getResources().getAssets().openFd("precount/" + precount);
//				mp.reset();
//				mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//				mp.prepare();
//				mp.start();
//				bplay = true;
//				setIconPlayButton(bplay,mplaysong);
//				mp.setOnCompletionListener(new OnCompletionListener(){
//					  int count = 0; // initialize somewhere before, not sure if this will work here
//					  
//
//					  @Override
//					  public void onCompletion(MediaPlayer mediaPlayer) {
//					    if(count < maxCount -1) {
//					      count++;
//					      mediaPlayer.seekTo(0);
//					      mediaPlayer.start();
//					    } else {
//					    	try {
//					    		file = getResources().getAssets().openFd("music/" + folder_favorite[lastPosition] + "/" + name_favortie[lastPosition] + "/" + filename[rate]);
//								  mplaysong = folder_favorite[lastPosition] + " - " + name_favortie[lastPosition];
//								mp.reset();
//								try {
//									mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//									mp.prepare();
//									mp.setLooping(true);
//									mp.start();
//									
//								} catch (IllegalArgumentException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (IllegalStateException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							
//														
//					    	
//					    }
//					}});
//				} else {
//					file = getResources().getAssets().openFd("music/" + folder_favorite[lastPosition] + "/" + name_favortie[lastPosition] + "/" + filename[rate]);
//					  mplaysong = folder_favorite[lastPosition] + " - " + name_favortie[lastPosition];
//				mp.reset();
//				mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//				mp.prepare();
//				mp.setLooping(true);
//				mp.start();
//				bplay = true;
//				setIconPlayButton(bplay,mplaysong);
//				}
//			  
//			  file = getResources().getAssets().openFd("music/" + folder_favorite[lastPosition] + "/" + name_favortie[lastPosition] + "/" + filename[rate]);
//			  mplaysong = folder_favorite[lastPosition] + " - " + name_favortie[lastPosition];
//			  mp.reset();
//			  mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//			} else {
//				final String filename[] = getFileName(folder,  name[lastPosition]);
//				String precount = filename[lastPosition].substring(filename[lastPosition].length() - 10);
//				maxCount = preferences.getInt("countoff", 0);
//				if (maxCount > 0 && !bplay){
//				file = getResources().getAssets().openFd("precount/" + precount);
//				mp.reset();
//				mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//				mp.prepare();
//				mp.start();
//				bplay = true;
//				setIconPlayButton(bplay,mplaysong);
//				mp.setOnCompletionListener(new OnCompletionListener(){
//					  int count = 0; // initialize somewhere before, not sure if this will work here
//					  
//
//					  @Override
//					  public void onCompletion(MediaPlayer mediaPlayer) {
//					    if(count < maxCount -1) {
//					      count++;
//					      mediaPlayer.seekTo(0);
//					      mediaPlayer.start();
//					    } else {
//					    	try {
//								file = getResources().getAssets().openFd("music/" + folder + "/" + name[lastPosition] + "/" + filename[rate]);
//								mplaysong = folder + " - " + name[lastPosition];
//								mp.reset();
//								try {
//									mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//									mp.prepare();
//									mp.setLooping(true);
//									mp.start();
//									
//								} catch (IllegalArgumentException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (IllegalStateException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							
//														
//					    	
//					    }
//					}});
//				} else {
//				file = getResources().getAssets().openFd("music/" + folder + "/" + name[lastPosition] + "/" + filename[rate]);
//				mplaysong = folder + " - " + name[lastPosition];
//				mp.reset();
//				mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//				mp.prepare();
//				mp.setLooping(true);
//				mp.start();
//				bplay = true;
//				setIconPlayButton(bplay,mplaysong);
//				}
//			}
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
////		if (mp.isPlaying())
////			mp.stop();
//		//mp.se
//		
//		
//	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//


}