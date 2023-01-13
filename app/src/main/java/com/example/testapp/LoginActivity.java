package com.example.testapp;

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

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btn_register, btn_login;
    DB_user db;
    public static String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize variables
        username = (EditText) findViewById(R.id.txt_login_username);
        password = (EditText) findViewById(R.id.txt_login_password);

        //MaterialButton loginbtn = (MaterialButton) findViewById(R.id.btn_login_login);
        btn_login = (Button) findViewById(R.id.btn_login_login);
        btn_register = (Button) findViewById(R.id.btn_login_register);
        db = new DB_user(this);

        // Login
        btn_login.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            setCurrentUser();

            // Check if fields are empty
            if (user.equals("") || pass.equals("")) {
                Toast.makeText(LoginActivity.this, "Bitte fülle alle Felder aus.", Toast.LENGTH_SHORT).show();
            } else {
                //check if user is in Database
                if (db.checkUsernamePassword(user, pass)) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Passwort oder Nutzername falsch.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button zur Registrierung
        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class); // TODO: später neu verlinken zur Register Activity
            startActivity(intent);
        });
    }

    // save the name of current User
    public void setCurrentUser() {
        currentUsername = username.getText().toString();
    }
}