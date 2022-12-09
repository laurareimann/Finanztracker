package com.example.testapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class EntriesDataSource {

    private static final String LOG_TAG = EntriesDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private EntriesDbHelper dbHelper;

    // Array needed for search queries
    // It contains the names of thee columns in our entries table.
    private String[] columns = {
            EntriesDbHelper.COLUMN_ID,
            EntriesDbHelper.COLUMN_USER_ID,
            EntriesDbHelper.COLUMN_ENTRY_VALUE,
            EntriesDbHelper.COLUMN_ENTRY_AMOUNT,
            EntriesDbHelper.COLUMN_ENTRY_DATE,
            EntriesDbHelper.COLUMN_ENTRY_NOTICE,
            /*EntriesDbHelper.COLUMN_ENTRY_REPETITION */
            EntriesDbHelper.COLUMN_CAT_ID
    };


    /*
    With the help of dbHelper we will establish the connection
    to our SQLite database
     */
    public EntriesDataSource(Context context){
        Log.d(LOG_TAG, "DataSource now creates the dbHelper.");
        dbHelper = new EntriesDbHelper(context);
    }

    public void open(){
        Log.d(LOG_TAG, "A reference to the database is now requested.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Received database reference. Path to the database: " + database.getPath());
    }

    public void close(){
        dbHelper.close();
        Log.d(LOG_TAG, "Database closed with the help of DbHelper");
    }

    // Insert Data into the table of our SQLite database
    public Entries createEntries(int userID, int entryValue, int entryAmount,
                                      int entryDate, String entryNotice, /*entryRepetition,*/
                                        int catID){
        ContentValues values = new ContentValues();
        values.put(EntriesDbHelper.COLUMN_USER_ID, userID);
        values.put(EntriesDbHelper.COLUMN_ENTRY_VALUE, entryValue);
        values.put(EntriesDbHelper.COLUMN_ENTRY_AMOUNT, entryAmount);
        values.put(EntriesDbHelper.COLUMN_ENTRY_DATE, entryDate);
        values.put(EntriesDbHelper.COLUMN_ENTRY_NOTICE, entryNotice);
        /*values.put(EntriesDbHelper.COLUMN_ENTRY_REPETITION, entryRepetition);*/
        values.put(EntriesDbHelper.COLUMN_CAT_ID, catID);



        // Insert values into database
        // Return value is the ID of the created dataset
        long insertId = database.insert(EntriesDbHelper.ENTRIES_LIST, null, values);

        // Read out objects again for control purposes
        // return value is a cursor
        Cursor cursor = database.query(EntriesDbHelper.ENTRIES_LIST,
                columns, EntriesDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        // We move the cursor to the position of its first record.
        // Then we call the cursorToEntries() method and thereby
        // convert the record of the cursor object into an Entries object
        cursor.moveToFirst();
        Entries entries = cursorToEntry(cursor);
        cursor.close();

        return entries;
    };

    // Convert Data into Entries objects
    private Entries cursorToEntry(Cursor cursor){
        // Read indexes of the table columns
        int idIndex = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ID);
        int idUserId = cursor.getColumnIndex(EntriesDbHelper.COLUMN_USER_ID);
        int idValue = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_VALUE);
        int idAmount = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_AMOUNT);
        int idDate = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_DATE);
        int idNotice = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_NOTICE);
        /*int idRepetition = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_REPETITION);*/
        int idCatID = cursor.getColumnIndex(EntriesDbHelper.COLUMN_CAT_ID);

        // get contents with indexes from above
        long id = cursor.getLong(idIndex);
        int user_id = cursor.getInt(idUserId);
        int entry_value = cursor.getInt(idValue);
        int entry_amount = cursor.getInt(idAmount);
        int entry_date = cursor.getInt(idDate);
        String entry_notice = cursor.getString(idNotice);
        int cat_id = cursor.getInt(idCatID);
        /*int entry_repetition = cursor.getInt(idRepetition);*/

        // Save read data for further use
        Entries entries = new Entries(id,user_id,entry_value,entry_amount,
                                        entry_date,entry_notice,cat_id);

        return entries;
    }

    // Read all existing records from the table
    public List<Entries> getAllEntries(){
        List<Entries> entriesList = new ArrayList<>();

        // Search query where all records are returned
        Cursor cursor = database.query(EntriesDbHelper.ENTRIES_LIST, columns,
                null, null, null, null, null);

        //Set the obtained cursor object to the first position
        cursor.moveToFirst();
        Entries entries;

        // Read all data of the search query and convert them into Entries objects
        // Add them to the EntriesList
        // Output to the console
        while(!cursor.isAfterLast()){
            entries = cursorToEntry(cursor);
            entriesList.add(entries);
            Log.d(LOG_TAG, "ID: " + entries.getId() + ", Inhalt: "
                    + entries.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return entriesList;
    }
}
