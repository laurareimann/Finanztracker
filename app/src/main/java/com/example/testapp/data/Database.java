package com.example.testapp.data;
/**
 * Database for barChart in Statisistcs Activity
 **/
/* Source: CodingMark: "Android Studio: Create a Bar Chart using SqLite", URL: https://www.youtube.com/watch?v=niLkRACZEMg, 16.12.2022*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String BARCHART_TABLE = "CREATE TABLE " +
                Constant.TABLE_NAME + " (" +
                Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constant.DATE + " STRING, " +
                Constant.SPENDINGS + " STRING);";

        db.execSQL(BARCHART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
        onCreate(db);
    }


    public void saveData(String valX, String valY) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.DATE, valX);
        values.put(Constant.SPENDINGS, valY);

        db.insert(Constant.TABLE_NAME, null, values);
    }

    public ArrayList<String> queryXData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> xData = new ArrayList<>();

        String query = "SELECT " + Constant.DATE + " FROM " + Constant.TABLE_NAME + " GROUP BY " + Constant.DATE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount()>0) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                xData.add(cursor.getString(0));
                System.out.println("current cursor value: " + cursor.getString(0)); // TODO: Kommentare nach Debugging löschen
            }
            cursor.close();
        }
        System.out.println("xData: " + xData); // TODO: Kommentare nach Debugging löschen
        return xData;
    }

    public ArrayList<String> queryYData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> yData = new ArrayList<>();

        String query = "SELECT SUM(" + Constant.SPENDINGS + ") FROM " + Constant.TABLE_NAME + " WHERE " + Constant.SPENDINGS + " IS NOT NULL " + " GROUP BY " + Constant.DATE;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount()>0) {
            for (cursor.moveToFirst(); !cursor.isLast(); cursor.moveToNext()) {
                yData.add(cursor.getString(0));
            }
            cursor.close();
        }

        return yData;
    }

}
