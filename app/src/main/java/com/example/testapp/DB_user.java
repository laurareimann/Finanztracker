package com.example.testapp;

/**
 * SOURCES
 * Database: BTech Days: "Login and Register Form using SQLite Database in Android Studio | Login Registration Android Studio", URL: https://www.youtube.com/watch?v=o9CVZ1gQgQo, 09.01.2023
 **/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_user extends SQLiteOpenHelper {

    public DB_user(Context context) {
        super(context, "DB_Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(" +
                "user_id integer primary key autoincrement, " +
                "user_name text, " +
                "user_password text, " +
                "user_balance double NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    // insert Data into Database, return true if successful
    public Boolean insertData(String username, String password, double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", username);
        contentValues.put("user_password", password);
        contentValues.put("user_balance", balance);
        long result = db.insert("users", null, contentValues);

        // check if adding Data is successful
        return result != -1;
    }

    // check if user already exists in Database
    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        // INFO: Cursor is an interface and it is used to retrieve data from collection object, one by one
        // put all users with the entered username in an array
        Cursor cursor = db.rawQuery("select * from users where user_name = ?", new String[]{username});
        // check if cursor has data --> if true user is already in Database
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    // check username and password
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where user_name = ? and user_password = ?", new String[]{username, password});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    // return user balance as String
    public String getUserBalance(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_balance FROM users WHERE user_name=\"" + username + "\"", null);

        cursor.moveToFirst();
        String balanceString = cursor.getString(0);
        cursor.close();
        return balanceString;
    }

    // return userID to username
    public int getUserID(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_id FROM users WHERE user_name=\"" + username + "\"", null);
        cursor.moveToFirst();
        int idInt = cursor.getInt(0);
        cursor.close();
        return idInt;
    }

    public double getUserBalanceDouble(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_balance FROM users WHERE user_name=\"" + username + "\"", null);

        cursor.moveToFirst();
        double balanceInt = cursor.getDouble(0);
        cursor.close();

        return balanceInt;
    }

    public void updateBalance(double amount, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        double newBalance;
        newBalance = getUserBalanceDouble(username) + amount;
        contentValues.put("user_balance", newBalance);
        db.update("users", contentValues, "user_name=\"" + username + "\"", null);
    }

}
