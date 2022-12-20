package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, password_rep, acc_balance;
    Button btn_register, btn_login;
    DB_user db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // declare variables with input from frontend
        username = (EditText)findViewById(R.id.txt_register_username);
        password = (EditText) findViewById(R.id.txt_register_password);
        // TODO: edittext View mit Balance verknüpfen
        acc_balance = (EditText) findViewById(R.id.txt_register_balance);
        password_rep = (EditText) findViewById(R.id.txt_register_password_rep);

        btn_register = (Button) findViewById(R.id.btn_register_register);
        btn_login = (Button) findViewById(R.id.btn_register_login);

        db = new DB_user(this);

        // Functionality of Register Button
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String pass_rep = password_rep.getText().toString();
                // TODO: get Balace und in einem float speichern
                float balance = Float.parseFloat(acc_balance.getText().toString());
                System.out.println(balance);
                String str_balance = acc_balance.getText().toString();

                if (user.equals("")|| pass.equals("")|| pass_rep.equals("") || str_balance.equals("")){
                    Toast.makeText(RegisterActivity.this, "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(pass_rep)){
                        Boolean userCheckResult = db.checkUsername(user);
                        if(userCheckResult == false){
                            // TODO: Balance der DB zufügen
                            //Boolean regResult = db.insertData(user, pass, balance);
                            Boolean regResult = db.insertData(user, pass, balance);
                            if (regResult == true){
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
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}