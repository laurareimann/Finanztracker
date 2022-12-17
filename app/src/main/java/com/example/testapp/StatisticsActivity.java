package com.example.testapp;
/**
 * SOURCES:
 * BarChart: CodingMark: "Android Studio: Create a Bar Chart using SqLite", URL: https://www.youtube.com/watch?v=niLkRACZEMg, 16.12.2022
 * MenuBar:
 **/

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapp.data.Database;
import com.example.testapp.databinding.ActivityStatisticsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    ActivityStatisticsBinding binding;

    private BarChart barChart_statistics_monthOverview;
    private Button button;
    private EditText editText;
    private Database db;
    long date = System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        /** BarChart **/

        barChart_statistics_monthOverview = (BarChart) findViewById(R.id.barchart_statistics);
        button = (Button) findViewById(R.id.button_statistics);
        editText = (EditText) findViewById(R.id.editText_statistics);

        addDataToGraph();
        barChart_statistics_monthOverview.invalidate();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click detectet"); // TODO: Kommentare nach Debugging löschen
                SaveToDatabase();
            }
        });


        /*** Menubar ***/
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

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


    /**
     * Methoden für BarChart Database
     **/

    public void SaveToDatabase() {
        db = new Database(this);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd mm yyyy");
        String xValue = sdf.format(date);
        String yValue = editText.getText().toString();

        db.saveData(xValue, yValue);
        addDataToGraph(); // Graph wird refreshed, wenn neue Daten gespeichert werden
        barChart_statistics_monthOverview.invalidate();
        db.close();
    }

    public void addDataToGraph() {
        db = new Database(this);

        final ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        final ArrayList<String> yData = db.queryYData();

        for (int i = 0; i < db.queryYData().size(); i++) {
            BarEntry newBarEntry = new BarEntry(i, Float.parseFloat(db.queryYData().get(i)));
            yVals.add(newBarEntry);
        }

        final ArrayList<String> xVals = new ArrayList<String>();
        final ArrayList<String> xData = db.queryXData();

        for (int i = 0; i < db.queryXData().size(); i++) {
            xVals.add(xData.get(i));
        }

        BarDataSet dataSet = new BarDataSet(yVals, "Überblick Monat");
        ArrayList<IBarDataSet> dataSetsList = new ArrayList<>();
        dataSetsList.add(dataSet);
        BarData data = new BarData(dataSetsList);

        barChart_statistics_monthOverview.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
        barChart_statistics_monthOverview.setData(data);


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


}