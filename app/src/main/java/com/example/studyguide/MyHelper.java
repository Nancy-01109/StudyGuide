package com.example.studyguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationsMa.db";
    private static final String TABLE_LOCATION = "LocationMarker";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GPS = "gps";
    private static final String COLUMN_DESCR = "descr";


    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_LOCATION + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_GPS + " TEXT," + COLUMN_DESCR + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }

    public ArrayList<Markers> listMarkers() {
        String sql = "select * from " + TABLE_LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Markers> storeMarkers = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String gps = cursor.getString(2);
                String description = cursor.getString(3);
                storeMarkers.add(new Markers(id, name, gps, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeMarkers;
    }

    public void addMarkers(Markers markers) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, markers.getName());
        values.put(COLUMN_GPS, markers.getGps());
        values.put(COLUMN_DESCR, markers.getDesription());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOCATION, null, values);
    }

    public void updateMarkers(Markers markers) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, markers.getName());
        values.put(COLUMN_GPS, markers.getGps());
        values.put(COLUMN_DESCR, markers.getDesription());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_LOCATION, values, COLUMN_ID + "	= ?", new String[]{String.valueOf(markers.getId())});

    }

    public Markers findMarker(String name) {
        String query = "Select * FROM " + TABLE_LOCATION + " WHERE " + COLUMN_NAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Markers markers = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String Cityname = cursor.getString(1);
            String Citygps = cursor.getString(2);
            String Citydes = cursor.getString(3);
            markers = new Markers(id, Cityname, Citygps, Citydes);
        }
        cursor.close();
        return markers;
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION, COLUMN_ID + "	= ?", new String[]{String.valueOf(id)});
    }
}
