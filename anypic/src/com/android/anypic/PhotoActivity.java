package com.android.anypic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import org.apache.commons.io.IOUtils;

import com.android.utils.GPSTracker;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoActivity extends Activity {
	Button bt_choose_photo;
	Button bt_take_photo;
	Button bt_publish;
	EditText comment;
	ImageView img_photo;
	GPSTracker gps;
	private ProgressDialog mProgressDialog;
	private static final int SELECT_PHOTO = 100;
	private static final int TAKE_PICTURE = 101;
	private static final int PHOTO_WIDTH = 480;
	private static final int THUMBNAIL_WIDTH = 72;
	
	Uri imageUri;
	byte[] bitmapdata;
	byte[] thumbnaildata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		bt_choose_photo = (Button) findViewById(R.id.bt_choose_a_photo);
		bt_take_photo = (Button) findViewById(R.id.bt_take_a_photo);
		bt_publish = (Button) findViewById(R.id.activity_photo_bt_publish);
		comment = (EditText) findViewById(R.id.activity_photo_text_box_comment);
		
		img_photo = (ImageView) findViewById(R.id.activity_photo_picture);

		bt_choose_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);

			}
		});
		
		bt_take_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			    File photoFile = new File(Environment.getExternalStorageDirectory(),  "Photo.png");
			    intent.putExtra(MediaStore.EXTRA_OUTPUT,
			            Uri.fromFile(photoFile));
			    imageUri = Uri.fromFile(photoFile);
			    startActivityForResult(intent, TAKE_PICTURE);
			}
		});
		
		bt_publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mProgressDialog = ProgressDialog.show(PhotoActivity.this, "", getString(R.string.loading), true);
//				InputStream imageStream = null;
//				byte[] data = null;
//				try {
//					imageStream = getContentResolver().openInputStream(
//							imageUri);
//					try {
//						data = IOUtils.toByteArray(imageStream);
//						
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				 gps = new GPSTracker(PhotoActivity.this);

					// check if GPS enabled		
			        if(gps.canGetLocation()){
			        	
			        	double latitude = gps.getLatitude();
			        	double longitude = gps.getLongitude();
			        	final ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
			        	final ParseFile file = new ParseFile("photo.jpg", bitmapdata);
						file.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								// TODO Auto-generated method stub
								final ParseFile filethumbnail = new ParseFile("photothumnail.jpg", thumbnaildata);
								try {
									filethumbnail.save();
									final ParseObject photo = new ParseObject("photo");
									photo.put("image", file);
									photo.put("thumbnail", filethumbnail);
									photo.put("user", ParseUser.getCurrentUser());
									photo.put("location", point);
									photo.saveInBackground(new SaveCallback() {
										
										@Override
										public void done(ParseException e) {
											// TODO Auto-generated method stub
											String scomment = comment.getEditableText().toString();
											if (scomment == null){
												img_photo.setVisibility(View.GONE);
												comment.setVisibility(View.GONE);
												bt_publish.setVisibility(View.GONE);
											} else {
												post_comment(scomment, photo.getObjectId());
												img_photo.setVisibility(View.GONE);
												comment.setVisibility(View.GONE);
												bt_publish.setVisibility(View.GONE);
											}
											
										}
									});
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
//								photo.saveInBackground(new SaveCallback() {
//									
//									@Override
//									public void done(ParseException e) {
//										// TODO Auto-generated method stub
//										ParseUser user = ParseUser.getCurrentUser();
//										ParseRelation relation = user.getRelation("photo");
//										relation.add(photo);
//										user.saveInBackground();
//										
//									}
//								});
								
							}
						});
			        	// \n is for new line
			        	//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
			        }else{
			        	// can't get location
			        	// GPS or Network is not enabled
			        	// Ask user to enable GPS/network in settings
			        	gps.showSettingsAlert();
			        }
				
				
				
				
			}
		});

	}
	public void post_comment(String content, String photo_id){
		ParseObject activity = new ParseObject("activity");
		ParseQuery query_photo = new ParseQuery("photo");
		query_photo.whereEqualTo("objectId", photo_id);
		try {
			ParseObject photo = query_photo.getFirst();
			ParseObject user = (ParseObject)photo.get("user");
			//String toUser = user.getObjectId();
			activity.put("fromUser", ParseUser.getCurrentUser());
			activity.put("toUser", user);
			activity.put("photo", photo);
			activity.put("content", content);
			activity.saveInBackground();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private byte[] Convert(Bitmap bmp) {
		int bytes = bmp.getWidth() * bmp.getHeight() * 4;
		// Log.e(“Bytes”, String.valueOf(bytes));
		ByteBuffer buffer = ByteBuffer.allocate(bytes);
		bmp.copyPixelsToBuffer(buffer);
		byte[] array = buffer.array();
		if (array != null) {
			return array;
		}
		return null;
	}
	public Bitmap ScaleBitmap(Bitmap bmp, int real_with){
		Float width  = new Float(bmp.getWidth());
		Float height = new Float(bmp.getHeight());
		Float ratio = height/width;
		bmp = Bitmap.createScaledBitmap(bmp, real_with,  (int)(real_with*ratio), false);
		return bmp;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Log.d("test", "lay anh xong");
				imageUri = imageReturnedIntent.getData();
				InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(
							imageUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				Bitmap photobitmap = ScaleBitmap(yourSelectedImage, PHOTO_WIDTH);
				ByteArrayOutputStream bos_photo = new ByteArrayOutputStream(); 
				photobitmap.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos_photo); 
				bitmapdata = bos_photo.toByteArray();
				Bitmap thumbnailbitmap = ScaleBitmap(yourSelectedImage, THUMBNAIL_WIDTH);
				ByteArrayOutputStream bos_thumbnail = new ByteArrayOutputStream(); 
				thumbnailbitmap.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos_thumbnail); 
				thumbnaildata = bos_thumbnail.toByteArray();
				// Drawable d = new BitmapDrawable(yourSelectedImage);
				img_photo.setImageBitmap(photobitmap);
				img_photo.setVisibility(View.VISIBLE);
				comment.setVisibility(View.VISIBLE);
				bt_publish.setVisibility(View.VISIBLE);
			}
			break;
		case TAKE_PICTURE:
			if (resultCode == Activity.RESULT_OK) {
				Log.d("test", "chup anh xong");
				Uri selectedImageUri = imageUri;
				// Do what ever you want
				InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(
							selectedImageUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				Bitmap photobitmap = ScaleBitmap(yourSelectedImage, PHOTO_WIDTH);
				ByteArrayOutputStream bos_photo = new ByteArrayOutputStream(); 
				photobitmap.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos_photo); 
				bitmapdata = bos_photo.toByteArray();
				Bitmap thumbnailbitmap = ScaleBitmap(yourSelectedImage, THUMBNAIL_WIDTH);
				ByteArrayOutputStream bos_thumbnail = new ByteArrayOutputStream(); 
				thumbnailbitmap.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos_thumbnail); 
				thumbnaildata = bos_thumbnail.toByteArray();
				// Drawable d = new BitmapDrawable(yourSelectedImage);
				img_photo.setImageBitmap(photobitmap);
				img_photo.setVisibility(View.VISIBLE);
				comment.setVisibility(View.VISIBLE);
				bt_publish.setVisibility(View.VISIBLE);
			}
			break;
		}
	}
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		openOptionsMenu();
//	}
	private void openQuitDialog(){
	  	  AlertDialog.Builder quitDialog 
	  	   = new AlertDialog.Builder(PhotoActivity.this);
	  	  quitDialog.setTitle("Confirm to Quit?");
	  	  
	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					
				}
	  	  });   	  
	  	  quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	  		  public void onClick(DialogInterface dialog, int which) {
	  	    // TODO Auto-generated method stub
	  	    
	  	   }});
	  	  
	  	  quitDialog.show();
	  	 }

	@Override
	  public void onBackPressed() {
		openQuitDialog(); 
	  }
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_photo, menu);
//		return true;
//	}

}
