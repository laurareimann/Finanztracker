package com.example.testapp;

/**
 * SOURCES:
 * MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", MenuBar: URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022
 **/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.testapp.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // store the name of our class into a String
    public static final String LOG_TAG = HomeActivity.class.getSimpleName();

    // Reference to the EntriesDataSource-Object
    private EntriesDataSource dataSource;
    BottomNavigationView bNV_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        /****** Database *******/
        // the object calls the two constructors of the EntriesDataSource and
        // EntriesDbHelper classes.
        Log.d(LOG_TAG, "The data source is opened.");
        dataSource = new EntriesDataSource(this);
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
        List<Entries> entriesList = dataSource.getAllEntries();

        // Create array adapter and pass the instance of the main activity as context
        // Pass a predefined standard layout as layout for the entries
        ArrayAdapter<Entries> entriesArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                entriesList);

        // Put Entries onto Homescreen
        ListView shoppingMemosListView = (ListView) findViewById(R.id.listview_home_entries);
        shoppingMemosListView.setAdapter(entriesArrayAdapter);
    }
}