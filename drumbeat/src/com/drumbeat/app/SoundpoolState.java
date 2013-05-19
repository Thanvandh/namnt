package com.drumbeat.app;

public class SoundpoolState {

	boolean bplay = false;
	String playsong = "";
	String srate = "120";
	public SoundpoolState() {
		// TODO Auto-generated constructor stub
		bplay = false;
		playsong = "";
		srate = "120";
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	public  void setState(boolean b){
		bplay = b;
	}
	public  boolean getState(){
		return bplay;
	}

	public void setplaysong(String song){
		playsong = song;
	}
	public String getplaysong(){
		return playsong;
	}
	public void setrate(String rate){
		srate = rate;
	}
	public String getrate(){
		return srate;
	}
}
