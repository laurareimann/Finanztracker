package com.example.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EntriesDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = EntriesDbHelper.class.getSimpleName();

    public static final String DB_NAME = "expenses_list.db";
    public static final int DB_VERSION = 1;

    public static final String ENTRIES_LIST = "entry_list";

    // Columns for the Table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_ENTRY_VALUE = "entry_value";
    public static final String COLUMN_ENTRY_AMOUNT = "entry_amount";
    public static final String COLUMN_ENTRY_DATE = "entry_date";
    public static final String COLUMN_ENTRY_NOTICE = "entry_notice";
    //public static final String COLUMN_ENTRY_REPETITION = "entry_repetition";
    public static final String COLUMN_CAT_ID = "cat_id";

    // Command to create Table as String
    // TODO: Category raus, value erstmal raus, date als 3 seperate ints speichern und einmal datum als string
    public static final String SQL_CREATE =
            "CREATE TABLE " + ENTRIES_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " INTEGER NOT NULL, " +
                    COLUMN_ENTRY_VALUE + " INTEGER NOT NULL, " +
                    COLUMN_ENTRY_AMOUNT + " INTEGER NOT NULL, " +
                    COLUMN_ENTRY_DATE + " INTEGER NOT NULL, " +
                    COLUMN_ENTRY_NOTICE + " STRING, " +
                    /*COLUMN_ENTRY_REPETITION + "INTEGER NOT NULL, " + */
                    COLUMN_CAT_ID + " INTEGER NOT NULL);";

      // Constructor
    public EntriesDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper created the Database: " + getDatabaseName());
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            Log.d(LOG_TAG, "Table is created with SQL-Command: " + SQL_CREATE );
            db.execSQL(SQL_CREATE);
        } catch(Exception e){
            Log.e(LOG_TAG, "Error while creating the table: " + e.getMessage());
        }

    }

    /*
    The last argument specifies the version of our database.
    With each database upgrade this value is increased by one
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
