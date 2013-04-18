package com.drumbeat.app;

public class SoundpoolState {

	static boolean bplaylist = false;
	static boolean bplayfav = false;
	static String playsong = "";
	public static void setStateList(boolean b){
		bplaylist = b;
	}
	public static boolean getStateList(){
		return bplaylist;
	}
	public static void setStateFav(boolean b){
		bplayfav = b;
	}
	public static boolean getStateFav(){
		return bplayfav;
	}
	public static boolean getState(){
		boolean res = bplayfav || bplaylist;
		return res;
	}
	public static void setplaysong(String song){
		playsong = song;
	}
	public static String getplaysong(){
		return playsong;
	}
}
