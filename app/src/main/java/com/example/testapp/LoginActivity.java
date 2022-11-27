package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    // variables
    EditText username, password;
    Button btn_register, btn_login;
    DB_user db;

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
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                // Check if fields are empty
                if(user.equals(null)|| pass.equals(null)){
                    Toast.makeText(LoginActivity.this, "Bitte fülle alle Felder aus.",Toast.LENGTH_SHORT).show();
                } else{
                    //check if user is in Database
                    if(db.checkUsernamePassword(user,pass)){
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(LoginActivity.this, "Passwort oder Nutzername falsch.",Toast.LENGTH_SHORT).show();
                    }
                }






            }
        });

        // Button zur Registrierung
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class); // TODO: später neu verlinken zur Register Activity
                startActivity(intent);
            }
        });
    }
}