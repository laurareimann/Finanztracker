package com.example.testapp;
/**
 * SOURCES:
 * BarChart: CodingMark: "Android Studio: Create a Bar Chart using SqLite", URL: https://www.youtube.com/watch?v=niLkRACZEMg, 16.12.2022
 * Wilson Yeung: "Using MPAndroidChart for Android Application — BarChart", URL: https://medium.com/@clyeung0714/using-mpandroidchart-for-android-application-barchart-540a55b4b9ef, 31.12.2022
 * MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", MenuBar: URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022
 **/

import static com.example.testapp.R.id.barchart_statistics_months;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {

    Button btn_months, btn_years;
    BottomNavigationView bNV_statistics;
    private BarChart barChart_statistics_monthOverview, barChart_statistics_yearOverview;
    private EntriesDataSource dataSource;
    private TextView balance;

    private DB_user dbUser;
    private String currentUser;

    private int currentUserID = HomeActivity.getCurrentUserID();

    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        dataSource = new EntriesDataSource(this);
        dbUser = new DB_user(this);
        currentUser = LoginActivity.currentUsername;


        balance = findViewById(R.id.txt_statistics_balance);
        balance.setText(dbUser.getUserBalance(currentUser) + " €");


        /** Buttons **/
        // Switch between Month and Year Graph
        btn_months = findViewById(R.id.btn_statistics_months);
        btn_years = findViewById(R.id.btn_statistics_years);
        barChart_statistics_monthOverview = (BarChart) findViewById(R.id.barchart_statistics_months);
        barChart_statistics_yearOverview = (BarChart) findViewById(R.id.barchart_statistics_years);

        btn_months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_years.setSelected(false);
                initBarChart(barChart_statistics_monthOverview);
                barChart_statistics_yearOverview.setVisibility(View.INVISIBLE);
                barChart_statistics_monthOverview.setVisibility(View.VISIBLE);
                showBarChartMonths();
            }
        });

        btn_years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_months.setSelected(false);
                initBarChart(barChart_statistics_yearOverview);
                barChart_statistics_monthOverview.setVisibility(View.INVISIBLE);
                barChart_statistics_yearOverview.setVisibility(View.VISIBLE);
                showBarChartYears();
            }
        });

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

    // Formattierung:
    private void initBarDataSet(BarDataSet barDataSet) { //TODO: muss noch eingestellt werden und aufgerufen wernden
        //Changing the color of the bar
        barDataSet.setColor(Color.parseColor("#0099FF"));
        //barDataSet.setColor(Color.parseColor(String.valueOf(ContextCompat.getColor(this, R.color.blue_primary))));
        //TODO: Farbe aufrufen mit ContextComat funktioniert noch nicht, ist aber erstmal nicht wichtig

        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
    }

    // TODO: anpassen an Designvorlage
    private void initBarChart(BarChart barChart) {
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart.animateY(500);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart.animateX(500);

        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barChart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);
    }

    // Jahresübersicht:
    private void showBarChartYears() {
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Integer> yearsWithData = dataSource.yearsWithData();
        System.out.println("yearsWithData in sA: "+yearsWithData);
        String title = "Jahre";

        //input data
        for (int i = 0; i < yearsWithData.size(); i++) {
            valueList.add(dataSource.sumExpensesYear(yearsWithData.get(i)));
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(yearsWithData.get(i), valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        // Formattierung:
        initBarDataSet(barDataSet);
        BarData data = new BarData(barDataSet);

        barChart_statistics_yearOverview.setData(data);
        barChart_statistics_yearOverview.invalidate();
    }

    // Monatsübersicht:
    private void showBarChartMonths() {
        ArrayList<Integer> valueList = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mär");
        months.add("Apr");
        months.add("Mai");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Okt");
        months.add("Nov");
        months.add("Dez");

        String title = "Monate";

        //input data
        for (int i = 0; i < 12; i++) {
            valueList.add(dataSource.sumExpensesMonth(i, currentYear));
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue()); // floatValue kann auch gelöscht werden
            entries.add(barEntry);
            //TODO: add months as String
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        // Formattierung:
        initBarDataSet(barDataSet);

        BarData data = new BarData(barDataSet);
        barChart_statistics_monthOverview.setData(data);
        barChart_statistics_monthOverview.invalidate();
    }
}
