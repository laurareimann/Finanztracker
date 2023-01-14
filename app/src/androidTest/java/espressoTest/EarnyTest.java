package espressoTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.annotation.NonNull;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.earny.MainActivity;
import com.example.testapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class EarnyTest {

    // User Variables
    // todo: generate random balance
    String currentTimestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
    private final String testUserName = "newUser" + currentTimestamp;
    private final String testUserPassword = "Password";
    private final double testUserBalance = 1234.56;

    // Entry Variables
    private final double EntryAmount = 123.45;
    private final String EntryNotice = "new Entry";

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

        // Add new Entry
        addNewEntry();

        // Navigate to HomeActivity
        onView(withId(R.id.home)).perform(ViewActions.click());

        // Check Balance
        String newBalanceGER = getGermanNumberFormat(testUserBalance - EntryAmount);
        onView(withId(R.id.txt_home_balance)).check(matches(withText((newBalanceGER + " €"))));


    }

    @NonNull
    private String getGermanNumberFormat(double newBalance) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat dform = (DecimalFormat) nf;
        return dform.format(newBalance);
    }

    private void addNewEntry() {
        // Navigate to EntriesActivity
        onView(withId(R.id.addEntry)).perform(ViewActions.click());

        // Add new Entry
        onView(withId(R.id.txt_entries_amount)).perform(ViewActions.typeText(String.valueOf(EntryAmount)));
        hideKeyboard();
        onView(withId(R.id.txt_entries_notice)).perform(ViewActions.typeText(EntryNotice));
        hideKeyboard();

        // Select Date
        onView(withId(R.id.txt_entries_date)).perform(ViewActions.click());
        onView(withText("OK")).perform(ViewActions.click());

        // Save Entry
        onView(withId(R.id.btn_entries_addentry)).perform(ViewActions.click());
    }

    public void hideKeyboard() {
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
    }

    public void registerAccountForTest() {
        // Navigate to RegisterActivity
        onView(withId(R.id.btn_main_register)).perform(ViewActions.click());

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
