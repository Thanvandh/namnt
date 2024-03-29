package com.drumbeat.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FavoritesActivity extends Activity {
	

//	String xml;
//	ListView lv;
//	FavoritesRowAdapter adapter;
//	ArrayList<HashMap<String, String>> array_name;
//	String[] values = new String[] {"Started Pack", "Break Beats", "Hit Songs 1", "Pop-rock 1" };
//	String[] values1 = new String[] {"Audio1", "Audio2", "Audio3"};
//	String[] values2 = new String[] {"Audio1", "Audio2", "Audio3"};
//	String[] values3 = new String[] {"Audio1", "Audio2", "Audio3"};
//	String[] values4 = new String[] {"Audio1", "Audio2", "Audio3"};
//	
//	static String KEY_NAME = "name";
//	static String KEY_ID = "id";
//	static String KEY_FOLDER = "folder";
//	String[] name;
//	String[] id;
//	String[] folder;
//
//	ImageButton bt_settings;
//	ImageButton bt_more;
//	Button bt_back;
//	
//	Button bt_tempo;
//	Button bt_random;
//	static Button bt_play;
//	static TextView playsong;
//	SeekBar volumeprogress;
//	float volume;
//	int streamid = 1;
//	private static SoundPool soundPool;
//	private int soundID;
//	// Flag for current page
//	int current_page = 1;
//	String srate;
//	int REQUEST_TEMPO = 1;
//	float rate;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_favorites);
//		lv = (ListView) findViewById(R.id.listview_favorites_activity);
//		DatabaseHandler myDbHelper = new DatabaseHandler(this);
//		  
//
//		 try {
//			 
//			 myDbHelper.openDataBase();
//			 List<Song> songs = myDbHelper.getFavariteSong();
//			 name = new String[songs.size()];
//			 id = new String[songs.size()];
//			 folder = new String[songs.size()];
//			 for(int i=0; i<songs.size(); i++)
//			 {
//				 name[i] = songs.get(i).getName();
//				 id[i] = songs.get(i).getID();
//				 folder[i] = songs.get(i).getFolder();
//			 }
//			  
//			 }catch(SQLException sqle){
//			  
//			 throw sqle;
//			  
//			 }
//		 myDbHelper.close();
//		array_name = new ArrayList<HashMap<String, String>>();
//		
//		for (int i=0; i<name.length; i++){
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put(KEY_NAME,name[i]);
//			map.put(KEY_ID,id[i]);
//			map.put(KEY_FOLDER,folder[i]);
//			array_name.add(map);
//		}
//		
//
//		// Getting adapter
//		adapter = new FavoritesRowAdapter(this, array_name);
//		lv.setAdapter(adapter);
//
//		/**
//		 * Listening to Load More button click event
//		 * */
//		
//		volume = 1;
//		srate = "150";
//		rate = 1;
//		/**
//		 * Listening to listview single row selected
//		 * **/
//		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//		lv.setFocusable(false);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
//					long arg3) {
//				// TODO Auto-generated method stub
//				//Log.d("Test","Sao khong vao day");
//				playMusic(postion);
//				
//				
//			}
//		});
//		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//	        @Override
//	        public boolean onItemLongClick(AdapterView<?> parent, View view,
//	                int arg2, long arg3) {
//
//	                           // Can't manage to remove an item here
//	        	openRemoveDialog(arg2);
//	            return false;
//	        }
//
//	    });
//		
//		
//		
//		bt_settings = (ImageButton) findViewById(R.id.favorite_bt_settings);
//		
//		bt_settings.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(FavoritesActivity.this, SettingsActivity.class);
//				startActivity(i);
//			}
//		});
//		
//	  
//		
//		bt_more = (ImageButton) findViewById(R.id.favorite_bt_more_edit);
//		bt_back = (Button) findViewById(R.id.favorite_bt_logo);
//		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MYRIADPRO-BOLD.OTF");  
//		bt_back.setTypeface(font);
//		
//		bt_back.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				onBackPressed();
//			}
//		});
//		
//
//		
//		bt_tempo = (Button) findViewById(R.id.favorite_bt_tempo);
//		bt_random = (Button) findViewById(R.id.favorite_bt_random);
//		bt_play = (Button) findViewById(R.id.favorite_bt_play);
//		volumeprogress = (SeekBar) findViewById(R.id.favorite_volumeProgressBar);
//		playsong = (TextView) findViewById(R.id.favorite_playsong);
//		
//		initControlsvolume();
//		setIconPlayButton(SoundpoolState.getState(),SoundpoolState.getplaysong());
//		bt_tempo.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(FavoritesActivity.this,TempoActivity.class);
//				i.putExtra("rate", srate);
//				startActivityForResult(i, REQUEST_TEMPO);
//				//setrandomrate();
//				
//			}
//		});
//		
//		bt_random.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//Intent i = new Intent(ListviewLevel2.this,TempoActivity.class);
//				//startActivity(i);
//				setrandomrate();
//				
//			}
//		});
//		
//		bt_play.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (SoundpoolState.getState())
//				{
//					stopMusic();
//					
//				}
//				else
//				{
//					int id = 0;
//					if (lv.getCheckedItemPosition() >= 0)
//						id = lv.getCheckedItemPosition();
//					playMusic(id);
//					
//				}
//			}
//		});
//		
//	}
//	public void playMusic(int postion)
//	{
////		SoundpoolState.setStateFav(true);
////		SoundpoolState.
////		setIconPlayButton(SoundpoolState.getState());
//		
//		if (soundPool != null)
//			soundPool.release();
//
//		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//		soundPool
//				.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//					@Override
//					public void onLoadComplete(SoundPool soundPool,
//							int sampleId, int status) {
//						//loaded = true;
//						//
//						SoundpoolState.setStateFav(true);
//						setIconPlayButton(SoundpoolState.getState(),SoundpoolState.getplaysong());
//						streamid = soundPool.play(soundID, volume,
//								volume, 1, -1, rate);
////						Toast.makeText(ListviewLevel2.this,
////								"Loop forever " + streamid,
////								Toast.LENGTH_LONG).show();
//
//
//					}
//				});
//		int songId = getResources().getIdentifier("song" + id[postion], "raw", getApplicationContext().getPackageName());
//		SoundpoolState.setplaysong(name[postion]);
//		soundID = soundPool.load(FavoritesActivity.this,
//				songId, 1);
//	}
//	
//	public static void stopMusic()
//	{
//		if(SoundpoolState.getStateList())
//			ListviewLevel2.stopMusic();
//		SoundpoolState.setStateFav(false);
//		SoundpoolState.setplaysong("");
//		setIconPlayButton(SoundpoolState.getState(),"");
//		
//		if (soundPool != null)
//			soundPool.release();
//	}
//	
//	public static void setIconPlayButton(boolean play, String splaysong)
//	{
//		if (bt_play != null){
//			if (play)
//				bt_play.setBackgroundResource(R.drawable.stop_button);
//			else
//				bt_play.setBackgroundResource(R.drawable.play_button);
//			}
//		if ( playsong != null){
//				playsong.setText(splaysong);
//			
//			}
//	}
//	public void setrandomrate()
//	{
//		Random random = new Random();
//		int i =  random.nextInt(25);
//		rate = (float) (0.5 + i*0.05);
//		srate = TextAdapter.arrayrate[i];
//		if (soundPool != null)
//		soundPool.setRate(streamid, rate);
//		setbuttonrate(srate);
//		
//	}
//	public void setbuttonrate(String input)
//	{
//		bt_tempo.setText(input);
//	}
//	private void initControlsvolume()
//    {
//        try
//        {
//        	final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//            volumeprogress.setMax(audioManager
//                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//            volumeprogress.setProgress(audioManager
//                    .getStreamVolume(AudioManager.STREAM_MUSIC));   
//
//
//            volumeprogress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
//            {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) 
//                {
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) 
//                {
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
//                {
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                            progress, 0);
//                }
//            });
//        }
//        catch (Exception e) 
//        {
//            e.printStackTrace();
//        }
//    }
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
////		MainActivity.setNameHomeSpec("Favorites");
//		DatabaseHandler myDbHelper = new DatabaseHandler(this);
//		  
//
//		 try {
//			 
//			 myDbHelper.openDataBase();
//			 List<Song> songs = myDbHelper.getFavariteSong();
//			 name = new String[songs.size()];
//			 id = new String[songs.size()];
//			 folder = new String[songs.size()];
//			 for(int i=0; i<songs.size(); i++)
//			 {
//				 name[i] = songs.get(i).getName();
//				 id[i] = songs.get(i).getID();
//				 folder[i] = songs.get(i).getFolder();
//			 }
//			  
//			 }catch(SQLException sqle){
//			  
//			 throw sqle;
//			  
//			 }
//		 myDbHelper.close();
//		array_name = new ArrayList<HashMap<String, String>>();
//		
//		for (int i=0; i<name.length; i++){
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put(KEY_NAME,name[i]);
//			map.put(KEY_ID,id[i]);
//			map.put(KEY_FOLDER,folder[i]);
//			array_name.add(map);
//		}
//		
//		
//
//		// Getting adapter
//		adapter = new FavoritesRowAdapter(this, array_name);
//		lv.setAdapter(adapter);
//		setIconPlayButton(SoundpoolState.getState(),SoundpoolState.getplaysong());
//		super.onResume();
//	}
//
//	
////	private void openQuitDialog(){
////	  	  AlertDialog.Builder quitDialog 
////	  	   = new AlertDialog.Builder(FavoritesActivity.this);
////	  	  quitDialog.setTitle("Confirm to Quit?");
////	  	  
////	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
////				
////				@Override
////				public void onClick(DialogInterface dialog, int which) {
////					ListviewLevel2.stopMusic();
////					FavoritesActivity.stopMusic();
////					finish();
////					
////				}
////	  	  });   	  
////	  	  quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
////
////	  		  public void onClick(DialogInterface dialog, int which) {
////	  	    // TODO Auto-generated method stub
////	  	    
////	  	   }});
////	  	  
////	  	  quitDialog.show();
////	  	 }
////
////	@Override
////	  public void onBackPressed() {
////		openQuitDialog(); 
////	  }
//	
//	private void openRemoveDialog(final int position){
//	  	  AlertDialog.Builder RemoveDialog 
//	  	   = new AlertDialog.Builder(FavoritesActivity.this);
//	  	RemoveDialog.setTitle("Do you remove this song from favorite list?");
//	  	  
//	  	RemoveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					array_name.remove(position);
//					removefavorite(position);
//					adapter.notifyDataSetChanged();
//					
//					
//				}
//	  	  });   	  
//	  	RemoveDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//	  		  public void onClick(DialogInterface dialog, int which) {
//	  	    // TODO Auto-generated method stub
//	  	    
//	  	   }});
//	  	  
//	  	RemoveDialog.show();
//	  	 }
//	public void removefavorite(int position) {
//        String sid = id[position];
//		DatabaseHandler myDbHelper = new DatabaseHandler(this);
//		myDbHelper.openDataBase();
//		//myDbHelper.removefavorite(sid);
//		myDbHelper.close();
//	}
//    
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//		  if (requestCode == REQUEST_TEMPO) {
//
//		     if(resultCode == RESULT_OK){      
//		         srate = data.getStringExtra("result");   
//		         Toast.makeText(this, srate, Toast.LENGTH_LONG).show();
//		         for (int i=0; i<TextAdapter.arrayrate.length; i++)
//		     	{
//		     		if (TextAdapter.arrayrate[i].equalsIgnoreCase(srate))
//		     		{
//		     			rate = (float) (0.5 + i*0.05);
//		     			if (soundPool != null)
//		     			soundPool.setRate(streamid, rate);
//		     			setbuttonrate(srate);
//		 				return;
//		     		}
//		     	}
//		     }
//		     if (resultCode == RESULT_CANCELED) {    
//		         //Write your code on no result return 
//		     }
//		  }
//		}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_timeline, menu);
//		return true;
//	}

}
