package com.namnt.listapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {

	private String[] tabs = { "ALL APPLICATIONS", "BLOCKED APPLICATIONS"};
    private TabsPagerAdapter mAdapter;
    private ViewPager viewPager;
    ActionBar.Tab tab;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

  
        
	    //adView.setAdUnitId(AD_UNIT_ID);
//		interstitial = new InterstitialAd(MainActivity.this,"3161adb9cc394cd6");
//		this.interstitial.setAdListener(this);
       // mAdView.setAdUnitId(AD_UNIT_ID);
        //mAdView.setAdSize(AdSize.BANNER);
//        //mAdView.setAdListener(new ToastAdListener(this));
       // mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
       // mAdView.setAdSize(AdSize.BANNER);
        
        //mAdView.setAdListener(new ToastAdListener(this));
       
		
        viewPager = (ViewPager) findViewById(R.id.pager);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        //TelephonyManager mngr = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE); 
        //String deviceId = mngr.getDeviceId();
        //Toast.makeText(this, "deviceId " + deviceId, Toast.LENGTH_LONG).show();
        viewPager.setAdapter(mAdapter);
        
        //tab added in action bar
         for(String tab_name:tabs)
         {
        	tab = getSupportActionBar().newTab();
            tab.setText(tab_name);
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
         }
         
         /**
 		 * on swiping the viewpager make respective tab selected
 		 * */
 		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

 			@Override
 			public void onPageSelected(int position) {
 				// on changing the page
 				// make respected tab selected
 				getSupportActionBar().setSelectedNavigationItem(position);
 			}

 			@Override
 			public void onPageScrolled(int arg0, float arg1, int arg2) {
 			}

 			@Override
 			public void onPageScrollStateChanged(int arg0) {
 			}
 		});
    }
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		// Toast.makeText(appContext, "BAck", Toast.LENGTH_LONG).show();
		AlertDialog.Builder alert = new AlertDialog.Builder(
				MainActivity.this);
		alert.setTitle(R.string.app_name);
		alert.setIcon(R.drawable.ic_launcher);
		alert.setMessage("Are You Sure You Want To Quit?");
	
		alert.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						
						 //you may open Interstitial Ads here
						//interstitial.loadAd(adRequest);
						finish();
					}
						 
	
					 
				});
	
		alert.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
	
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						 
						 
					}
				});
		alert.show();
		return true;
	}
	
	return super.onKeyDown(keyCode, event);
	
	}

}
