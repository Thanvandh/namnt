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
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListviewLevel2 extends Activity {
	

	String xml;
	ListView lv;
	ListviewLevel2RowAdapter adapter;
	Button bt_tempo;
	Button bt_random;
	static Button bt_play;
	SeekBar volumeprogress;
	ArrayList<HashMap<String, String>> array_name;
	String[] values = new String[] {"Started Pack", "Break Beats", "Hit Songs 1", "Pop-rock 1" };
	String[] name;
	String[] id;
	String[] values2 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values3 = new String[] {"Audio1", "Audio2", "Audio3"};
	String[] values4 = new String[] {"Audio1", "Audio2", "Audio3"};
	
	static String KEY_NAME = "name";
	static String KEY_ID = "id";
	String folder;
	static boolean bplay = false;


	// Flag for current page
	int current_page = 1;

	float volume;
	int streamid = 1;
	private static SoundPool soundPool;

	private int soundID;
	String srate;
	int REQUEST_TEMPO = 2;
	float rate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview2);
		Intent in = getIntent();
		Bundle bundle = in.getExtras();
		folder = bundle.getString(ListviewLevel1.KEY_FOLDER);
		DatabaseHandler myDbHelper = new DatabaseHandler(this);
		  

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
		lv = (ListView) findViewById(R.id.listview2_activity);
		bt_tempo = (Button) findViewById(R.id.bt_tempo);
		bt_random = (Button) findViewById(R.id.bt_random);
		bt_play = (Button) findViewById(R.id.bt_play);
		volumeprogress = (SeekBar) findViewById(R.id.volumeProgressBar);
		
		initControlsvolume();
		setIconPlayButton(bplay);
		
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
		//int picId = getResources().getIdentifier("ogg_country_straightclean", "raw", getApplicationContext().getPackageName());
		bt_tempo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getParent(),TempoActivity.class);
				i.putExtra("rate", srate);
				startActivityForResult(i, REQUEST_TEMPO);
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
				if (bplay)
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
	}
	
	public void playMusic(int postion)
	{
		bplay = true;
		setIconPlayButton(bplay);
		FavoritesActivity.stopMusic();
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
						bplay = true;
						setIconPlayButton(bplay);
						streamid = soundPool.play(soundID, volume,
								volume, 1, -1, 1);
//						Toast.makeText(ListviewLevel2.this,
//								"Loop forever " + streamid,
//								Toast.LENGTH_LONG).show();


					}
				});
		int songId = getResources().getIdentifier("song" + id[postion], "raw", getApplicationContext().getPackageName());
		soundID = soundPool.load(ListviewLevel2.this,
				songId, 1);
	}
	
	public static void stopMusic()
	{
		bplay = false;
		setIconPlayButton(bplay);
		if (soundPool != null)
			soundPool.release();
	}
	
	public static void setIconPlayButton(boolean play)
	{
		if (bt_play != null){
		if (play)
			bt_play.setBackgroundResource(R.drawable.stop_button);
		else
			bt_play.setBackgroundResource(R.drawable.play_button);
		}
	}
	public void setrandomrate()
	{
		Random random = new Random();
		int i =  random.nextInt(25);
		rate = (float) (0.5 + i*0.05);
		srate = TextAdapter.arrayrate[i];
		if (soundPool != null)
		soundPool.setRate(streamid, rate);
		setbuttonrate(srate);
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
