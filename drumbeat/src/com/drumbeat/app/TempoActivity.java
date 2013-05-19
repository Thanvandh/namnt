package com.drumbeat.app;



import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TempoActivity extends Activity {
	int lastPos;
	Button bt_random;
	GridView gridView;
	String srate;
	Button bt_done;
	Button bt_play;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo);
        gridView = (GridView) findViewById(R.id.grid_view);
        bt_random = (Button) findViewById(R.id.bt_randomrate);
        bt_done = (Button) findViewById(R.id.bt_done);
        bt_play = (Button) findViewById(R.id.bt_playrate);
        Intent in = getIntent();
		Bundle bundle = in.getExtras();
		srate = bundle.getString("rate");
		bt_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				donerate();
			}
		});
		bt_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				donerate();
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
		
		// Instance of ImageAdapter Class
		//gridView.setAdapter(new ImageAdapter(this));
		//gridView.setAdapter(new TextAdapter(this));
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
				srate = TextAdapter.mTemporary[lastPos];
//				gridView.setSelection((int)(gridView.getAdapter()).getItemId(lastPos));
//				gridView.requestFocus();
				gridView.setSelection(lastPos);
				gridView.requestFocusFromTouch();
				gridView.setSelection(lastPos);
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
		setdefaultpostion();
    }
    public void setdefaultpostion()
    {
    	for (int i=0; i<TextAdapter.arrayrate.length; i++)
    	{
    		if (TextAdapter.arrayrate[i].equalsIgnoreCase(srate))
    		{
    			lastPos = i;
    			Log.d("testqfag", "thu tu la" + i);
				gridView.setSelection(lastPos);
				gridView.requestFocusFromTouch();
				gridView.setSelection(lastPos);
				return;
    		}
    	}
    }
	protected void setrandomrate() {
		// TODO Auto-generated method stub
		Random random = new Random();
		int rate = random.nextInt(26);
		lastPos = rate;
		srate = TextAdapter.mTemporary[lastPos];
		gridView.setSelection(lastPos);
		gridView.requestFocusFromTouch();
		gridView.setSelection(lastPos);
	}
	public void donerate()
	{
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result",srate);
		setResult(RESULT_OK,returnIntent);     
		finish();
	}
}