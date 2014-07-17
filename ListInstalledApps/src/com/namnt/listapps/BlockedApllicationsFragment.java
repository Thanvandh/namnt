package com.namnt.listapps;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class BlockedApllicationsFragment extends SherlockFragment {
	
	
	private PackageManager packageManager = null;
	ListView lsvBlockedApps;
	private List<ApplicationInfo> applist = null;
	private ApplicationAdapter listadaptor = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.fragment_blocked_apps, container, false);
		
		setHasOptionsMenu(true);
 
		lsvBlockedApps=(ListView)rootView.findViewById(R.id.lsvBlockedApps);
		packageManager = getActivity().getApplication().getPackageManager();
		new LoadApplications().execute();
		
		lsvBlockedApps.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());
				//alert.setTitle(R.string.app_name);
				//alert.setIcon(R.drawable.ic_launcher);
				alert.setMessage("Do you want to enable this application?");
			
				alert.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								
								 //you may open Interstitial Ads here
								//interstitial.loadAd(adRequest);
								//finish();
							}
								 
			
							 
						});
			
				alert.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {
			
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								 
								 
							}
						});
				alert.show();
				
				
				
			}
		});
		return rootView;
	}
	
	private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
		ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
		for (ApplicationInfo info : list) {
			try {
				if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
					applist.add(info);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		return applist;
	}
 
	private class LoadApplications extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progress = null;

		@Override
		protected Void doInBackground(Void... params) {
			applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
			listadaptor = new ApplicationAdapter(getActivity(),
					R.layout.snippet_list_row, applist);

			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			lsvBlockedApps.setAdapter(listadaptor);
			progress.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(getActivity(), null,
					"Loading application info...");
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}


	 

	
	
}
