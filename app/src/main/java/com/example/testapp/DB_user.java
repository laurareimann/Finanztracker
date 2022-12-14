package com.example.testapp;

/**
 * SOURCES
 * Database: BTech Days: "Login and Register Form using SQLite Database in Android Studio | Login Registration Android Studio", URL: https://www.youtube.com/watch?v=o9CVZ1gQgQo, 09.01.2023
 **/

// Content.Context:
// it's the context of current state of the application/object.
// It lets newly-created objects understand what has been going on.
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
        // TODO: add account balance and mail here later
        db.execSQL("create Table users(" +
                "user_id integer primary key autoincrement, " +
                "user_name text, " +
                "user_password text, " +
                "user_balance integer NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    // TODO: Do we need user_id?
    // insert Data into Database, return true if successful
    // TODO: balance dem konstruktor zufügen
    public Boolean insertData (String username, String password, float balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", username);
        contentValues.put("user_password", password);
        contentValues.put("user_balance", balance);
        long result = db.insert("users", null, contentValues);

        // check if adding Data is successful (?)
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    // check if user already exists in Database
    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        // INFO: Cursor is an interface and it is used to retrieve data from collection object, one by one
        // put all users with the entered username in an array
        Cursor cursor = db.rawQuery("select * from users where user_name = ?", new String[] {username});

        // check if cursor has data --> if true user is already in Database
        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    // check username and password
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where user_name = ? and user_password = ?", new String[] {username, password});

        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    // return user balance as String
    public String getUserBalance(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_balance FROM users WHERE user_name=\"" + username + "\"", null);

        cursor.moveToFirst();
        String balanceString = cursor.getString(0);

        return balanceString;
    }

    // return userID to username
    public int getUserID(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_id FROM users WHERE user_name=\"" + username + "\"", null);

        cursor.moveToFirst();
        int idInt = cursor.getInt(0);

        return idInt;
    }

    public float getUserBalanceFloat(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_balance FROM users WHERE user_name=\"" + username + "\"", null);

        cursor.moveToFirst();
        float balanceInt = cursor.getFloat(0);
        cursor.close();

        return balanceInt;
    }
    public void updateBalance(float amount,String username,boolean isChecked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        float newBalance;
        newBalance =  getUserBalanceFloat(username) + amount;
        contentValues.put("user_balance",newBalance);
        db.update("users",contentValues,"user_name=\"" +username+ "\"",null);

    }

}
