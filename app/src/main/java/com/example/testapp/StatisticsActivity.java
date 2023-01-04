package com.example.testapp;
/**  SOURCES: **/
// BarChart: CodingMark: "Android Studio: Create a Bar Chart using SqLite", URL: https://www.youtube.com/watch?v=niLkRACZEMg, 16.12.2022
// Wilson Yeung: "Using MPAndroidChart for Android Application — BarChart", URL: https://medium.com/@clyeung0714/using-mpandroidchart-for-android-application-barchart-540a55b4b9ef, 31.12.2022
// MenuBar: jangirkaran17: "How to Implement Bottom Navigation With Activities in Android?", MenuBar: URL: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/, 19.12.2022

/**  IMPORTS: **/
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    Button btn_months, btn_years;
    BottomNavigationView bNV_statistics;
    private BarChart barChart_statistics_monthOverview, barChart_statistics_yearOverview;
    private EntriesDataSource dataSource;

    private DB_user dbUser;
    private String currentUser;

    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);

    private final List<String> months = Arrays.asList("Januar","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        dataSource = new EntriesDataSource(this);
        dbUser = new DB_user(this);
        currentUser = LoginActivity.currentUsername;


        /** Buttons **/
        // Switch between Month and Year Graph
        btn_months = findViewById(R.id.btn_statistics_months);
        btn_years = findViewById(R.id.btn_statistics_years);
        barChart_statistics_monthOverview = findViewById(R.id.barchart_statistics_months);
        barChart_statistics_yearOverview = findViewById(R.id.barchart_statistics_years);

        btn_months.setOnClickListener(view -> {
            btn_years.setSelected(false);
            initBarChart(barChart_statistics_monthOverview);
            barChart_statistics_yearOverview.setVisibility(View.INVISIBLE);
            barChart_statistics_monthOverview.setVisibility(View.VISIBLE);
            showBarChartMonths();
        });

        btn_years.setOnClickListener(view -> {
            btn_months.setSelected(false);
            initBarChart(barChart_statistics_yearOverview);
            barChart_statistics_monthOverview.setVisibility(View.INVISIBLE);
            barChart_statistics_yearOverview.setVisibility(View.VISIBLE);
            showBarChartYears();
        });

        /*** Menubar ***/
        // Initialize and assign variable
        bNV_statistics = findViewById(R.id.bottomNav_statistics);

        // Set Home selected
        bNV_statistics.setSelectedItemId(R.id.statistics);

        // Perform item selected listener
        bNV_statistics.setOnNavigationItemSelectedListener(item -> {

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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Aktuelle Werte aus der Datenbank abrufen:
        dataSource.open();
        initBarChart(barChart_statistics_monthOverview);
        showBarChartMonths();
        TextView balance = findViewById(R.id.txt_statistics_balance);
        balance.setText(dbUser.getUserBalance(currentUser) + " €");

        TextView month = findViewById(R.id.txt_statistics_month);
        month.setText(months.get(currentMonth));

        TextView expensesMonth = findViewById(R.id.txt_statistics_expenses);
        expensesMonth.setText(dataSource.sumExpensesMonth(currentMonth+1,currentYear) + " €");

        TextView year = findViewById(R.id.txt_h_year);
        year.setText(String.valueOf(currentYear));

        TextView income = findViewById(R.id.txt_statistics_incomeyear);
        income.setText(dataSource.sumIncomeYear(currentYear) + " €");

        TextView expenses = findViewById(R.id.txt_statistics_expensesyear);
        expenses.setText(dataSource.sumExpensesYear(currentYear)  + " €");
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }


    /** Methoden für BarChart Database **/

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

        // TODO: no data Text krieg ich nicht weg
        barChart.setNoDataText("whatever");

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart.animateY(500);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart.animateX(500);

        //change the position of x-axis to the bottom
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //set the horizontal distance of the grid line
        //xAxis.setGranularity(1f);

        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);

        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        //hiding the left y-axis line, default true if not set
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);

        //hiding the right y-axis line, default true if not set
        YAxis rightAxis = barChart.getAxisRight();
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
        ArrayList<Integer> valueList = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Integer> yearsWithData = dataSource.yearsWithData();
        String title = "Jahre";

        //input data
        for (int i = 0; i < yearsWithData.size(); i++) {
            valueList.add((dataSource.sumExpensesYear(yearsWithData.get(i)))*-1);
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

        String title = "Monate";

        //input data
        for (int i = 0; i < 12; i++) {
            valueList.add((dataSource.sumExpensesMonth(i, currentYear))*-1);
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue()); // floatValue kann auch gelöscht werden
            entries.add(barEntry);
            //TODO: show months as String
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        // Formattierung:
        initBarDataSet(barDataSet);
        initBarChart(barChart_statistics_monthOverview);
        BarData data = new BarData(barDataSet);
        barChart_statistics_monthOverview.setData(data);
        barChart_statistics_monthOverview.invalidate();
    }
}