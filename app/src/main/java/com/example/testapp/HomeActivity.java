package com.example.testapp;

/**
 * SOURCES:
 * Database: CodeYourApp: "Spezialkurs: SQLite Datenbank App Programmieren (ab API Version 29)", URL: https://www.codeyourapp.de/sqlite-app-programmieren-kurs/, 09.01.2023
 * MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022
 **/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testapp.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    Button btn_logout;

    // store the name of our class into a String
    public static final String LOG_TAG = HomeActivity.class.getSimpleName();

    // Reference to the EntriesDataSource-Object
    private EntriesDataSource dataSource;
    private DB_user dbUser;
    private TextView balance;
    BottomNavigationView bNV_home;
    private String currentUser;
    private static int currentUserID;
    boolean checker = false;
    DB_user db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_logout = (Button) findViewById(R.id.btn_home_logout);

        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class); // TODO: später neu verlinken zur Register Activity
                startActivity(intent);
            }
        });

        /*** Nagivation Bar ***/
        // Initialize and assign variable
        bNV_home = findViewById(R.id.bottomNav_home);

        // Set Home selected
        bNV_home.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bNV_home.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.addEntry:
                        startActivity(new Intent(getApplicationContext(), EntriesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.statistics:
                        startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        /*** Databases ***/
        // the object calls the two constructors of the EntriesDataSource and
        // EntriesDbHelper classes.
        Log.d(LOG_TAG, "The data source is opened.");
        dataSource = new EntriesDataSource(this);
        dbUser = new DB_user(this);

        /*** Views ***/
        currentUser = LoginActivity.currentUsername;
        currentUserID = dbUser.getUserID(currentUser);
        balance = findViewById(R.id.text_home_balance);
        // Kontostand mit Dezimal Punkt:
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat dform = (DecimalFormat)nf;
        double balanceAsDouble = Double.parseDouble(dbUser.getUserBalance(currentUser));
        balance.setText(dform.format(balanceAsDouble) + " €");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet");
        dataSource.open();

        Log.d(LOG_TAG, "Folgene Einträge sind in der Datenbank vorhanden: ");
        showAllListEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }


    /*** Method to put Entries onto Homescreen ***/
    private void showAllListEntries() {
        // Save DB Entries in List
        List<Entries> entriesList = dataSource.getAllEntriesFromUser(currentUserID);
        List<Entries> shallowCopy = entriesList.subList(0, entriesList.size());
        Collections.reverse(shallowCopy);

        // Create array adapter and pass the instance of the main activity as context
        // Pass a predefined standard layout as layout for the entries
        ArrayAdapter<Entries> entriesArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                shallowCopy);

        // Put Entries onto Homescreen
        ListView entriesListView = (ListView) findViewById(R.id.listview_home_entries);

        entriesListView.setAdapter(entriesArrayAdapter);
    }

    // save the name of current User
    public static Integer getCurrentUserID(){
        return currentUserID;
    }

}