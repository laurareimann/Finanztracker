package com.example.earny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.testapp.R;

public class MainActivity extends AppCompatActivity {
    private Button btn_register, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declare variables with input from frontend
        btn_register = (Button) findViewById(R.id.btn_main_register);
        btn_login = (Button) findViewById(R.id.btn_main_login);

        // Functionality of Register Button
        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}