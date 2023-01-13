package com.example.testapp;

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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public double getEntry_amount() {
        return entry_amount;
    }

    public void setEntry_amount(double entry_amount) {
        this.entry_amount = entry_amount;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getEntry_notice() {
        return entry_notice;
    }

    public void setEntry_notice(String entry_notice) {
        this.entry_notice = entry_notice;
    }

    public int getEntry_day() {
        return entry_day;
    }

    public void setEntry_day(int entry_day) {
        this.entry_day = entry_day;
    }

    public int getEntry_month() {
        return entry_month;
    }

    public void setEntry_month(int entry_month) {
        this.entry_month = entry_month;
    }

    public int getEntry_year() {
        return entry_year;
    }

    public void setEntry_year(int entry_year) {
        this.entry_year = entry_year;
    }

    // Todo Ausgabe im Home screen fixen: Hier wird nicht der richtige Wert aus der Datenbank gelesen
    @Override
    public String toString() {
        Log.d( "Entries","Method: toString: " + String.valueOf(entry_amount));
        return "Einnahme/Ausgabe: " + entry_amount +
                " € , Datum: " + entry_date +
                ", Notiz: " + entry_notice;

    }
}
