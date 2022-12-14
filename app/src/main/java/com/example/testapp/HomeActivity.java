package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.testapp.databinding.ActivityHomeBinding;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // store the name of our class into a String
    public static final String LOG_TAG = HomeActivity.class.getSimpleName();

    // Reference to the EntriesDataSource-Object
    private EntriesDataSource dataSource;
    Button btn_addEntry;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*** Menubar ***/
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(this, HomeActivity.class));
                    break;
                case R.id.addEntry:
                    startActivity(new Intent(this, EntriesActivity.class));
                    break;
                case R.id.statistics:
                    startActivity(new Intent(this, StatisticsActivity.class));
                    break;
            }
            return true;
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