package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.testapp.databinding.ActivityHomeBinding;
import com.example.testapp.databinding.ActivityStatisticsBinding;

public class StatisticsActivity extends AppCompatActivity {

    ActivityStatisticsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        /*** Menubar ***/
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationViewStatistics.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(this, HomeActivity.class));
                    break;
                case R.id.addEntry:
                    startActivity(new Intent(this, EntriesActivity.class));
                    break;
                case R.id.statistics:
                    startActivity(new Intent(this, StatisticsActivity.class));
                    break;
            }
            return true;
        });
    }
}