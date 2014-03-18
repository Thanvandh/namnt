package vtc.namnt.ultility;

import java.util.ArrayList;

import vtc.namnt.database.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class ManagementInfo {
	private LocalDatabase mlocaldata;
	private SQLiteDatabase db;
	private SQLiteDatabase dbwrite;
	private String mdate;
	
	public ManagementInfo(Context context) {
		// TODO Auto-generated constructor stub
		mlocaldata = new LocalDatabase(context );
		//db = mlocaldata.getReadableDatabase();
		dbwrite = mlocaldata.getWritableDatabase();
	}
	public void setdate(String date)
	{
		mdate = date;
	}
//	public Cursor getversion()
//	{
//		//SQLiteDatabase db = mlocaldata.getReadableDatabase();
//        Cursor cursor = db.query(LocalDatabase.version, null, null, null, null, null, null);
//		return cursor;
//	}
//	public Cursor getinfomation()
//    {    	
//    	//SQLiteDatabase db = mlocaldata.getReadableDatabase();
//        Cursor cursor = db.query(LocalDatabase.detailofchannel+","+LocalDatabase.listofchannels, null, "(detailofchannel.idchannel = listofchannels.idchannel) AND (detailofchannel.starttime < '06:44:00') AND (detailofchannel.endtime > '06:44:00')", null, null,
//                null, null);
//        return cursor;
//    }
	public void close()
	{
		mlocaldata.close();
		//db.close();
		dbwrite.close();
	}
	public void insertdata()
	  {    	
//	  	String[] columns;
//	  	columns = new String[4];
//	  	columns[0] = "id";
//	  	columns[1] = "description";
//	  	columns[2] = "danhngon";
//	  	columns[3] = "tacgia";
	
	  	for (int i = 0; i < DanhNgon.length; i++) {
	  		String values = "(" + i + ",NULL,'" + DanhNgon[i] + "',NULL)";
	  		dbwrite.execSQL("Insert into " + LocalDatabase.danhngon + " values " + values);
			}
	    
	  }
	/**
	 * @return
	 */
////	public int getnumberchannel(int mode)
////    {    	
////		int result = 0;
////		Cursor cursor = null;
////		if (mode == 0)
////		{
////		cursor = db.query(LocalDatabase.listofchannels, null, null, null, null,
////                null, null);
////        result = cursor.getCount();
////		}
////		else if (mode == 1)
////		{
////			cursor = db.query(LocalDatabase.favorites, null, null, null, null,
////	                null, null);
////	        result = cursor.getCount();	
////		}
////        cursor.close();
////        return result;
////    }
////	public Cursor getodernumber(int mode)
////    {    	
////    	String[] columns;
////    	columns = new String[2];
////    	columns[0] = "ID";
////    	columns[1] = "No";
////        Cursor cursor = null;
////        if (mode == 0)
////        	cursor = db.query(LocalDatabase.odernumber, columns, null, null, null, null, null);
////        else if (mode == 1)
////        	cursor = db.query(LocalDatabase.odernumberfav, columns, null, null, null, null, null);
////        return cursor;
////    }
//	public void updateallodernumber(ArrayList<Integer> iodernumber, int mode)
//    {    	
//    	String[] columns;
//    	columns = new String[2];
//    	columns[0] = "ID";
//    	columns[1] = "No";
//    	if (mode == 0)
//    	{
//    	dbwrite.execSQL("Delete from " + LocalDatabase.odernumber);
//    	for (int i = 0; i < iodernumber.size(); i++) {
//    		String values = "(" + String.valueOf(iodernumber.get(i)) + "," + String.valueOf(i) + ")";
//    		dbwrite.execSQL("Insert into " + LocalDatabase.odernumber + " values " + values);
//		}
//    	}
//    	else if (mode == 1)
//    	{
//        	dbwrite.execSQL("Delete from " + LocalDatabase.odernumberfav);
//        	for (int i = 0; i < iodernumber.size(); i++) {
//        		String values = "(" + String.valueOf(iodernumber.get(i)) + "," + String.valueOf(i) + ")";
//        		dbwrite.execSQL("Insert into " + LocalDatabase.odernumberfav + " values " + values);
//    		}
//        	}
//    	
//      
//    }
//	public void addfavorites (int idchannel,String namechannel)
//	{
//		String values = "(" + idchannel + ",'" + namechannel + "')";
//		dbwrite.execSQL("Insert into " + LocalDatabase.favorites + " values " + values);
//		Cursor cursor = db.query(LocalDatabase.favorites, null, null, null, null,
//                null, null);
//        int result = cursor.getCount()-1;	
//		values = "(" + idchannel + "," + result + ")";
//		dbwrite.execSQL("Insert into " + LocalDatabase.odernumberfav + " values " + values);
//		
//	}
//	public void removefavorites (int id)
//	{
//		String where = " where idchannel=" + id;
//		dbwrite.execSQL("Delete from " + LocalDatabase.favorites + where);
//		where = " where ID=" + id;
//		dbwrite.execSQL("Delete from " + LocalDatabase.odernumberfav + where);
//	}
//	public Cursor getnamechannel(int mode)
//    {    	
//    	String[] columns;
//    	columns = new String[2];
//    	columns[0] = "No";
//    	columns[1] = "namechannel";
//    	String where = "listofchannels.idchannel = odernumber.ID";
//    	
//    	Cursor cursor = null;
//    	if (mode == 0)
//    		cursor = db.query(LocalDatabase.listofchannels+","+LocalDatabase.odernumber, columns, where, null, null, null, null);
//    	else if (mode == 1)
//    	{
//    		where = "favorites.idchannel = odernumberfav.ID";
//    		cursor = db.query(LocalDatabase.favorites+","+LocalDatabase.odernumberfav, columns, where, null, null, null, null);
//    	}
//        return cursor;
//    }
//	public String getnamechannelfromid(String idchannel)
//	{
//		String[] columns;
//    	String where = "idchannel = '" + idchannel + "'";
//    	columns = new String[1];
//    	columns[0] = "namechannel";
//        Cursor cursor = db.query(LocalDatabase.listofchannels,columns, where, null, null, null, null);
//        cursor.moveToFirst();
//        String result = cursor.getString(0);
//        cursor.close();
//        return result;
//	}
//	public Cursor getlistprogram()
//    {    	
//    	String[] columns;
//    	String where = "datetime = '" + mdate + "'";
//    	columns = new String[4];
//    	columns[0] = "idchannel";
//    	columns[1] = "idprogram";
//    	columns[2] = "(strftime('%s',detailofchannel.endtime) - strftime('%s',detailofchannel.starttime ))";
//    	columns[3] = "description";
//        Cursor cursor = db.query(LocalDatabase.detailofchannel,columns, where, null, null, null, null);
//        return cursor;
//    }
//	public Cursor getlistprograminchannel(String idchannel, String day)
//    {    	
//    	String[] columns;
//    	String where = "(datetime = '" + day + "') AND (idchannel = '" + idchannel + "')";
//    	columns = new String[5];
//    	columns[0] = "idchannel";
//    	columns[1] = "idprogram";
//    	columns[2] = "description";
//    	columns[3] = "starttime";
//    	columns[4] = "endtime";
//        Cursor cursor = db.query(LocalDatabase.detailofchannel,columns, where, null, null, null, null);
//        return cursor;
//    }
//	public Cursor getlistprograminhour(String from, String to, int mode)
//    {    	
//    	String[] columns;
//    	//String where = "(datetime = '" + mdate + "') " + "AND (detailofchannel.starttime > '00:00:00') AND (detailofchannel.starttime < '07:00:00') AND (listofchannels.idchannel = detailofchannel.idchannel)";
//    	String where = null; 
//    	Cursor cursor = null;
//    	columns = new String[5];
//    	columns[0] = "No";
//    	columns[1] = "idprogram";
//    	//columns[2] = "(strftime('%s',detailofchannel.endtime) - strftime('%s',detailofchannel.starttime ))";
//    	columns[2] = "detailofchannel.starttime";
//    	columns[3] = "detailofchannel.endtime";
//    	columns[4] = "description";
//    	if (mode == 0)
//    	{
//    		where = "(datetime = '" + mdate + "') " + "AND (detailofchannel.starttime < '" + to + "') AND (detailofchannel.endtime > '" + from + "') AND (odernumber.ID = detailofchannel.idchannel)";
//    		cursor = db.query(LocalDatabase.detailofchannel + "," + LocalDatabase.odernumber,columns, where, null, null, null, null);
//    	}
//    	else if (mode == 1)
//    	{
//    		where = "(datetime = '" + mdate + "') " + "AND (detailofchannel.starttime < '" + to + "') AND (detailofchannel.endtime > '" + from + "') AND (odernumberfav.ID = detailofchannel.idchannel)";
//    		cursor = db.query(LocalDatabase.detailofchannel + "," + LocalDatabase.odernumberfav,columns, where, null, null, null, null);
//    	}
//        return cursor;
//        
//        //SQL SELECT listofchannels.idchannel, idprogram, namechannel, description, starttime, endtime FROM listofchannels, detailofchannel WHERE (detailofchannel.datetime = '2011-08-05') AND (listofchannels.idchannel = detailofchannel.idchannel) AND (detailofchannel.starttime > '06:00') AND (detailofchannel.starttime < '12:00')
//    }
//	public Cursor searchprogram(String keywords)
//    {    	
//    	String[] columns;
//    	String argument;
//    	columns = new String[6];
//    	columns[0] = "listofchannels.idchannel";
//    	columns[1] = "idprogram";
//    	columns[2] = "namechannel";
//    	columns[3] = "description";
//    	columns[4] = "detailofchannel.starttime";
//    	columns[5] = "detailofchannel.endtime";
//    	argument = "(detailofchannel.idchannel = listofchannels.idchannel) AND " + "(description like" + "'%" + keywords + "%')";
//        Cursor cursor = db.query(LocalDatabase.detailofchannel+","+LocalDatabase.listofchannels,columns, argument, null, null, null, null);
//        //SQL SELECT listofchannels.idchannel, idprogram, namechannel, description, starttime, endtime FROM listofchannels, detailofchannel WHERE (detailofchannel.idchannel = listofchannels.idchannel) AND (description like  '%Hà Nội%')
//        return cursor;
//    }
//	public Cursor searchprogrambydate(String keywords, String from, String to)
//    {    	
//    	String[] columns;
//    	String argument;
//    	columns = new String[6];
//    	columns[0] = "listofchannels.idchannel";
//    	columns[1] = "idprogram";
//    	columns[2] = "namechannel";
//    	columns[3] = "description";
//    	columns[4] = "detailofchannel.starttime";
//    	columns[5] = "detailofchannel.endtime";
//    	argument = "(detailofchannel.idchannel = listofchannels.idchannel) AND " + "(description like" + "'%" + keywords + "%') AND " +"(datetime >= '" + from + "') AND (datetime <= '" + to + "')";
//        Cursor cursor = db.query(LocalDatabase.detailofchannel+","+LocalDatabase.listofchannels,columns, argument, null, null, null, null);
//        //SQL SELECT listofchannels.idchannel, idprogram, namechannel, description, starttime, endtime FROM listofchannels, detailofchannel WHERE (detailofchannel.idchannel = listofchannels.idchannel) AND (description like  '%Hà Nội%')
//        return cursor;
//    }
//	public boolean checkfav(int input)
//    {    	
//        boolean result = false;
//		Cursor cursor = db.query(LocalDatabase.favorites, null, "idchannel = " + String.valueOf(input), null, null,
//                null, null);
//		if (cursor.getCount() > 0) result = true;
//		else result = false;
//		cursor.close();
//        return result;
//    }
//	public Cursor getprogram(int idchannel, int idprogram)
//    {    	
//    	String[] columns;
//    	String argument;
//    	columns = new String[6];
//    	columns[0] = "detailofchannel.idchannel";
//    	columns[1] = "idprogram";
//    	columns[2] = "namechannel";
//    	columns[3] = "description";
//    	columns[4] = "detailofchannel.starttime";
//    	columns[5] = "detailofchannel.endtime";
//    	argument = "(detailofchannel.idchannel = " + idchannel + ") AND (detailofchannel.idchannel = listofchannels.idchannel) AND (idprogram = " + idprogram + " ) AND " +"(detailofchannel.datetime = '" + mdate + "' )";
//        Cursor cursor = db.query(LocalDatabase.detailofchannel + "," + LocalDatabase.listofchannels,columns, argument, null, null, null, null);
//        //SQL SELECT listofchannels.idchannel, idprogram, namechannel, description, starttime, endtime FROM listofchannels, detailofchannel WHERE (detailofchannel.idchannel = listofchannels.idchannel) AND (detailofchannel.idchannel = 0) AND (idprogram = 1)
//        return cursor;
//    }
}
