package com.drumbeat.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.drumbeat.utils.DatabaseHandler;
import com.drumbeat.utils.Song;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ListviewLevel2 extends Activity {
	

	String xml;
	ListView lv;
	
	Button bt_tempo;
	Button bt_random;
	static Button bt_play;
	static TextView playsong;
	SeekBar volumeprogress;
	
	String[] values = new String[] {"Started Pack", "Break Beats", "Hit Songs 1", "Pop-rock 1" };
	String[] name;
	String[] id;
	String[] values2 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values3 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values4 = new String[] {"Audio1", "Audio2", "Audio3"};
	
	ListviewLevel2RowAdapter adapter;
	ArrayList<HashMap<String, String>> array_name;
	static String KEY_NAME = "name";
	static String KEY_ID = "id";
	public static String folder = "";

	static String KEY_FOLDER = "folder";
	FavoritesRowAdapter adapter_favorite;
	ArrayList<HashMap<String, String>> array_name_favorite;
	String[] name_favortie;
	String[] id_favorite;
	String[] folder_favorite;

	// Flag for current page
	int current_page = 1;

	float volume;
	int streamid = 1;
	private static SoundPool soundPool;

	private int soundID;
	String srate;
	static int REQUEST_TEMPO = 2;
	float rate;
	
	ImageButton bt_settings;
	ImageButton bt_more;
	ImageButton bt_favorite;
	Button bt_back;
	
	RelativeLayout tempo_view;
	GridView gridView;
	int lastPos;
	Button bt_tempo_view_play;
	RelativeLayout listview2_view;
	
	boolean favorite_mode = false;
	boolean tempo_view_mode = false;
	DatabaseHandler myDbHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview2);
		listview2_view = (RelativeLayout) findViewById(R.id.listview2_view); 
		Intent in = getIntent();
		Bundle bundle = in.getExtras();
		favorite_mode = bundle.getBoolean(ListviewLevel1.KEY_FAVORITE_MODE);
		if (!favorite_mode)
		folder = bundle.getString(ListviewLevel1.KEY_FOLDER);
		
		myDbHelper = new DatabaseHandler(this);
		  

		
		lv = (ListView) findViewById(R.id.listview2_activity);
		bt_tempo = (Button) findViewById(R.id.bt_tempo);
		bt_random = (Button) findViewById(R.id.bt_random);
		bt_play = (Button) findViewById(R.id.bt_play);
		volumeprogress = (SeekBar) findViewById(R.id.volumeProgressBar);
		playsong = (TextView) findViewById(R.id.listview_playsong);
		
		bt_settings = (ImageButton) findViewById(R.id.listview2_bt_settings);
		bt_settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ListviewLevel2.this, SettingsActivity.class);
				startActivity(i);
			}
		});
		
		bt_more = (ImageButton) findViewById(R.id.listview2_bt_more_edit);
		
		bt_favorite = (ImageButton) findViewById(R.id.listview2_bt_favorites);

		
		bt_back = (Button) findViewById(R.id.listview2_bt_logo);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MYRIADPRO-BOLD.OTF");  
		bt_back.setTypeface(font);
		loadUI(favorite_mode);
		
		initControlsvolume();
		setIconPlayButton(SoundpoolState.getState(),SoundpoolState.getplaysong());
		
		

		/**
		 * Listening to Load More button click event
		 * */
		
//		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//		float actualVolume = (float) audioManager
//				.getStreamVolume(AudioManager.STREAM_MUSIC);
//		float maxVolume = (float) audioManager
//				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		//volume = actualVolume / maxVolume;
		volume = 1;
		srate = "150";
		rate = 1;
		/**
		 * Listening to listview single row selected
		 * **/
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setFocusable(false);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
					long arg3) {
				// TODO Auto-generated method stub
				//Log.d("Test","Sao khong vao day");
				playMusic(postion);
				
				
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

	        @Override
	        public boolean onItemLongClick(AdapterView<?> parent, View view,
	                int arg2, long arg3) {

	                           // Can't manage to remove an item here
	        	if (favorite_mode)
	        	openRemoveDialog(arg2);
	            return false;
	        }

	    });
		//int picId = getResources().getIdentifier("ogg_country_straightclean", "raw", getApplicationContext().getPackageName());
		bt_tempo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(ListviewLevel2.this,ActivityStack.class);
//				i.putExtra("rate", srate);
//				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivityForResult(i, REQUEST_TEMPO);
				
				tempo_view.setVisibility(View.VISIBLE);
				tempo_view_mode = true;
				listview2_view.setVisibility(View.GONE);
				if (SoundpoolState.getState())
				{
					bt_tempo_view_play.setBackgroundResource(R.drawable.stop_button);
					
				}
				else
				{
					bt_tempo_view_play.setBackgroundResource(R.drawable.play_button);
					
				}
				//setrandomrate();
				
			}
		});
		
		bt_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(ListviewLevel2.this,TempoActivity.class);
				//startActivity(i);
				setrandomrate();
				
			}
		});
		
		bt_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SoundpoolState.getState())
				{
					stopMusic();
					
				}
				else
				{
					int id = 0;
					if (lv.getCheckedItemPosition() >= 0)
						id = lv.getCheckedItemPosition();
					playMusic(id);
					
				}
			}
		});
		
		
		
		
		//Tempo View
		tempo_view = (RelativeLayout) findViewById(R.id.listview2_tempo_view);
		tempo_view.setVisibility(View.GONE);
		gridView = (GridView) findViewById(R.id.listview2_tempo_view_grid_view);
		Button bt_tempo_view_random = (Button) findViewById(R.id.listview2_tempo_view_bt_randomrate);
        Button bt_tempo_view_done = (Button) findViewById(R.id.listview2_tempo_view_bt_done);
        bt_tempo_view_play = (Button) findViewById(R.id.listview2_tempo_view_bt_playrate);
        //Intent in = getIntent();
		//Bundle bundle = in.getExtras();
		//srate = bundle.getString("rate");
        bt_tempo_view_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SoundpoolState.getState())
				{
					stopMusic();
					bt_tempo_view_play.setBackgroundResource(R.drawable.play_button);
					
				}
				else
				{
					int id = 0;
					if (lv.getCheckedItemPosition() >= 0)
						id = lv.getCheckedItemPosition();
					playMusic(id);
					bt_tempo_view_play.setBackgroundResource(R.drawable.stop_button);
					
				}
			}
		});
		
        bt_tempo_view_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tempo_view.setVisibility(View.GONE);
				listview2_view.setVisibility(View.VISIBLE);
				tempo_view_mode = false;
				//donerate();
			}
		});
		bt_tempo_view_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(ListviewLevel2.this,TempoActivity.class);
				//startActivity(i);
				setTemporandomrate();
				
			}
		});
		
		// Instance of ImageAdapter Class
		//gridView.setAdapter(new ImageAdapter(this));
		gridView.setAdapter(new TextAdapter(this));
		//gridView.setDrawSelectorOnTop(true);
		
		gridView.setSelected(true);
		gridView.setFocusable(true);
		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// Sending image id to FullScreenActivity
				lastPos = position;
				gridView.setSelection(lastPos);
				gridView.requestFocusFromTouch();
				gridView.setSelection(lastPos);
				srate = TextAdapter.mTemporary[lastPos];
		         for (int i=0; i<TextAdapter.arrayrate.length; i++)
		     	{
		     		if (TextAdapter.arrayrate[i].equalsIgnoreCase(srate))
		     		{
		     			float step = (float)5/120;
		     			rate = (float) (0.5 + i*step);
		     			if (soundPool != null)
		     			soundPool.setRate(streamid, rate);
		     			setbuttonrate(srate);
		 				return;
		     		}
		     	}
//				gridView.setSelection((int)(gridView.getAdapter()).getItemId(lastPos));
//				gridView.requestFocus();
				
				//v.setBackgroundResource(R.drawable.pressedback);
//				v.setSelected(true);
				
//				Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
//				// passing array index
//				i.putExtra("id", position);
//				startActivity(i);
			}
		});
		
		gridView.setOnTouchListener(new OnTouchListener(){

		@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 if(event.getAction() == MotionEvent.ACTION_MOVE){
					 
			            return true;
			        }
			        return false;
//			  int x=(int)event.getX();  
//              int y=(int)event.getY();  
//              Log.d("x-value",""+x);  
//              Log.d("Y-value",""+y);  
              //image = new ImageView(DockitControllerActivity.this);  
              //image.setImageBitmap(im.imageView.getDrawingCache());  
              //image.setImageResource(R.drawable.a_1);  
              //Layout.addView(image, params);  
//            Toast.makeText(DockitControllerActivity.this,""+x+" "+y, Toast.LENGTH_SHORT);  
//              if (event.getAction() == MotionEvent.ACTION_DOWN) {  
//                    
//                  image = new ImageView(TempoActivity.this);  
//                  //Bitmap bp=btn.getDrawingCache();  
//                  //Log.d("bitmap in dragme",""+bp);  
//                  image.setImageResource(R.drawable.a_1);  
//                  Layout.addView(image, params);  
//                  System.out.println("start Dragging");  
//              }  
//              if (event.getAction() == MotionEvent.ACTION_UP) {  
//                  //status = STOP_DRAGGING;  
//                  Log.i("Drag", "Stopped Dragging");  
//              if (event.getAction() == MotionEvent.ACTION_DOWN) {  
//                  //if (status == START_DRAGGING) {  
//                      //TextView txt = (TextView) v;
//                      v.setBackgroundResource(R.color.white);
//                     // String tmp = txt.getText().toString();
//                      //Toast.makeText(TempoActivity.this, tmp, Toast.LENGTH_LONG).show();
//                      return true;
//                  //}  
//              }  
//            
//          return false;  
			}

		});
	}
	public void loadUI(boolean mode){
		if (mode){
			bt_more.setImageResource(R.drawable.edit_button);
			bt_favorite.setImageResource(R.drawable.favoritesbutton_white_pressed);
//			bt_favorite.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent i = new Intent(ListviewLevel2.this, FavoritesActivity.class);
//					startActivity(i);
//				}
//			});

			bt_more.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Intent i = new Intent(ListviewLevel2.this, MoreActivity.class);
//					startActivity(i);
				}
			});
			bt_back.setText("   Favorites");
			
			bt_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (folder.length() > 0){
						//Log.d("test","test|" + folder);
						favorite_mode = false;
						onResume();
					} else {
						Intent i = new Intent(ListviewLevel2.this, ListviewLevel1.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}
				}
			});
			try {
				 
				 myDbHelper.openDataBase();
				 List<Song> songs = myDbHelper.getFavariteSong();
				 if (songs != null)
				 {
				 name_favortie = new String[songs.size()];
				 id_favorite = new String[songs.size()];
				 folder_favorite = new String[songs.size()];
				 for(int i=0; i<songs.size(); i++)
				 {
					 name_favortie[i] = songs.get(i).getName();
					 id_favorite[i] = songs.get(i).getID();
					 folder_favorite[i] = songs.get(i).getFolder();
				 }
				 }
				  
				 }catch(SQLException sqle){
				  
				 throw sqle;
				  
				 }
			 myDbHelper.close();
			array_name_favorite = new ArrayList<HashMap<String, String>>();
			if (name_favortie != null){
			
			for (int i=0; i<name_favortie.length; i++){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_NAME,name_favortie[i]);
				map.put(KEY_ID,id_favorite[i]);
				map.put(KEY_FOLDER,folder_favorite[i]);
				array_name_favorite.add(map);
			}
			

			// Getting adapter
			adapter_favorite = new FavoritesRowAdapter(this, array_name_favorite);
			lv.setAdapter(adapter_favorite);
			}
		} else {
			
			bt_more.setImageResource(R.drawable.morebutton_white);
			bt_favorite.setImageResource(R.drawable.favoritesbutton_white);
			bt_favorite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Intent i = new Intent(ListviewLevel2.this, FavoritesActivity.class);
					//startActivity(i);
					favorite_mode = true;
					onResume();
				}
			});

			bt_more.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ListviewLevel2.this, MoreActivity.class);
					startActivity(i);
				}
			});
			bt_back.setText("   " + folder);
			
			bt_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ListviewLevel2.this, ListviewLevel1.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			});
			 try {
				 
				 myDbHelper.openDataBase();
				 List<Song> songs = myDbHelper.getListSongbyFolder(folder);
				 name = new String[songs.size()];
				 id = new String[songs.size()];
				 for(int i=0; i<songs.size(); i++)
				 {
					 name[i] = songs.get(i).getName();
					 id[i] = songs.get(i).getID();
				 }
				  
				 }catch(SQLException sqle){
				  
				 throw sqle;
				  
				 }
			 myDbHelper.close();
			 array_name = new ArrayList<HashMap<String, String>>();
				
				for (int i=0; i<name.length; i++){
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(KEY_NAME,name[i]);
					map.put(KEY_ID, id[i]);
					array_name.add(map);
				}
				

				// Getting adapter
				adapter = new ListviewLevel2RowAdapter(this, array_name);
				lv.setAdapter(adapter);
			
		}
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		loadUI(favorite_mode);
			setIconPlayButton(SoundpoolState.getState(),SoundpoolState.getplaysong());
		super.onResume();
	}
	public void playMusic(int postion)
	{
//		SoundpoolState.setStateList(true);
//		setIconPlayButton(SoundpoolState.getState());
		
		if (soundPool != null)
			soundPool.release();

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool
				.setOnLoadCompleteListener(new OnLoadCompleteListener() {

					@Override
					public void onLoadComplete(SoundPool soundPool,
							int sampleId, int status) {
						//loaded = true;
						//
						SoundpoolState.setStateList(true);
						setIconPlayButton(SoundpoolState.getState(),SoundpoolState.getplaysong());
						streamid = soundPool.play(soundID, volume,
								volume, 1, -1, rate);
//						Toast.makeText(ListviewLevel2.this,
//								"Loop forever " + streamid,
//								Toast.LENGTH_LONG).show();


					}
				});
		if (favorite_mode){
			int songId = getResources().getIdentifier("song" + id_favorite[postion], "raw", getApplicationContext().getPackageName());
			SoundpoolState.setplaysong(name_favortie[postion]);
			soundID = soundPool.load(ListviewLevel2.this,
				songId, 1);
		} else {
			int songId = getResources().getIdentifier("song" + id[postion], "raw", getApplicationContext().getPackageName());
			SoundpoolState.setplaysong(name[postion]);
			soundID = soundPool.load(ListviewLevel2.this,
					songId, 1);
		}
	}
	
	public static void stopMusic()
	{
		if (SoundpoolState.getStateFav())
			FavoritesActivity.stopMusic();
		SoundpoolState.setStateList(false);
		SoundpoolState.setplaysong("");
		setIconPlayButton(SoundpoolState.getState(),"");
		
		if (soundPool != null)
			soundPool.release();
	}
	
	public static void setIconPlayButton(boolean play, String splaysong)
	{
		if (bt_play != null){
			if (play)
				bt_play.setBackgroundResource(R.drawable.stop_button);
			else
				bt_play.setBackgroundResource(R.drawable.play_button);
			}
		if ( playsong != null){
				playsong.setText(splaysong);
			
			}
	}
	public void setrandomrate()
	{
		Random random = new Random();
		int i =  random.nextInt(26);
		float step = (float)5/120;
		rate = (float) (0.5 + i*step);
		srate = TextAdapter.arrayrate[i];
		if (soundPool != null)
		soundPool.setRate(streamid, rate);
		setbuttonrate(srate);
	}
	public void setTemporandomrate()
	{
		Random random = new Random();
		int i =  random.nextInt(26);
		
		float step = (float)5/120;
		rate = (float) (0.5 + i*step);
		
		srate = TextAdapter.arrayrate[i];
		//Log.d("test","rate|" +  i + "|" +  rate + "|" + srate);
        for (int j=0; i<TextAdapter.mTemporary.length; j++)
    	{
    		if (TextAdapter.mTemporary[j].equalsIgnoreCase(srate))
    		{
    			lastPos = j;
				break;
    		}
    	}
        
		gridView.setSelection(lastPos);
		gridView.requestFocusFromTouch();
		gridView.setSelection(lastPos);
		setbuttonrate(srate);
		if (soundPool != null)
		soundPool.setRate(streamid, rate);
	}
	public void setbuttonrate(String input)
	{
		bt_tempo.setText(input);
	}
	private void initControlsvolume()
    {
        try
        {
        	final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            volumeprogress.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeprogress.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));   


            volumeprogress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
			Log.d("test","vao day moi dauafadf");
			  if (requestCode == REQUEST_TEMPO) {

			     if(resultCode == RESULT_OK){      
			         srate = data.getStringExtra("result");   
			         Toast.makeText(this, srate, Toast.LENGTH_LONG).show();
			         for (int i=0; i<TextAdapter.arrayrate.length; i++)
			     	{
			     		if (TextAdapter.arrayrate[i].equalsIgnoreCase(srate))
			     		{
			     			rate = (float) (0.5 + i*0.05);
			     			if (soundPool != null)
			     			soundPool.setRate(streamid, rate);
			     			setbuttonrate(srate);
			 				return;
			     		}
			     	}
			     }
			     if (resultCode == RESULT_CANCELED) {    
			         //Write your code on no result return 
			     }
			  }

		super.onActivityResult(requestCode, resultCode, data);
	}
	private void openRemoveDialog(final int position){
	  	  AlertDialog.Builder RemoveDialog 
	  	   = new AlertDialog.Builder(ListviewLevel2.this);
	  	RemoveDialog.setTitle("Do you remove this song from favorite list?");
	  	  
	  	RemoveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					array_name_favorite.remove(position);
					removefavorite(position);
					adapter_favorite.notifyDataSetChanged();
					
					
				}
	  	  });   	  
	  	RemoveDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

	  		  public void onClick(DialogInterface dialog, int which) {
	  	    // TODO Auto-generated method stub
	  	    
	  	   }});
	  	  
	  	RemoveDialog.show();
	  	 }
	public void removefavorite(int position) {
      String sid = id[position];
		DatabaseHandler myDbHelper = new DatabaseHandler(this);
		myDbHelper.openDataBase();
		myDbHelper.removefavorite(sid);
		myDbHelper.close();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (tempo_view_mode){
			tempo_view.setVisibility(View.GONE);
			listview2_view.setVisibility(View.VISIBLE);
			tempo_view_mode = false;
		} else {
			super.onBackPressed();
		}
	}
  
//	private void openQuitDialog(){
//	  	  AlertDialog.Builder quitDialog 
//	  	   = new AlertDialog.Builder(ListviewLevel2.this);
//	  	  quitDialog.setTitle("Confirm to Quit?");
//	  	  
//	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					finish();
//					
//				}
//	  	  });   	  
//	  	  quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//	  		  public void onClick(DialogInterface dialog, int which) {
//	  	    // TODO Auto-generated method stub
//	  	    
//	  	   }});
//	  	  
//	  	  quitDialog.show();
//	  	 }
//
//	@Override
//	  public void onBackPressed() {
//		openQuitDialog(); 
//	  }
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_timeline, menu);
//		return true;
//	}

}
