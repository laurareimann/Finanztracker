package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username = (TextView) findViewById(R.id.txt_login_username);
        TextView password = (TextView) findViewById(R.id.txt_login_password);

        MaterialButton loginbtn = (MaterialButton)  findViewById(R.id.btn_login_login);

        // admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    // correct
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else{
                    // incorrect
                    Toast.makeText(LoginActivity.this, "Login failed !!!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}