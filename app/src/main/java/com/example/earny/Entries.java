package com.example.earny;

import android.util.Log;

/**
 * SOURCES
 * Database: CodeYourApp: "Spezialkurs: SQLite Datenbank App Programmieren (ab API Version 29)", URL: https://www.codeyourapp.de/sqlite-app-programmieren-kurs/, 09.01.2023
 **/

public class Entries {

    private long id;
    private long user_id;
    private double entry_amount;
    private String entry_date;
    private String entry_notice;
    private int entry_day;
    private int entry_month;
    private int entry_year;

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

    @Override
    public String toString() {
        // Todo Ausgabe im Home screen fixen: Hier wird nicht der richtige Wert aus der Datenbank gelesen
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
