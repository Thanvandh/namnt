package namnt.drumbeat.utils;

public class Song {
	
	//private variables
	String _id;
	String _name;
	String _folder;
	
	// Empty constructor
	public Song(){
		
	}
	// constructor
	public Song(String id, String name, String folder){
		this._id = id;
		this._name = name;
		this._folder = folder;
	}
	
	// constructor
	public Song(String name, String folder){
		this._name = name;
		this._folder = folder;
	}
	// getting ID
	public String getID(){
		return this._id;
	}
	
	// setting id
	public void setID(String id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getFolder(){
		return this._folder;
	}
	
	// setting phone number
	public void setFolder(String folder){
		this._folder = folder;
	}
}
