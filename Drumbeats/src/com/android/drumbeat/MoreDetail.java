package com.android.drumbeat;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreDetail extends Activity {
	 Drawable[] logo = new Drawable [8];
	 Drawable[] img = new Drawable [8];
	    String[] header = new String [8];
	    String[] filesize = new String[8];
	    String[] content = new String [8];
	    String[] price = new String [8];
	    String[] filedemo = new String [8];
	    ImageButton bt_back;
	    Button bt_play;
	    
	    int position;
	    
	    ImageView logo_view;
	    ImageView img_view;
	    TextView header_view;
	    TextView filesize_view;
	    TextView content_view;
	    TextView price_view;
	    
	    boolean bplay = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_detail);
		Intent in = getIntent();
		Bundle bundle = in.getExtras();
		position = bundle.getInt("position");
		
		logo[0] = getResources().getDrawable(R.drawable.rnb_essentials1_icon);
        logo[1] = getResources().getDrawable(R.drawable.world_beats_icon);
        logo[2] = getResources().getDrawable(R.drawable.tambourine10pack_icon);
        logo[3] = getResources().getDrawable(R.drawable.latin_jazz_icon);
        logo[4] = getResources().getDrawable(R.drawable.heavy_metal1_icon);
        logo[5] = getResources().getDrawable(R.drawable.country1_icon);
        logo[6] = getResources().getDrawable(R.drawable.blues1_icon);
        logo[7] = getResources().getDrawable(R.drawable.artificial1_icon);
        
        
        img[0] = null;
        img[1] = getResources().getDrawable(R.drawable.world_beats_details);
        img[2] = getResources().getDrawable(R.drawable.tambourine10pack_details);
        img[3] = getResources().getDrawable(R.drawable.latin_jazz_details);
        img[4] = getResources().getDrawable(R.drawable.heavy_metal1_details);
        img[5] = getResources().getDrawable(R.drawable.country1_details);
        img[6] = getResources().getDrawable(R.drawable.blues1_details);
        img[7] = getResources().getDrawable(R.drawable.artificial1_details);
        
        header[0] = getResources().getString(R.string.string_more_text_header0);
        header[1] = getResources().getString(R.string.string_more_text_header1);
        header[2] = getResources().getString(R.string.string_more_text_header2);
        header[3] = getResources().getString(R.string.string_more_text_header3);
        header[4] = getResources().getString(R.string.string_more_text_header4);
        header[5] = getResources().getString(R.string.string_more_text_header5);
        header[6] = getResources().getString(R.string.string_more_text_header6);
        header[7] = getResources().getString(R.string.string_more_text_header7);
        
        filesize[0] = getResources().getString(R.string.string_more_text_file_size0);
        filesize[1] = getResources().getString(R.string.string_more_text_file_size1);
        filesize[2] = getResources().getString(R.string.string_more_text_file_size2);
        filesize[3] = getResources().getString(R.string.string_more_text_file_size3);
        filesize[4] = getResources().getString(R.string.string_more_text_file_size4);
        filesize[5] = getResources().getString(R.string.string_more_text_file_size5);
        filesize[6] = getResources().getString(R.string.string_more_text_file_size6);
        filesize[7] = getResources().getString(R.string.string_more_text_file_size7);
        
        content[0] = getResources().getString(R.string.string_more_text_content0);
        content[1] = getResources().getString(R.string.string_more_text_content1);
        content[2] = getResources().getString(R.string.string_more_text_content2);
        content[3] = getResources().getString(R.string.string_more_text_content3);
        content[4] = getResources().getString(R.string.string_more_text_content4);
        content[5] = getResources().getString(R.string.string_more_text_content5);
        content[6] = getResources().getString(R.string.string_more_text_content6);
        content[7] = getResources().getString(R.string.string_more_text_content7);
        
        price[0] = getResources().getString(R.string.string_more_text_price0);
        price[1] = getResources().getString(R.string.string_more_text_price1);
        price[2] = getResources().getString(R.string.string_more_text_price2);
        price[3] = getResources().getString(R.string.string_more_text_price3);
        price[4] = getResources().getString(R.string.string_more_text_price4);
        price[5] = getResources().getString(R.string.string_more_text_price5);
        price[6] = getResources().getString(R.string.string_more_text_price6);
        price[7] = getResources().getString(R.string.string_more_text_price7);
        
        filedemo[0] = getResources().getString(R.string.string_more_text_file_demo0);
        filedemo[1] = getResources().getString(R.string.string_more_text_file_demo1);
        filedemo[2] = getResources().getString(R.string.string_more_text_file_demo2);
        filedemo[3] = getResources().getString(R.string.string_more_text_file_demo3);
        filedemo[4] = getResources().getString(R.string.string_more_text_file_demo4);
        filedemo[5] = getResources().getString(R.string.string_more_text_file_demo5);
        filedemo[6] = getResources().getString(R.string.string_more_text_file_demo6);
        filedemo[7] = getResources().getString(R.string.string_more_text_file_demo7);
        
        bt_back = (ImageButton) findViewById(R.id.more_detail_header_bt_back);
        bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back();
			}
		});
        
        bt_play = (Button) findViewById(R.id.more_detail_bt_play);
        bt_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bplay)
					stopMusic();
				else 
					playMusic();
				
			}
		});
        
        logo_view = (ImageView) findViewById(R.id.more_detail_body_logo);
        img_view = (ImageView) findViewById(R.id.more_detail_body_mid_img);
        header_view = (TextView) findViewById(R.id.more_detail_body_text_header);
        filesize_view = (TextView) findViewById(R.id.more_detail_text_content);
        price_view = (TextView) findViewById(R.id.more_detail_text_price);
        content_view = (TextView) findViewById(R.id.more_detail_body_mid_content);
        
        logo_view.setImageDrawable(logo[position]);
        img_view.setImageDrawable(img[position]);
        header_view.setText(header[position]);
        filesize_view.setText(filesize[position]);
        price_view.setText(price[position]);
        content_view.setText(content[position]);
        
        
        
        
	}
        
    public void playMusic(){
    	bplay = true;
		setButtonPlay(bplay);
		playMusic(filedemo[position]);
    	
    }
	public void playMusic(String file) {
		if (DrumbeatsMediaPlayer.mp != null) {
			if (DrumbeatsMediaPlayer.mp.isPlaying()) {
				DrumbeatsMediaPlayer.mp.stop();
				// mp.release();

			}
		} else
			DrumbeatsMediaPlayer.mp = new MediaPlayer();

		AssetFileDescriptor fileplay;
		try {
			fileplay = getResources().getAssets().openFd(
					"demo/" + file);
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
		bplay = false;
		setButtonPlay(bplay);
		// playsong.setText("");
		if (DrumbeatsMediaPlayer.mp != null) {
			if (DrumbeatsMediaPlayer.mp.isPlaying()) {
				DrumbeatsMediaPlayer.mp.stop();
				// mp.release();

			}
		}

	}
	
	public void setButtonPlay(boolean b) {
		if (b) {
			bt_play.setBackgroundResource(R.drawable.stop_demo_button);
		} else {
			bt_play.setBackgroundResource(R.drawable.btn_play_demo);
		}

	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.more_detail, menu);
//		return true;
//		
//	}
	public void back(){
		super.onBackPressed();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		return;
	}

}
