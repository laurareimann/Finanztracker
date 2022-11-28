package com.example.testapp;

public class Entries {

    private long id;
    private long user_id;
    private int entry_value;
    private int entry_amount;
    private int entry_date;
    private String entry_notice;
    /*private int entry_repetition;*/
    private int cat_id;

    public Entries(long id, long user_id, int entry_value, int entry_amount,
                   int entry_date, String entry_notice, int cat_id) {
        this.id = id;
        this.user_id = user_id;
        this.entry_value = entry_value;
        this.entry_amount = entry_amount;
        this.entry_date = entry_date;
        this.entry_notice = entry_notice;
        this.cat_id = cat_id;
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

    public int getEntry_value() {
        return entry_value;
    }

    public void setEntry_value(int entry_value) {
        this.entry_value = entry_value;
    }

    public int getEntry_amount() {
        return entry_amount;
    }

    public void setEntry_amount(int entry_amount) {
        this.entry_amount = entry_amount;
    }

    public int getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(int entry_date) {
        this.entry_date = entry_date;
    }

    public String getEntry_notice() {
        return entry_notice;
    }

    public void setEntry_notice(String entry_notice) {
        this.entry_notice = entry_notice;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    @Override
    public String toString() {
        return "Ausgaben: " + entry_amount +
                "€ , Datum: " + entry_date +
                ", Notiz: " + entry_notice;
    }
}
