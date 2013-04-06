package com.drumbeat.app;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class MoreActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_layout);
    }
    private void openQuitDialog(){
	  	  AlertDialog.Builder quitDialog 
	  	   = new AlertDialog.Builder(MoreActivity.this);
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
	  public void onBackPressed() {
		openQuitDialog(); 
	  }
}