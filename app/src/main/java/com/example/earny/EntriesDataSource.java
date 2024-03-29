package com.example.earny;

/**
 * SOURCES
 * Database: CodeYourApp: "Spezialkurs: SQLite Datenbank App Programmieren (ab API Version 29)", URL: https://www.codeyourapp.de/sqlite-app-programmieren-kurs/, 09.01.2023
 **/

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EntriesDataSource {

    private static final String LOG_TAG = EntriesDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private final EntriesDbHelper dbHelper;


    // Array needed for search queries
    // It contains the names of the columns in our entries table.
    private final String[] columns = {
            EntriesDbHelper.COLUMN_ID,
            EntriesDbHelper.COLUMN_USER_ID,
            EntriesDbHelper.COLUMN_ENTRY_AMOUNT,
            EntriesDbHelper.COLUMN_ENTRY_NOTICE,
            EntriesDbHelper.COLUMN_ENTRY_DATE,
            EntriesDbHelper.COLUMN_ENTRY_DAY,
            EntriesDbHelper.COLUMN_ENTRY_MONTH,
            EntriesDbHelper.COLUMN_ENTRY_YEAR
    };

    /*
    With the help of dbHelper we will establish the connection
    to our SQLite database
     */
    public EntriesDataSource(Context context) {
        Log.d(LOG_TAG, "DataSource now creates the dbHelper.");
        dbHelper = new EntriesDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "A reference to the database is now requested.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Received database reference. Path to the database: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Database closed with the help of DbHelper");
    }

    // Insert Data into the table of our SQLite database
    public void createEntries(int userID, double entryAmount, String entryNotice,
                                 String entryDate, int entryDay, int entryMonth, int entryYear) {
        ContentValues values = new ContentValues();
        values.put(EntriesDbHelper.COLUMN_USER_ID, userID);
        values.put(EntriesDbHelper.COLUMN_ENTRY_AMOUNT, entryAmount);
        values.put(EntriesDbHelper.COLUMN_ENTRY_NOTICE, entryNotice);
        values.put(EntriesDbHelper.COLUMN_ENTRY_DATE, entryDate);
        values.put(EntriesDbHelper.COLUMN_ENTRY_DAY, entryDay);
        values.put(EntriesDbHelper.COLUMN_ENTRY_MONTH, entryMonth);
        values.put(EntriesDbHelper.COLUMN_ENTRY_YEAR, entryYear);

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
    }

    // Convert Data into Entries objects
    private Entries cursorToEntry(Cursor cursor) {
        // Read indexes of the table columns
        int idIndex = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ID);
        int idUserId = cursor.getColumnIndex(EntriesDbHelper.COLUMN_USER_ID);
        int idAmount = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_AMOUNT);
        int idNotice = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_NOTICE);
        int idDate = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_DATE);
        int idDay = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_DAY);
        int idMonth = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_MONTH);
        int idYear = cursor.getColumnIndex(EntriesDbHelper.COLUMN_ENTRY_YEAR);


        // get contents with indexes from above
        long id = cursor.getLong(idIndex);
        int user_id = cursor.getInt(idUserId);
        double entry_amount = cursor.getDouble(idAmount);
        String entry_notice = cursor.getString(idNotice);
        String entry_date = cursor.getString(idDate);
        int entry_day = cursor.getInt(idDay);
        int entry_month = cursor.getInt(idMonth);
        int entry_year = cursor.getInt(idYear);

        // Save read data for further use

        return new Entries(id, user_id, entry_amount, entry_notice,
                entry_date, entry_day, entry_month, entry_year);
    }

    // Read all existing records from a User from the table
    public List<Entries> getAllEntriesFromUser(int userID) {
        List<Entries> entriesList = new ArrayList<>();

        // Search query where all records are returned
        Cursor cursor = database.query(EntriesDbHelper.ENTRIES_LIST, columns,
                EntriesDbHelper.COLUMN_USER_ID + "=\"" + userID + "\"", null, null, null, null);

        //Set the obtained cursor object to the first position
        cursor.moveToFirst();
        Entries entries;

        // Read all data of the search query and convert them into Entries objects
        // Add them to the EntriesList
        // Output to the console
        while (!cursor.isAfterLast()) {
            entries = cursorToEntry(cursor);
            entriesList.add(entries);
            Log.d(LOG_TAG, "ID: " + entries.getId() + ", Inhalt: "
                    + entries);
            cursor.moveToNext();
        }
        cursor.close();
        return entriesList;
    }

    // get sum of all expenses for a certain year
    public double sumExpensesYear(int year) {
        double sumYearExpenses = 0;
        List<Entries> allEntries = this.getAllEntriesFromUser(HomeActivity.getCurrentUserID());
        for (Entries e : allEntries) {
            if (e.getEntry_year() == year && e.getEntry_amount() < 0) {
                sumYearExpenses -= e.getEntry_amount();
                // TODO: getEntry_amount() scheint keinen double Wert zurück zu geben. Gleiches Problem wie bei der Liste auf der HomeActivity?
            }
        }
        return sumYearExpenses * -1;
    }

    // get sum of all income for a certain year
    public double sumIncomeYear(int year) {
        List<Entries> allEntries = this.getAllEntriesFromUser(HomeActivity.getCurrentUserID());
        double sumYearIncome = 0;
        for (Entries e : allEntries) {
            if (e.getEntry_year() == year && e.getEntry_amount() > 0) {
                sumYearIncome += e.getEntry_amount();
                // TODO: getEntry_amount() scheint keinen double Wert zurück zu geben. Gleiches Problem wie bei der Liste auf der HomeActivity?
            }
        }
        return sumYearIncome;
    }


    // get set of all years with expenses
    public ArrayList<Integer> yearsWithData() {
        Set<Integer> yearsWithDataSet = new LinkedHashSet<>();
        List<Entries> allEntries = this.getAllEntriesFromUser(HomeActivity.getCurrentUserID());

        for (Entries e : allEntries) {
            yearsWithDataSet.add(e.getEntry_year());
        }
        return new ArrayList<>(yearsWithDataSet);
    }

    // get sum of all expenses for a certain year
    public double sumExpensesMonth(int month, int year) {
        double sumMonthExpenses = 0;
        List<Entries> allEntries = this.getAllEntriesFromUser(HomeActivity.getCurrentUserID());
        for (Entries e : allEntries) {
            if (e.getEntry_month()-1 == month && e.getEntry_year() == year) {
                if (e.getEntry_amount() < 0) {
                    sumMonthExpenses -= e.getEntry_amount();
                }
            }
        }
        return sumMonthExpenses * -1;
    }
}
