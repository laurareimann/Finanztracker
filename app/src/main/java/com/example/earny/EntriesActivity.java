package com.example.earny;

/**
 * SOURCES:
 * MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", MenuBar: URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022
 **/

import static com.example.testapp.R.*;
import static com.example.testapp.R.id.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class EntriesActivity extends AppCompatActivity {

    // LOGTAG
    public static final String LOG_TAG = EntriesActivity.class.getSimpleName();
    private static final String TAG = "EntriesActivity";

    private EntriesDataSource dataSource;
    private DB_user dbUser;
    BottomNavigationView bNV_entries;
    private String currentUser;
    private int currentUserID;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Switch einAusgabeSwitch;
    private TextView switchText;
    public static boolean checker = false;
    int yearDB;
    int monthDB;
    int dayDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_entries);
        mDisplayDate = (TextView) findViewById(txt_entries_date);


        /*** Menubar ***/
        // Initialize and assign variable
        bNV_entries = findViewById(bottomNav_entries);

        // Set Home selected
        bNV_entries.setSelectedItemId(addEntry);

        // Perform item selected listener
        bNV_entries.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
            }

            if (item.getItemId() == statistics) {
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                overridePendingTransition(0, 0);
            }
            return false;
        });

        /*** DATABASE ***/
        // the object calls the two constructors of the EntriesDataSource and
        // EntriesDbHelper classes.
        dataSource = new EntriesDataSource(this);
        dbUser = new DB_user(this);

        Log.d(LOG_TAG, "The data source is opened.");
        dataSource.open();


        /*** Views ***/
        activateAddButton();
        currentUser = LoginActivity.currentUsername;
        currentUserID = dbUser.getUserID(currentUser);


        /*** Date ***/
        mDisplayDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    EntriesActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
            //shows date in entry
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        });
        mDateSetListener = (datePicker, year, month, day) -> {
            // January would be month 0 und December would be month 11 so:
            month = month + 1;
            if (month < 10) {
                Log.d(TAG, "ondateSet: dd.mm.yyy: " + day + ".0" + month + "." + year);
                String date = day + ".0" + month + "." + year;
                mDisplayDate.setText(date);
            } else {
                Log.d(TAG, "ondateSet: dd.mm.yyy: " + day + "." + month + "." + year);
                String date = day + "." + month + "." + year;
                mDisplayDate.setText(date);
            }

            mDisplayDate.setTextColor(getResources().getColor(color.black));

            // save date as ints
            dayDB = day;
            monthDB = month;
            yearDB = year;
        };
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet");
        dataSource.open();

        Log.d(LOG_TAG, "Folgene Einträge sind in der Datenbank vorhanden: ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }


    private void activateAddButton() {
        // References Widget-Objects
        Button buttonAddProduct = (Button) findViewById(btn_entries_addentry);
        final EditText editTextAmount = (EditText) findViewById(txt_entries_amount);
        final EditText editTextNotice = (EditText) findViewById(txt_entries_notice);
        final TextView textViewDate = (TextView) findViewById(txt_entries_date);

        switchText = (TextView) findViewById(txt_entries_switchText);
        einAusgabeSwitch = (Switch) findViewById(switch_entries_incomeOrExpense);


        /*** Switch ***/
        einAusgabeSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                checker = true;
                switchText.setText(string.h_income2);
                switchText.setTextColor(Color.parseColor("#7fff00"));
                editTextAmount.setHint("Neue Einnahme");

            } else {
                checker = false;
                switchText.setText(string.h_expense);
                switchText.setTextColor(Color.parseColor("#ff0000"));
                editTextAmount.setHint("Neue Ausgabe");
            }

        });

        // OnClickListener
        buttonAddProduct.setOnClickListener(view -> {

            // get content of EditTextViews
            String amountString = editTextAmount.getText().toString();
            String notice = editTextNotice.getText().toString();
            String date = textViewDate.getText().toString();


            // In Case fields are empty
            if (TextUtils.isEmpty(amountString)) {
                editTextAmount.setError("Ausgabe darf nicht leer sein");
                return;
            }
            if (TextUtils.isEmpty(notice)) {
                editTextNotice.setError("Notiz darf nicht leer sein");
                return;
            }
            if (TextUtils.isEmpty(date)) {
                textViewDate.setError("Datum darf nicht leer sein");
                return;
            }

            Toast.makeText(EntriesActivity.this, "Eintrag wurde gespeichert", Toast.LENGTH_SHORT).show();

            // Cast amount to string
            //int amount = Integer.parseInt(amountString);
            double amount = Double.parseDouble((amountString));


            // clear EditTextViews
            editTextAmount.setText("");
            editTextNotice.setText("");
            textViewDate.setText("");


            // create new row in DB
            // Constructor: userID, amount, notice, String date, day, month, year
            if (!einAusgabeSwitch.isChecked()) {
                amount *= -1;
            }

            // Format Entry to have only 2 Decimals (#.##)
            // Create the DecimalFormat Instance
            DecimalFormat deciFormat = new DecimalFormat("#.##");
            // Set the rounding mode as CEILING
            deciFormat.setRoundingMode(RoundingMode.CEILING);
            amount = Double.parseDouble(deciFormat.format(amount));

            dataSource.createEntries(currentUserID, amount, notice, date, dayDB, monthDB, yearDB);

            dbUser.updateBalance(amount, currentUser);
            // Hide Keyboard
            InputMethodManager inputMethodManager;
            inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }


}