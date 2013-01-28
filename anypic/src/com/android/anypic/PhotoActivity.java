package com.android.anypic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.commons.io.IOUtils;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoActivity extends Activity {
	Button bt_choose_photo;
	Button bt_take_photo;
	Button bt_upload;
	ImageView photo;
	private ProgressDialog mProgressDialog;
	private static final int SELECT_PHOTO = 100;
	private static final int TAKE_PICTURE = 101;
	Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		bt_choose_photo = (Button) findViewById(R.id.bt_choose_a_photo);
		bt_take_photo = (Button) findViewById(R.id.bt_take_a_photo);
		bt_upload = (Button) findViewById(R.id.bt_upload_photo);
		bt_upload.setEnabled(false);
		
		photo = (ImageView) findViewById(R.id.activity_photo_picture);

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
		
		bt_upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mProgressDialog = ProgressDialog.show(PhotoActivity.this, "", getString(R.string.loading), true);
				InputStream imageStream = null;
				byte[] data = null;
				try {
					imageStream = getContentResolver().openInputStream(
							imageUri);
					try {
						data = IOUtils.toByteArray(imageStream);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final ParseFile file = new ParseFile("photo.jpg", data);
				file.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						Log.d("test", "upload file");
						final ParseObject photo = new ParseObject("photo");
						photo.put("image", file);
						photo.put("user", ParseUser.getCurrentUser());
						photo.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								// TODO Auto-generated method stub
								if (mProgressDialog.isShowing()) {
									mProgressDialog.dismiss();
						      }
								
							}
						});
//						photo.saveInBackground(new SaveCallback() {
//							
//							@Override
//							public void done(ParseException e) {
//								// TODO Auto-generated method stub
//								ParseUser user = ParseUser.getCurrentUser();
//								ParseRelation relation = user.getRelation("photo");
//								relation.add(photo);
//								user.saveInBackground();
//								
//							}
//						});
						
					}
				});
				
				
			}
		});

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
				// Drawable d = new BitmapDrawable(yourSelectedImage);
				photo.setImageBitmap(yourSelectedImage);
				bt_upload.setEnabled(true);
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
				// Drawable d = new BitmapDrawable(yourSelectedImage);
				photo.setImageBitmap(yourSelectedImage);
				bt_upload.setEnabled(true);
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_photo, menu);
		return true;
	}

}
