package namnt.drumbeat;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import namnt.drumbeat.facebook.FBLikeActivity;

import namnt.drumbeat.MainActivity;
import namnt.drumbeat.R;
import namnt.drumbeat.facebook.Constants.ParseConstants;
import namnt.drumbeat.facebook.BaseRequestListener;
import namnt.drumbeat.facebook.SessionEvents;
import namnt.drumbeat.facebook.SessionStore;

import namnt.drumbeat.facebook.Utility;

import namnt.drumbeat.facebook.Constants;

import namnt.drumbeat.facebook.FacebookAuthButton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoreDetail extends Activity {
	 Drawable[] logo = new Drawable [8];
	 Drawable[] img = new Drawable [8];
	    String[] header = new String [8];
	    String[] filesize = new String[8];
	    String[] content = new String [8];
	    String[] price = new String [8];
	    String[] filedemo = new String [8];
	    String[] folder = new String [8];
	    String[] state = new String [8];
	    ImageButton bt_back;
	    Button bt_play;
	    
	    int position;
	    
	    ImageView logo_view;
	    ImageView img_view;
	    ImageButton btn_download;
	    TextView header_view;
	    TextView filesize_view;
	    TextView content_view;
	    TextView price_view;
	    
	    boolean bplay = false;
	    
	    //facebook
	    private FacebookAuthButton mAuthBtn;
		private Button mLikeBtn;
		
		/* Objects */
		private Handler mHandler;
		private ArrayList<String> mLikeIds;
		private boolean mFacebookLiked = false;

		/* Primitives */
		/**
		 * true : if user clicked 'like' button previously, we don't check if the user still likes<br>
		 * false : if user 'unliked' FB_FOLLOW_ID after 'liking', we do check if the user still like FB_FOLLOW_ID.<br>
		 * 
		 * @see Constants.Common#FB_FOLLOW_ID
		 */
		private boolean mDontBother = false;

		/* Constants */
	    final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
	    final static int LIKE_ACTIVITY_RESULT_CODE = 1;
	    
	    
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
        
        folder[0] = getResources().getString(R.string.string_more_text_folder_name0);
	    folder[1] = getResources().getString(R.string.string_more_text_folder_name1);
	    folder[2] = getResources().getString(R.string.string_more_text_folder_name2);
	    folder[3] = getResources().getString(R.string.string_more_text_folder_name3);
	    folder[4] = getResources().getString(R.string.string_more_text_folder_name4);
        folder[5] = getResources().getString(R.string.string_more_text_folder_name5);
        folder[6] = getResources().getString(R.string.string_more_text_folder_name6);
        folder[7] = getResources().getString(R.string.string_more_text_folder_name7);
        
        
        state[0] = MainActivity.preferences.getString(folder[0], getResources().getString(R.string.string_more_text_folder_name_status0));
        state[1] = MainActivity.preferences.getString(folder[1], getResources().getString(R.string.string_more_text_folder_name_status1));
        state[2] = MainActivity.preferences.getString(folder[2], getResources().getString(R.string.string_more_text_folder_name_status2));
        state[3] = MainActivity.preferences.getString(folder[3], getResources().getString(R.string.string_more_text_folder_name_status3));
        state[4] = MainActivity.preferences.getString(folder[4], getResources().getString(R.string.string_more_text_folder_name_status4));
        state[5] = MainActivity.preferences.getString(folder[5], getResources().getString(R.string.string_more_text_folder_name_status5));
        state[6] = MainActivity.preferences.getString(folder[6], getResources().getString(R.string.string_more_text_folder_name_status6));
        state[7] = MainActivity.preferences.getString(folder[7], getResources().getString(R.string.string_more_text_folder_name_status7));
       
        
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
        
        
        btn_download = (ImageButton) findViewById(R.id.more_detail_header_bt_download);
        btn_download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(price[position].equalsIgnoreCase("free"))
				{
					showPopup(MoreDetail.this);
					return;
				}
				
				if (state[position].equalsIgnoreCase("1"))
				Toast.makeText(MoreDetail.this, "Alreadly download", Toast.LENGTH_LONG).show();
				else
					confirmDownloadDialog();
				
			}
		});
        
        
        //facebook popup
        
        mHandler = new Handler();
        mLikeIds = new ArrayList<String>();
        
        
        if (mDontBother){
	        SharedPreferences sharedPref = getSharedPreferences(Constants.SharedPreference.NAME_COMMON, MODE_PRIVATE);
	        mFacebookLiked = sharedPref.getBoolean(Constants.SharedPreference.NAME.FB_LIKED, false);
        }
	}
	
	private void confirmDownloadDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				MoreDetail.this);
		quitDialog.setTitle("Do you want to download it?");

		quitDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.preferences.edit().putString(folder[position], "1").commit();
						state[position] = MainActivity.preferences.getString(folder[position], getResources().getString(R.string.string_more_text_folder_name_status0));
						Log.d("test more detail", "folder " + folder[position] + " state " + state[position]);
						

					}
				});
		quitDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		quitDialog.show();
	}
        
    public void playMusic(){
    	bplay = true;
    	DrumbeatsMediaPlayer.bplay = false;
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
		DrumbeatsMediaPlayer.bplay = false;
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
		stopMusic();
		super.onBackPressed();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		stopMusic();
		super.onBackPressed();
		return;
	}
	
	//============================================
	//**** popup facebook like***//
	
	
	// The method that displays the popup.
	private void showPopup(final Activity context) 
	{   
	   int popupWidth = getResources().getDimensionPixelSize(R.dimen.facebook_popup_width);;
	   int popupHeight = getResources().getDimensionPixelSize(R.dimen.facebook_popup_height);;
	 
	   // Inflate the popup_layout.xml
	   RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.facbook_popup_for_free);
	   LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   View layout = layoutInflater.inflate(R.layout.facebook_popup, viewGroup);
	 
	   // Creating the PopupWindow
	   final PopupWindow popup = new PopupWindow(context);
	   popup.setContentView(layout);
	   popup.setWidth(popupWidth);
	   popup.setHeight(popupHeight);
	   popup.setFocusable(true);
	 
	   // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
	   int OFFSET_X = 0;
	   int OFFSET_Y = 30;
	 
	   // Clear the default translucent background
	   popup.setBackgroundDrawable(new BitmapDrawable());
	 
	   // Displaying the popup at the specified location, + offsets.
	   popup.showAtLocation(layout, Gravity.CENTER, OFFSET_X, OFFSET_Y);
	 
	   // Getting a reference to Close button, and close the popup when clicked.
	   Button close = (Button) layout.findViewById(R.id.close);
	   close.setOnClickListener(new OnClickListener() {
	 
	     @Override
	     public void onClick(View v) {
	    	 popup.dismiss();
	     }
	   });
	   
	   //facebook update
	   mAuthBtn = (FacebookAuthButton)layout.findViewById(R.id.btn_auth);
       mLikeBtn = (Button)layout.findViewById(R.id.btn_like);
	   showFacebookStatus();
       initFacebook();
       
       mLikeBtn.setOnClickListener(new View.OnClickListener() {
       	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
       		/* Opens activity with webview to connect to the facebook page to follow. */
       		Intent intent = new Intent(MoreDetail.this, FBLikeActivity.class);
           	intent.putExtra(Constants.Common.INTENT_FB_LIKE, mFacebookLiked);
       		startActivityForResult(intent, LIKE_ACTIVITY_RESULT_CODE);
			}
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        /*
         * if this is the activity result from authorization flow, do a call
         * back to authorizeCallback Source Tag: login_tag
         */
            case AUTHORIZE_ACTIVITY_RESULT_CODE: {
                Utility.mFacebook.authorizeCallback(requestCode, resultCode, data);
                break;
            }
            case LIKE_ACTIVITY_RESULT_CODE:{
            	if (resultCode == RESULT_OK){
            		boolean liked = data.getExtras().getBoolean(Constants.Common.INTENT_FB_LIKE);
            		if (mFacebookLiked == liked)
            			break;
            		mFacebookLiked = liked;
            		if (liked)
            			mLikeIds.add(Constants.Common.FB_FOLLOW_ID);
            		else{
	            		for (String id : mLikeIds){
	            			if (id.equals(Constants.Common.FB_FOLLOW_ID)){
	                    		mLikeIds.remove(id);
	                    		break;
	            			}
	            		}
            		}
            		showFacebookStatus();
            	}
            	break;
            }
        }
    }
	
	//==================Facebook action
	////////////////////////////////////
	
	private void initFacebook(){
    	// Create the Facebook Object using the app id.
        Utility.mFacebook = new Facebook(Constants.Common.FB_APP_ID);
        // Instantiate the asynrunner object for asynchronous api calls.
        Utility.mAsyncRunner = new AsyncFacebookRunner(Utility.mFacebook);

        // restore session if one exists
        SessionStore.restore(Utility.mFacebook, this);
        
        /* This listener will be called when succeed to log in. */
        SessionEvents.addAuthListener(new SessionEvents.AuthListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onAuthSucceed() {
				// TODO Auto-generated method stub

				/* Log in succeed. specify parameters you need to get and request to async task */
				Bundle params = new Bundle();
		        params.putString("fields", "name, picture, likes");
		        
		        Utility.mAsyncRunner.request("me", params, new BaseRequestListener() {
					
					@Override
					public void onComplete(String response, Object state) {
						// TODO Auto-generated method stub

			            JSONObject jsonObject;
			            try {
			                jsonObject = new JSONObject(response);

			                /* Parse data you've requested. */
//			                final String picURL = jsonObject.getJSONObject("picture")
//			                        .getJSONObject("data").getString("url");
			                Utility.userName = jsonObject.getString(ParseConstants.Facebook.User.NAME);
			                Utility.userUID = jsonObject.getString(ParseConstants.Facebook.User.ID);
			                Log.i("fb session", Utility.userName);

							if (mFacebookLiked && mDontBother)
								return;
			                
			                /* check if user previously liked the YOOII STUDIOS */
			                JSONArray likesList = jsonObject.getJSONObject(ParseConstants.Facebook.Likes.LIKES).getJSONArray(ParseConstants.Facebook.Likes.DATA);
			                mLikeIds.clear();
			                for (int i = 0; i < likesList.length(); i++){
			                	JSONObject obj = likesList.getJSONObject(i);
			                	String likesId = obj.getString(ParseConstants.Facebook.Likes.ID);
		                		mLikeIds.add(likesId);
			                	
			                	if (likesId.equals(Constants.Common.FB_FOLLOW_ID)){
//			                		liked = true;
			                		mFacebookLiked = true;
//			                		break;
			                	}
			                }
			                mHandler.post(new Runnable() {
								
								@Override
								public void run() {
							    	//여기에다가 좋아요 했으면 버튼 못누르게 설정.
			                		Utility.storeFacebookLiked(MoreDetail.this, mFacebookLiked);
									
						        	showFacebookStatus();
								}
							});
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
					}
				});
			}
			
			@Override
			public void onAuthFail(String error) {
				// TODO Auto-generated method stub
                Log.i("fb session", error);
			}
		});
        SessionEvents.addLogoutListener(new SessionEvents.LogoutListener() {
			
			@Override
			public void onLogoutFinish() {
				// TODO Auto-generated method stub
				showFacebookStatus();
			}
			
			@Override
			public void onLogoutBegin() {
				// TODO Auto-generated method stub
				
			}
		});

    	mAuthBtn.init(this, AUTHORIZE_ACTIVITY_RESULT_CODE, Utility.mFacebook, Constants.Common.permissions);
    }
	
    private void showFacebookStatus(){
    	final boolean loggedIn = (Utility.mFacebook != null) ? Utility.mFacebook.isSessionValid() : false;
    	final boolean liked = mFacebookLiked;

    	mAuthBtn.setVisibility(loggedIn ? View.INVISIBLE : View.VISIBLE);
		mLikeBtn.setVisibility(loggedIn ? View.VISIBLE : View.INVISIBLE);
		
    	mLikeBtn.setText(loggedIn ? (liked ? getString(R.string.unliked) : getString(R.string.liked)) : "");
    }
//    @Override
//    public void onResume(){
//    	super.onResume();
//    	showFacebookStatus();
//    }
//    
//    @Override
//    public void onDestroy(){
//    	super.onDestroy();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.more_detail, menu);
//        return true;
//    }

}
