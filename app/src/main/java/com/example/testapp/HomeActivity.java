package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // store the name of our class into a String
    public static final String LOG_TAG = HomeActivity.class.getSimpleName();

    // Reference to the EntriesDataSource-Object
    private EntriesDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /****** START DATABASE *******/
        // the object calls the two constructors of the EntriesDataSource and
        // EntriesDbHelper classes.
        dataSource = new EntriesDataSource(this);

        Log.d(LOG_TAG, "The data source is opened.");
        dataSource.open();

        // Read Data of Database
        // TODO: this next line is just for Testing purposes and
        //  can be removed once the rest of the code is written
        Entries entries = dataSource.createEntries(1,2,2,10041998,"Beschreibung",10);
        Log.d(LOG_TAG, "The following entry was written to the database: ");
        Log.d(LOG_TAG, "ID: " + entries.getId() + ", Content: " + entries.toString());

        Log.d(LOG_TAG, "The following entries are available in the database: ");
        showAllListEntries();

        Log.d(LOG_TAG, "The data source is closed.");
        dataSource.close();

        /****** END DATABASE *******/

    }


    /*
    Method to put Entries onto Homescreen
     */
    private void showAllListEntries(){
        // Save DB Entries in List
        List<Entries> entriesList = dataSource.getAllShoppingMemos();

        // Create array adapter and pass the instance of the main activity as context
        // Pass a predefined standard layout as layout for the entries
        ArrayAdapter<Entries> entriesArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                entriesList);

        // Put Entries onto Homescreen
        ListView shoppingMemosListView = (ListView) findViewById(R.id.home_listview_entries);
        shoppingMemosListView.setAdapter(entriesArrayAdapter);
    }
}