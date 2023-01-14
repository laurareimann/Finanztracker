package espressoTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.earny.MainActivity;
import com.example.testapp.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class EarnyTest {

    // TODO: test Statistics with random System time
    // TODO: add multiple entries for one users to check database mathmatics

    // User Variables
    // Generate random UserName with Timestamp
    String currentTimestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
    private final String testUserName = "newUser" + currentTimestamp;
    private final String testUserPassword = "Password";

    // Generate random UserBalance
    // TODO: BUG!! aktuell kann nur ein Wert <10k richtig gespeichert werden
    private double testUserBalance = generateRandomBalance();

    // Entry Variables
    private double EntryAmount = generateRandomEntryAmount(); // Genereates double with 2 decimal places between 0 and 150
    private final String EntryNotice = "new Entry";
    private String BalanceGER;
    private Date EntryDate = generateRandomEntryDate(); // Generates random Date between five years ago and five years from now
    private int year = EntryDate.getYear();
    private int month = EntryDate.getMonth();
    private int day = EntryDate.getDay();

    // Statistics Variables
    private final List<String> months = Arrays.asList("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember");
    private final String currentMonth = (months.get(Calendar.getInstance().get(Calendar.MONTH)));
    private final String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private double currentMonthExpenses = 0;
    private double currentYearExpenses = 0;
    private double currentYearIncome = 0;

    String pattern = "MMddyyyy";
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    // formatting
    private final String Date = format.format(new Date());

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void EarnyEspressoTest() {
        // Register
        registerAccountForTest();

        // Login
        loginWithTestUser();

        // Check Balance
        String testUserBalanceGER = getGermanNumberFormat(testUserBalance);
        onView(withId(R.id.txt_home_balance)).check(matches(withText((testUserBalanceGER + " €"))));

        // Add new Entry Expense
        addNewEntryExpense();

        // Navigate to HomeActivity
        NavigateTo(R.id.home);

        // Check Balance
        onView(withId(R.id.txt_home_balance)).check(matches(withText((BalanceGER + " €"))));

        // Add new Entry Income
        addNewEntryIncome();

        // Navigate to HomeActivity
        NavigateTo(R.id.home);

        // Check Balance
        onView(withId(R.id.txt_home_balance)).check(matches(withText((BalanceGER + " €"))));

        // Check Numbers on StatisticsActivity
        // Navigate to StatisticsActivity
        NavigateTo(R.id.statistics);

        // Check Balance Statistics
        onView(withId(R.id.txt_statistics_balance)).check(matches(withText((BalanceGER + " €"))));

        // Check Current Month Statistics
        onView(withId(R.id.txt_statistics_month)).check(matches(withText(currentMonth)));

        // Check Current Year Statistics
        onView(withId(R.id.txt_statistics_h_year)).check(matches(withText(currentYear)));

        // TODO: format matches to fit the template
        /*
        // Check Current Year/Month Expenses and Income
        onView(withId(R.id.txt_statistics_expensesyear)).check(matches(withText(String.valueOf(currentYearExpenses))));
        onView(withId(R.id.txt_statistics_incomeyear)).check(matches(withText(String.valueOf(currentYearIncome))));
        onView(withId(R.id.txt_statistics_expenses)).check(matches(withText(String.valueOf(currentMonthExpenses))));
         */
    }

    private Date generateRandomEntryDate() {
        //Generate random Date in 10 Year Timeframe
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5); // to get previous year add -1
        Date fiveYearsFromNow = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.YEAR, -5); // to get previous year add -1
        Date fiveYearsAgo = cal2.getTime();

        Date EntryDate = between(fiveYearsAgo, fiveYearsFromNow);
        year = EntryDate.getYear();
        month = EntryDate.getMonth();
        day = EntryDate.getDay();
        return EntryDate;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double generateRandomEntryAmount() {
        Random r = new Random();
        return round(150 * r.nextDouble(), 2); // Entry Amount is <= 150
    }

    private double generateRandomBalance() {
        Random r = new Random();
        return round(1 + (9999.99 - 1) * r.nextDouble(), 2);
    }

    private void NavigateTo(int Id) {
        onView(withId(Id)).perform(ViewActions.click());
    }

    @NonNull
    private String getGermanNumberFormat(double newBalance) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat dform = (DecimalFormat) nf;
        return dform.format(newBalance);
    }

    private void addNewEntryExpense() {
        // Navigate to EntriesActivity
        NavigateTo(R.id.addEntry);

        // Generate new Entry Variables
        EntryAmount = generateRandomEntryAmount();
        EntryDate = generateRandomEntryDate();

        // Add new Entry
        onView(withId(R.id.txt_entries_amount)).perform(ViewActions.typeText(String.valueOf(EntryAmount)));
        hideKeyboard();
        onView(withId(R.id.txt_entries_notice)).perform(ViewActions.typeText(EntryNotice));
        hideKeyboard();

        // Select Date
        onView(withId(R.id.txt_entries_date)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withText("OK")).perform(ViewActions.click());

        // Save Entry
        onView(withId(R.id.btn_entries_addentry)).perform(ViewActions.click());

        // Adjust Variables
        testUserBalance -= EntryAmount;
        BalanceGER = getGermanNumberFormat(testUserBalance);

        if (currentYear.equals(EntryDate.getYear())) {
            currentYearExpenses += EntryAmount;
            if (currentMonth.equals(EntryDate.getMonth())) {
                currentMonthExpenses += EntryAmount;
            }
        }
    }

    private void addNewEntryIncome() {
        // Navigate to EntriesActivity
        NavigateTo(R.id.addEntry);

        // Generate new Entry Variables
        EntryAmount = generateRandomEntryAmount();
        EntryDate = generateRandomEntryDate();

        // Activate Switch
        onView(withId(R.id.switch_entries_incomeOrExpense)).perform(ViewActions.click());

        // Add new Entry
        onView(withId(R.id.txt_entries_amount)).perform(ViewActions.typeText(String.valueOf(EntryAmount)));
        hideKeyboard();
        onView(withId(R.id.txt_entries_notice)).perform(ViewActions.typeText(EntryNotice));
        hideKeyboard();

        // Select Date
        onView(withId(R.id.txt_entries_date)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withText("OK")).perform(ViewActions.click());

        // Save Entry
        onView(withId(R.id.btn_entries_addentry)).perform(ViewActions.click());

        // Adjust variables
        testUserBalance += EntryAmount;
        BalanceGER = getGermanNumberFormat(testUserBalance);
        if (currentYear.equals(EntryDate.getYear())) {
            currentYearIncome += EntryAmount;
        }
    }

    public void hideKeyboard() {
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
    }

    public static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    public void registerAccountForTest() {
        // Navigate to RegisterActivity
        NavigateTo(R.id.btn_main_register);

        // Enter new user data
        onView(withId(R.id.txt_register_username)).perform(ViewActions.typeText(testUserName));
        hideKeyboard();
        onView(withId(R.id.txt_register_password)).perform(ViewActions.typeText(testUserPassword));
        hideKeyboard();
        onView(withId(R.id.txt_register_password_rep)).perform(ViewActions.typeText(testUserPassword));
        hideKeyboard();
        onView(withId(R.id.txt_register_balance)).perform(ViewActions.typeText(String.valueOf(testUserBalance)));
        hideKeyboard();

        // Register new user
        onView(withId(R.id.btn_register_register)).perform(ViewActions.click());
    }

    private void loginWithTestUser() {
        // Login
        onView(withId(R.id.txt_login_username)).perform(ViewActions.typeText(testUserName));
        hideKeyboard();
        onView(withId(R.id.txt_login_password)).perform(ViewActions.typeText(testUserPassword));
        hideKeyboard();
        onView(withId(R.id.btn_login_login)).perform(ViewActions.click());
    }

}
