package com.drumbeat.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	
	private static String DB_PATH = "/data/data/com.drumbeat.app/databases/";
	 
	private static String DB_NAME = "drumbeat";
	
	 private final Context myContext;
	 private SQLiteDatabase myDataBase; 
	// Database Name
	//private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLE_SONGS = "Songs";
	private static final String TABLE_FAVORITES = "Favorites";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_FOLDER = "folder";
	private static final String KEY_FILE = "file";

	public DatabaseHandler(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}
	 public void createDataBase() throws IOException{
		 
		 boolean dbExist = checkDataBase();
		  
		 if(dbExist){
		 //do nothing - database already exist
		 }else{
		  
		 //By calling this method and empty database will be created into the default system path
		 //of your application so we are gonna be able to overwrite that database with our database.
		 this.getReadableDatabase();
		  
		 try {
		  
		 copyDataBase();
		  
		 } catch (IOException e) {
		  
		 throw new Error("Error copying database");
		  
		 }
		 }
		  
		 }
	 private boolean checkDataBase(){
		 
		 SQLiteDatabase checkDB = null;
		  
		 try{
		 String myPath = DB_PATH + DB_NAME;
		 checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		  
		 }catch(SQLiteException e){
		  
		 //database does't exist yet.
		  
		 }
		  
		 if(checkDB != null){
		  
		 checkDB.close();
		  
		 }
		  
		 return checkDB != null ? true : false;
		 }
		  
		 /**
		   * Copies your database from your local assets-folder to the just created empty database in the
		   * system folder, from where it can be accessed and handled.
		   * This is done by transfering bytestream.
		   * */
		 private void copyDataBase() throws IOException{
		  
		 //Open your local db as the input stream
		 InputStream myInput = myContext.getAssets().open(DB_NAME);
		  
		 // Path to the just created empty db
		 String outFileName = DB_PATH + DB_NAME;
		  
		 //Open the empty db as the output stream
		 OutputStream myOutput = new FileOutputStream(outFileName);
		  
		 //transfer bytes from the inputfile to the outputfile
		 byte[] buffer = new byte[1024];
		 int length;
		 while ((length = myInput.read(buffer))>0){
		 myOutput.write(buffer, 0, length);
		 }
		  
		 //Close the streams
		 myOutput.flush();
		 myOutput.close();
		 myInput.close();
		  
		 }
		  
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
//		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
//				+ KEY_PH_NO + " TEXT" + ")";
//		db.execSQL(CREATE_CONTACTS_TABLE);
		
	}
	 public void openDataBase() throws SQLException{
		 
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
		 
		}
		 
		@Override
		public synchronized void close() {
		 
		if(myDataBase != null)
		myDataBase.close();
		 
		super.close();
		 
		}
		 
		
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
//	void addContact(Contact contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, contact.getName()); // Contact Name
//		values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
//
//		// Inserting Row
//		db.insert(TABLE_CONTACTS, null, values);
//		db.close(); // Closing database connection
//	}
	public void addfavorite(String folder, String file)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_FOLDER, folder); 
		values.put(KEY_FILE, file);
//		values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
		myDataBase.insert(TABLE_FAVORITES, null, values);
	}
	public void removefavorite(String folder, String file)
	{
		ContentValues values = new ContentValues();
		//values.put(KEY_ID, id); // Contact Name
//		values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
		myDataBase.delete(TABLE_FAVORITES, KEY_FOLDER + " = ? and " + KEY_FILE + " = ?",
				new String[] {folder, file });
	}
	public boolean getfavorite(String folder, String file)
	{
	//	String selectQuery = "select * from Favorites where folder ='" + folder + "' and file ='" + file + "'";
	//	Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		Cursor cursor = myDataBase.query(TABLE_FAVORITES, new String[] { KEY_FOLDER, KEY_FILE}, KEY_FOLDER + " = ? and " + KEY_FILE + " = ?",
				new String[] { folder, file }, null, null, null, null);
		if (cursor != null)
		{
			if (cursor.getCount()>0)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	// Getting single contact
	public Song getSong(String id) {
		//SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = myDataBase.query(TABLE_SONGS, new String[] { KEY_ID,
				KEY_NAME, KEY_FOLDER }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Song song = new Song(cursor.getString(0),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return song;
	}
	public List<Song> getFavariteSong() {
		//SQLiteDatabase db = this.getReadableDatabase();
		List<Song> songList = new ArrayList<Song>();
		String selectQuery = "select folder, file from Favorites";
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		if (cursor != null)
		{
			if (cursor.getCount()>0)
			{
				if (cursor.moveToFirst()) {
					do {
						Song song = new Song();
						song.setID("1");
						song.setName(cursor.getString(1));
						song.setFolder(cursor.getString(0));
						songList.add(song);
						
					} while (cursor.moveToNext());
				}
				return songList;
			}
			else
				return null;
		}
		else
			return null;
		
	}
	
	public String[] getListFolder ()
	{
		String [] list;
		String selectQuery = "select distinct folder from songs";// + TABLE_SONGS;
		
				//SQLiteDatabase db = this.getReadableDatabase();
				Cursor cursor = myDataBase.rawQuery(selectQuery, null);
				int length = cursor.getCount();
				list = new String[length];
				int i = 0;
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						list[i] = cursor.getString(0);
						i++;
						
					} while (cursor.moveToNext());
				}
		return list;
	}
	public List<Song> getListSongbyFolder (String folder)
	{
		List<Song> songList = new ArrayList<Song>();
		String selectQuery = "select * from songs where folder ='" + folder + "'";
		
				//SQLiteDatabase db = this.getReadableDatabase();
				Cursor cursor = myDataBase.rawQuery(selectQuery, null);
				
				
				int i = 0;
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Song song = new Song();
						song.setID(cursor.getString(1));
						song.setName(cursor.getString(2));
						song.setFolder(cursor.getString(0));
						songList.add(song);
						
					} while (cursor.moveToNext());
				}
		return songList;
	}
	
	// Getting All Contacts
//	public List<Contact> getAllContacts() {
//		List<Contact> contactList = new ArrayList<Contact>();
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				Contact contact = new Contact();
//				contact.setID(Integer.parseInt(cursor.getString(0)));
//				contact.setName(cursor.getString(1));
//				contact.setPhoneNumber(cursor.getString(2));
//				// Adding contact to list
//				contactList.add(contact);
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return contactList;
//	}
//
//	// Updating single contact
//	public int updateContact(Contact contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, contact.getName());
//		values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//		// updating row
//		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//				new String[] { String.valueOf(contact.getID()) });
//	}
//
//	// Deleting single contact
//	public void deleteContact(Contact contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//				new String[] { String.valueOf(contact.getID()) });
//		db.close();
//	}
//
//
//	// Getting contacts Count
//	public int getContactsCount() {
//		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
//	}

}
