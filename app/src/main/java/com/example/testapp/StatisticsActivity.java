package com.example.testapp;
/**
 * SOURCES:
 * BarChart: CodingMark: "Android Studio: Create a Bar Chart using SqLite", URL: https://www.youtube.com/watch?v=niLkRACZEMg, 16.12.2022
 * MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", MenuBar: URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022
 **/

import static com.example.testapp.R.id.barchart_statistics_months;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapp.data.Database;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class StatisticsActivity extends AppCompatActivity {

    BottomNavigationView bNV_statistics;
    private BarChart barChart_statistics_monthOverview;
    private BarChart barChart_statistics_yearOverview;
    private EntriesDataSource dataSource;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        dataSource = new EntriesDataSource(this);

        /** BarChart **/
        barChart_statistics_monthOverview = (BarChart) findViewById(R.id.barchart_statistics_months);
        barChart_statistics_yearOverview = (BarChart) findViewById(R.id.barchart_statistics_years);

        showBarChartYears();
        showBarChartMonths();

        barChart_statistics_monthOverview.invalidate(); // TODO: kann weg?


        /*** Menubar ***/
        // Initialize and assign variable
        bNV_statistics = findViewById(R.id.bottomNav_statistics);

        // Set Home selected
        bNV_statistics.setSelectedItemId(R.id.statistics);

        // Perform item selected listener
        bNV_statistics.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.addEntry:
                        startActivity(new Intent(getApplicationContext(), EntriesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.statistics:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataSource.open();
    }
    @Override
    protected void onPause() {
        super.onPause();

        dataSource.close();
    }


    /**
     * Methoden für BarChart Database
     **/

    public void formatGraph() {
        // Zur Formattierung:
        XAxis xAxis = barChart_statistics_monthOverview.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawLabels(true);
        xAxis.isCenterAxisLabelsEnabled();
        xAxis.setGranularityEnabled(true);

        YAxis yAxis = barChart_statistics_monthOverview.getAxisRight();
        yAxis.setEnabled(false);

        barChart_statistics_monthOverview.setMaxVisibleValueCount(5);
        barChart_statistics_monthOverview.setFitBars(true);
    }

    private void showBarChartYears(){
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Integer> yearsWithData = dataSource.yearsWithData();
        String title = "Jahre";

        //input data
        for(int i = 0; i < yearsWithData.size(); i++){
            valueList.add(dataSource.sumExpensesYear(yearsWithData.get(i)));
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(yearsWithData.get(i), valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);

        BarData data = new BarData(barDataSet);
        barChart_statistics_yearOverview.setData(data);
        barChart_statistics_yearOverview.invalidate();
    }
    private void showBarChartMonths(){
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Monate";

        //input data
        for(int i = 0; i < 12; i++){
            valueList.add(dataSource.sumExpensesMonth(i,2022));
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        BarData data = new BarData(barDataSet);
        barChart_statistics_monthOverview.setData(data);
        barChart_statistics_monthOverview.invalidate();
    }
}
