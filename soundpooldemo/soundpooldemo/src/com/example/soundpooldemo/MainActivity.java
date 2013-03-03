package com.example.soundpooldemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	private SoundPool soundPool;

	private int soundID;

	boolean loaded = false;
	Button play1;
	Button play2;
	Button play3;
	Button play4;
	Button play5;
	Button play6;
	Button play7;
	Button play8;
	Button play9;
	Button play10;
	Button play11;
	Button play12;
	
	float volume;
	int streamid = 1;
	MediaPlayer mp;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View view = findViewById(R.id.textView1);
		// view.setOnTouchListener(this);
		// Set the hardware buttons to control the music
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// Load the sound

		// soundPool2 = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		// soundPool2.setOnLoadCompleteListener(new OnLoadCompleteListener() {
		//
		// @Override
		// public void onLoadComplete(SoundPool soundPool, int sampleId,
		// int status) {
		// loaded2 = true;
		// soundPool2.play(soundID2, volume, volume, 0, -1, 1f);
		// Toast.makeText(MainActivity.this, "Loop forever",
		// Toast.LENGTH_LONG).show();
		//
		// Log.e("Test", "Played sound 2");
		//
		// }
		// });
		// soundPool3 = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		// soundPool3.setOnLoadCompleteListener(new OnLoadCompleteListener() {
		//
		// @Override
		// public void onLoadComplete(SoundPool soundPool, int sampleId,
		// int status) {
		// loaded3 = true;
		// soundPool3.play(soundID3, volume, volume, 0, -1, 1f);
		// Toast.makeText(MainActivity.this, "Loop forever",
		// Toast.LENGTH_LONG).show();
		//
		// Log.e("Test", "Played sound 3");
		//
		// }
		// });
		play1 = (Button) findViewById(R.id.bt_play1);
		play2 = (Button) findViewById(R.id.bt_play2);
		play3 = (Button) findViewById(R.id.bt_play3);
		play4 = (Button) findViewById(R.id.bt_play4);
		play5 = (Button) findViewById(R.id.bt_play5);
		play6 = (Button) findViewById(R.id.bt_play6);
		
		play7 = (Button) findViewById(R.id.bt_play7);
		play8 = (Button) findViewById(R.id.bt_play8);
		play9 = (Button) findViewById(R.id.bt_play9);
		play10 = (Button) findViewById(R.id.bt_play10);
		play11 = (Button) findViewById(R.id.bt_play11);
		play12 = (Button) findViewById(R.id.bt_play12);

		// soundID2 = soundPool.load(MainActivity.this,
		// R.raw.country_straightclean, 1);
		// soundID3 = soundPool.load(MainActivity.this,
		// R.raw.pop_rock_dirtygroove, 1);

		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = actualVolume / maxVolume;

		play1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
				if (soundPool != null)
					soundPool.release();

				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
				soundPool
						.setOnLoadCompleteListener(new OnLoadCompleteListener() {

							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								loaded = true;
								//
								Log.d("Test", "onLoadComplete " + sampleId
										+ status);
								streamid = soundPool.play(soundID, volume,
										volume, 1, -1, 0.5f);
								Toast.makeText(MainActivity.this,
										"Loop forever " + streamid,
										Toast.LENGTH_LONG).show();

								Log.e("Test", "Played sound " + streamid);

							}
						});
				soundID = soundPool.load(MainActivity.this,
						R.raw.ogg_country_straightclean, 1);
//				mp = MediaPlayer.create(MainActivity.this, R.raw.starterpack60);
//				//mp.reset();
//				//mp.se
//				mp.setLooping(true);
//				mp.start();

			}
		});
		play2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (soundPool != null)
					soundPool.release();

				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
				soundPool
						.setOnLoadCompleteListener(new OnLoadCompleteListener() {

							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								loaded = true;
								//
								Log.d("Test", "onLoadComplete " + sampleId
										+ status);
								streamid = soundPool.play(soundID, volume,
										volume, 1, -1, 1f);
								Toast.makeText(MainActivity.this,
										"Loop forever " + streamid,
										Toast.LENGTH_LONG).show();

								Log.e("Test", "Played sound " + streamid);

							}
						});
				soundID = soundPool.load(MainActivity.this,
						R.raw.starterpack190, 1);

			}
		});
		play3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//mp.stop();
				if (soundPool != null)
					soundPool.release();

				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
				soundPool
						.setOnLoadCompleteListener(new OnLoadCompleteListener() {

							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								loaded = true;
								//
								Log.d("Test", "onLoadComplete " + sampleId
										+ status);
								streamid = soundPool.play(soundID, volume,
										volume, 1, -1, 1f);
								Toast.makeText(MainActivity.this,
										"Loop forever " + streamid,
										Toast.LENGTH_LONG).show();

								Log.e("Test", "Played sound " + streamid);

							}
						});
				soundID = soundPool.load(MainActivity.this,
						R.raw.ogg_pop_rock_drirtygroove, 1);

			}
		});
		
		play4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//mp.stop();
//				Toast.makeText(MainActivity.this,
//						"button 4 ",
//						Toast.LENGTH_LONG).show();
				if (soundPool != null)
					soundPool.release();

				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
				soundPool
						.setOnLoadCompleteListener(new OnLoadCompleteListener() {

							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								loaded = true;
								//
								Log.d("Test", "onLoadComplete " + sampleId
										+ status);
								streamid = soundPool.play(soundID, volume,
										volume, 1, -1, 1f);
								Toast.makeText(MainActivity.this,
										"Loop forever " + streamid,
										Toast.LENGTH_LONG).show();

								Log.e("Test", "Played sound " + streamid);

							}
						});
				soundID = soundPool.load(MainActivity.this,
						R.raw.starterpack190, 1);

			}
		});
		
		play5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//mp.stop();
				if (soundPool != null)
					soundPool.release();

				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
				soundPool
						.setOnLoadCompleteListener(new OnLoadCompleteListener() {

							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								loaded = true;
								//
								Log.d("Test", "onLoadComplete " + sampleId
										+ status);
								streamid = soundPool.play(soundID, volume,
										volume, 1, -1, 1f);
								Toast.makeText(MainActivity.this,
										"Loop forever " + streamid,
										Toast.LENGTH_LONG).show();

								Log.e("Test", "Played sound " + streamid);

							}
						});
				soundID = soundPool.load(MainActivity.this,
						R.raw.starterpack60, 1);

			}
		});
		
		play6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//mp.stop();
				if (soundPool != null)
					soundPool.release();

//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 1f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.starterpack60, 1);

			}
		});
		
		play7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 0.5f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.ogg_country_straightclean, 1);
				if (mp != null){
				if (mp.isPlaying()){
					mp.stop();
					mp.release();
					
				}
				}
				mp = MediaPlayer.create(MainActivity.this, R.raw.ogg_country_straightclean);
//				if (mp.isPlaying())
//				mp.stop();
				//mp.se
				mp.setLooping(true);
				mp.start();

			}
		});
		
		play8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 0.5f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.ogg_country_straightclean, 1);
				if (mp != null){
					if (mp.isPlaying()){
						mp.stop();
						mp.release();
						
					}
					}
				mp = MediaPlayer.create(MainActivity.this, R.raw.ogg_heavymetal_headbang);
//				if (mp.isPlaying())
//					mp.stop();
				//mp.se
				mp.setLooping(true);
				mp.start();

			}
		});
		
		play9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 0.5f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.ogg_country_straightclean, 1);
				if (mp != null){
					if (mp.isPlaying()){
						mp.stop();
						mp.release();
						
					}
					}
				mp = MediaPlayer.create(MainActivity.this, R.raw.ogg_pop_rock_drirtygroove);
//				if (mp.isPlaying())
//					mp.stop();
//				//mp.se
				mp.setLooping(true);
				mp.start();

			}
		});
		
		play10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 0.5f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.ogg_country_straightclean, 1);
				if (mp != null){
					if (mp.isPlaying()){
						mp.stop();
						mp.release();
						
					}
					}
				mp = MediaPlayer.create(MainActivity.this, R.raw.starterpack190);
//				if (mp.isPlaying())
//					mp.stop();
				//mp.se
				mp.setLooping(true);
				mp.start();

			}
		});
		
		play11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 0.5f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.ogg_country_straightclean, 1);
				if (mp != null){
					if (mp.isPlaying()){
						mp.stop();
						mp.release();
						
					}
					}
					
				mp = MediaPlayer.create(MainActivity.this, R.raw.starterpack60);
				
				//mp.se
				mp.setLooping(true);
				mp.start();

			}
		});
		
		play12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// soundPool.stop(streamid2);
				// soundPool.stop(streamid3);
				// soundPool.unload(soundID);
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 0.5f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this,
//						R.raw.ogg_country_straightclean, 1);
				if (mp != null){
					if (mp.isPlaying()){
						mp.stop();
						mp.release();
						
					}
					}
				mp = MediaPlayer.create(MainActivity.this, R.raw.starterpack60);
				//mp.se
				//mp.setLooping(true);
				//mp.start();

			}
		});
//		play4.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 1f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this, R.raw.ogg_country_straightclean, 1);
//
//			}
//		});
//		play5.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 1f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this, R.raw.ogg_heavymetal_headbang, 1);
//
//			}
//		});
//		play6.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (soundPool != null)
//					soundPool.release();
//
//				soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//				soundPool
//						.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//
//							@Override
//							public void onLoadComplete(SoundPool soundPool,
//									int sampleId, int status) {
//								loaded = true;
//								//
//								Log.d("Test", "onLoadComplete " + sampleId
//										+ status);
//								streamid = soundPool.play(soundID, volume,
//										volume, 1, -1, 1f);
//								Toast.makeText(MainActivity.this,
//										"Loop forever " + streamid,
//										Toast.LENGTH_LONG).show();
//
//								Log.e("Test", "Played sound " + streamid);
//
//							}
//						});
//				soundID = soundPool.load(MainActivity.this, R.raw.ogg_pop_rock_rirtygroove, 1);
//
//			}
//		});

		// soundID = soundPool.load(this, R.raw.sound1, 1);

	}

	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// if (event.getAction() == MotionEvent.ACTION_DOWN) {
	// // Getting the user sound settings
	// AudioManager audioManager = (AudioManager)
	// getSystemService(AUDIO_SERVICE);
	// float actualVolume = (float) audioManager
	// .getStreamVolume(AudioManager.STREAM_MUSIC);
	// float maxVolume = (float) audioManager
	// .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	// float volume = actualVolume / maxVolume;
	// // Is the sound loaded already?
	// if (loaded) {
	// //soundPool.setLoop(soundID, 100);
	// soundPool.play(soundID, volume, volume, 0, -1, 1f);
	// Toast.makeText(MainActivity.this, "Loop forever",
	// Toast.LENGTH_LONG).show();
	//
	// Log.e("Test", "Played sound");
	// }
	// }
	// return false;
	// }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
