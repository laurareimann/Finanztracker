package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class EntriesActivity extends AppCompatActivity {

    // LOGTAG
    public static final String LOG_TAG = EntriesActivity.class.getSimpleName();

    private EntriesDataSource dataSource;
    Button btn_backHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        /****** START DATABASE *******/
        // the object calls the two constructors of the EntriesDataSource and
        // EntriesDbHelper classes.
        dataSource = new EntriesDataSource(this);

        Log.d(LOG_TAG, "The data source is opened.");
        dataSource.open();

        /*** Buttons ***/
        btn_backHome = (Button) findViewById(R.id.button_entries_home);

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        activateAddButton();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet");
        dataSource.open();

        Log.d(LOG_TAG, "Folgene Einträge sind in der Datenbank vorhanden: ");
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    private void activateAddButton(){
        // Referenzen der Widget-Objekte
        Button buttonAddProduct = (Button) findViewById(R.id.button_entries_add);
        final EditText editTextAmount = (EditText) findViewById(R.id.editxt_entries_amount);
        final EditText editTextNotice = (EditText) findViewById(R.id.editxt_entries_notice);
        final EditText editTextKategory = (EditText) findViewById(R.id.editxt_entries_kategory);

        // OnClickListener erstellen
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Inhalt der Textfelder auslesen
                String amountString = editTextAmount.getText().toString();
                String notice = editTextNotice.getText().toString();
                String kategoryString = editTextKategory.getText().toString();

                // Falls Eingabefelder leer, gib eine Errornachricht zurück
                if(TextUtils.isEmpty(amountString)){
                    editTextAmount.setError("Ausgabe darf nicht leer sein");
                    return;
                }
                if(TextUtils.isEmpty(notice)){
                    editTextNotice.setError("Notiz darf nicht leer sein");
                    return;
                }
                if(TextUtils.isEmpty(kategoryString)){
                    editTextKategory.setError("Kategorie darf nicht leer sein");
                    return;
                }

                // Speicher die Quantity als Integer statt als String
                int amount = Integer.parseInt(amountString);
                //int kategory = Integer.parseInt(kategoryString);

                // Textfelder leeren
                editTextAmount.setText("");
                editTextNotice.setText("");
                editTextKategory.setText("");

                // der DB übergeben
                dataSource.createEntries(111,0, amount, 8112022, notice,1);

                // Tastatur ausblenden
                InputMethodManager inputMethodManager;
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                }

            }
        });
    }

}