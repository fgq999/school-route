package com.tongxin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbTool extends SQLiteOpenHelper
{
	private final static String DATABASE_NAME = "places.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "place_table";
	public final static String PLACE_ID = "place_id";
	public final static String PLACE_NAME = "place_name";
	public final static String LATITUDE = "Latitude";
	public final static String LONGITUDE = "Longitude";

	public DbTool(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//创建table 
	public void onCreate(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + PLACE_ID
				+ " INTEGER primary key autoincrement, " + PLACE_NAME + " text, "+ LATITUDE + " text, " + LONGITUDE +" text);";
		db.execSQL(sql);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select(String placename)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, "place_name LIKE '%"+placename+"%'", null, null, null, null);
		return cursor;
	}
	//增加操作 
	public long insert(String placename,String latitude,String longitude)
	{
		SQLiteDatabase db = this.getWritableDatabase(); 
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(PLACE_NAME, placename);
		cv.put(LATITUDE, latitude);
		cv.put(LONGITUDE, longitude);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}
	//删除操作 
	public void delete(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = PLACE_ID + " = ?";
		String[] whereValue ={ Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}
	//修改操作 
	public void update(int id, String placename,String latitude,String longitude)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = PLACE_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };

		ContentValues cv = new ContentValues();
		cv.put(PLACE_NAME, placename);
		cv.put(LATITUDE, latitude);
		cv.put(LONGITUDE, longitude);
		db.update(TABLE_NAME, cv, where, whereValue);
	}
}

