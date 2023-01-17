package com.example.earny;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * SOURCES
 * Database: CodeYourApp: "Spezialkurs: SQLite Datenbank App Programmieren (ab API Version 29)", URL: https://www.codeyourapp.de/sqlite-app-programmieren-kurs/, 09.01.2023
 **/

public class Entries {

    private long id;
    private final long user_id;
    private final double entry_amount;
    private final String entry_date;
    private final String entry_notice;
    private final int entry_day;
    private final int entry_month;
    private final int entry_year;

    public Entries(long id, long user_id, double entry_amount, String entry_notice,
                   String entry_date, int entry_day, int entry_month, int entry_year) {
        this.id = id;
        this.user_id = user_id;
        this.entry_amount = entry_amount;
        this.entry_date = entry_date;
        this.entry_notice = entry_notice;
        this.entry_day = entry_day;
        this.entry_month = entry_month;
        this.entry_year = entry_year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEntry_amount() {
        return entry_amount;
    }

    public int getEntry_month() { return entry_month; }

    public int getEntry_year() {
        return entry_year;
    }

    @NonNull
    @Override
    public String toString() {
        Log.d("Entries", "Method: toString: " + entry_amount);

        if (entry_amount > 0) {
            return "Einnahme: " + entry_amount +
                    " € \nDatum: " + entry_date +
                    "\t\t\tNotiz: " + entry_notice;
        } else {
            return "Ausgabe: " + entry_amount +
                    " € \nDatum: " + entry_date +
                    "\t\t\tNotiz: " + entry_notice;
        }
    }
}
