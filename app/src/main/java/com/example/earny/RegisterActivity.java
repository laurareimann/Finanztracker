package com.example.earny;

/**
 * SOURCES
 * Database: BTech Days: "Login and Register Form using SQLite Database in Android Studio | Login Registration Android Studio", URL: https://www.youtube.com/watch?v=o9CVZ1gQgQo, 09.01.2023
 **/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.R;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, password_rep, acc_balance;
    Button btn_register, btn_login;
    DB_user db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // declare variables with input from frontend
        username = (EditText) findViewById(R.id.txt_register_username);
        password = (EditText) findViewById(R.id.txt_register_password);
        acc_balance = (EditText) findViewById(R.id.txt_register_balance);
        password_rep = (EditText) findViewById(R.id.txt_register_password_rep);

        btn_register = (Button) findViewById(R.id.btn_register_register);
        btn_login = (Button) findViewById(R.id.btn_register_login);

        db = new DB_user(this);

        // Functionality of Register Button
        btn_register.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String pass_rep = password_rep.getText().toString();

            String str_balance = acc_balance.getText().toString();

            if (user.equals("") || pass.equals("") || pass_rep.equals("") || str_balance.equals("")) {
                Toast.makeText(RegisterActivity.this, "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show();
            } else {
                if (pass.equals(pass_rep)) {
                    Boolean userCheckResult = db.checkUsername(user);
                    if (!userCheckResult) {
                        float balance = Float.parseFloat(acc_balance.getText().toString());
                        Boolean regResult = db.insertData(user, pass, balance);
                        if (regResult) {
                            Toast.makeText(RegisterActivity.this, "Registrierung erfolgreich.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registrierung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Benutzername existiert bereits.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Die Passwörter stimmen nicht überein.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

    }
}