package com.android.musicdemo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = new String("/sdcard/music/test");
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	
	// Constructor
	public SongsManager(){
		
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(){
		File home = new File(MEDIA_PATH);
		File[] list = home.listFiles(new FileExtensionFilter());
		int length = list.length;
		if (length > 0) {
			for (int i =0; i < length; i++) {
				HashMap<String, String> song = new HashMap<String, String>();
				File tmp = list[length-1-i];
				song.put("songTitle", tmp.getName().substring(0, (tmp.getName().length() - 4)));
				song.put("songPath", tmp.getPath());
				
				// Adding each song to SongList
				songsList.add(song);
			}
			
//			for (File file : home.listFiles(new FileExtensionFilter())) {
//				HashMap<String, String> song = new HashMap<String, String>();
//				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
//				song.put("songPath", file.getPath());
//				
//				// Adding each song to SongList
//				songsList.add(song);
//			}
		}

		// return songs list array
		return songsList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".wav") || name.endsWith(".wav"));
		}
	}
}
