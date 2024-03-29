package com.example.earny;

/**
 * SOURCES:
 * Database: CodeYourApp: "Spezialkurs: SQLite Datenbank App Programmieren (ab API Version 29)", URL: https://www.codeyourapp.de/sqlite-app-programmieren-kurs/, 09.01.2023
 * MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022
 **/

import static com.example.testapp.R.id.addEntry;
import static com.example.testapp.R.id.statistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    // store the name of our class into a String
    public static final String LOG_TAG = HomeActivity.class.getSimpleName();

    // Reference to the EntriesDataSource-Object
    private EntriesDataSource dataSource;
    private static int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /*** Nagivation Bar ***/
        // Initialize and assign variable
        BottomNavigationView bNV_home = findViewById(R.id.bottomNav_home);

        // Set Home selected
        bNV_home.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bNV_home.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == addEntry) {
                startActivity(new Intent(getApplicationContext(), EntriesActivity.class));
                overridePendingTransition(0, 0);
            }

            if (item.getItemId() == statistics) {
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                overridePendingTransition(0, 0);
            }
            return false;
        });

        /*** Databases ***/
        Log.d(LOG_TAG, "The data source is opened.");
        dataSource = new EntriesDataSource(this);
        UserDbHelper dbUser = new UserDbHelper(this);

        /*** Views & Listeners***/
        activateLogoutButton();
        TextView balance = findViewById(R.id.txt_home_balance);

        /*** User Data ***/
        String currentUser = LoginActivity.currentUsername;
        currentUserID = dbUser.getUserID(currentUser);

        /*** Formatting ***/
        // show balance with decimal point
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat dform = (DecimalFormat) nf;
        double balanceAsDouble = Double.parseDouble(dbUser.getUserBalance(currentUser));
        balance.setText(getString(R.string.eurosign, dform.format(balanceAsDouble)));
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

    private void activateLogoutButton(){
        Button btn_logout = (Button) findViewById(R.id.btn_home_logout);

        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class); // TODO: später neu verlinken zur Register Activity
            startActivity(intent);
        });
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
    public static Integer getCurrentUserID() {
        return currentUserID;
    }

}