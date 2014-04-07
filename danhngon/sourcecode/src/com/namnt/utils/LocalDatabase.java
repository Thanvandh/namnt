package com.namnt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.namnt.danhngon.Quote;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class LocalDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "/data/data/com.namnt.danhngon/databases/danhngon.db";
	// private static final String DATABASE_NAME = "smartguide.db";
	private static final int DATABASE_VERSION = 1;
	private String msql = null;

	// Table name

	//public static final String danhngon = "danhngon";

	private static String DB_PATH = "/data/data/com.namnt.danhngon/databases/";

	private static String DB_NAME = "danhngon.db";

	private final Context myContext;
	private SQLiteDatabase myDataBase;

	// Columns
	public static final String col_id = "id";
	public static final String col_description = "description";
	public static final String col_danhngon = "danhngon";
	public static final String col_tacgia = "tacgia";

	public LocalDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;

	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database " + e.toString());

			}
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
		// + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
		// + KEY_PH_NO + " TEXT" + ")";
		// db.execSQL(CREATE_CONTACTS_TABLE);

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	// @Override
	// public void onCreate(SQLiteDatabase db) {
	// // String sql = "create table " + TABLE + "( " + BaseColumns._ID
	// // + " integer identify, " + idchannel + " integer, primary key(" +
	// BaseColumns._ID + "," + idchannel + "));";
	// Log.d("EventsData", "onCreate: smartguide");
	// boolean dbExist = checkDataBase(db);
	// if(!dbExist){
	//
	// msql =
	// "create table danhngon (id INTEGER NOT NULL, description VARCHAR, danhngon VARCHAR, tacgia VARCHAR, PRIMARY KEY (id));";
	// Log.v("test",msql);
	// db.execSQL(msql);
	//
	// // msql = "insert into android_metadata VALUES('en_US')";
	// // db.execSQL(msql);
	// }
	// }
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;

		String sql = null;
		if (oldVersion == 1) {
			sql = "alter table danhngon add note text;";
			db.execSQL(sql);

		}
		if (oldVersion == 2)
			sql = "";

		Log.d("danhngon", "onUpgrade	: " + sql);
		if (sql != null)
			db.execSQL(sql);
	}

	public List<Quote> getListQuotes() {
		// SQLiteDatabase db = this.getReadableDatabase();
		List<Quote> List = new ArrayList<Quote>();
		String selectQuery = "select danhngon.id, danhngon.description, danhngon.danhngon, danhngon.tacgia from danhngon";
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					do {
						Quote entry = new Quote(cursor.getString(0),
								cursor.getString(1), cursor.getString(2),
								cursor.getString(3));
						// entry.setID(cursor.getString(0));
						// entry.settitle(cursor.getString(1));
						// entry.setcontent(cursor.getString(2));
						List.add(entry);

					} while (cursor.moveToNext());
				}
				cursor.close();
				return List;
			} else {
				cursor.close();
				return null;
			}
		} else {
			return null;
		}
		
	}
}
/*
 * SQL SELECT *,(strftime('%s',detailofchannel.endtime) -
 * strftime('%s',detailofchannel.starttime )) FROM detailofchannel,
 * listofchannels WHERE (detailofchannel.idchannel = listofchannels.idchannel)
 * AND (detailofchannel.starttime < '06:44:00') AND (detailofchannel.endtime >
 * '06:44:00')
 */
