package com.drumbeat.app;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class ActivityStack extends ActivityGroup {

	  private Stack<String> stack;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    if (stack == null) stack = new Stack<String>();
	    //start default activity
	    push("FirstStackActivity", new Intent(this, ListviewLevel1.class));
	  }

	  @Override
	  public void finishFromChild(Activity child) {
	    pop();
	  }

	  @Override
	  public void onBackPressed() {
	    pop();
	  }


	  public void push(String id, Intent intent) {
	    Window window = getLocalActivityManager().startActivity(id, intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	    if (window != null) {
	      stack.push(id);
	      setContentView(window.getDecorView());
	    }
	  }

	  public void pop() {
	    if (stack.size() == 1 ) 
	    	{
	    		openQuitDialog();
	    		return;
	    	}
	    if (stack.size() == 2 ) 
    	{
    		ListviewLevel2.stopMusic();
    	}
	    LocalActivityManager manager = getLocalActivityManager();
	    manager.destroyActivity(stack.pop(), true);
	    if (stack.size() > 0) {
	      Intent lastIntent = manager.getActivity(stack.peek()).getIntent();
	      Window newWindow = manager.startActivity(stack.peek(), lastIntent);
	      setContentView(newWindow.getDecorView());
	    }
	  }
	  private void openQuitDialog(){
	  	  AlertDialog.Builder quitDialog 
	  	   = new AlertDialog.Builder(ActivityStack.this);
	  	  quitDialog.setTitle("Confirm to Quit?");
	  	  
	  	  quitDialog.setPositiveButton("OK, Quit!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ListviewLevel2.stopMusic();
					FavoritesActivity.stopMusic();
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
	public void startActivityFromChild(Activity child, Intent intent,
			int requestCode) {
		// TODO Auto-generated method stub
		  Log.d("test","vao day khong nhi");
		super.startActivityFromChild(child, intent, requestCode);
	}
	  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stu
		 Log.d("test","vao day moi dau");
		super.onActivityResult(requestCode, resultCode, data);
	}
	}