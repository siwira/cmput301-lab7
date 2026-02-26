package com.example.androiduitesting;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static java.util.EnumSet.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Test
    public void testAddCity(){// Click on Add City button
        onView(withId(R.id.button_add)).perform(click());
        // Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if text "Edmonton" is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }
    @Test
    public void testClearCity() {

        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear the list
        onView(withId(R.id.button_clear)).perform(click());

        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView() {

        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if in the AdapterView (given id of that adapter view),
        // there is data (instance of String) located at position zero.
        // If it matches the text we provided, the test should pass.
        // You can also use anything() instead of is(instanceOf(String.class)).
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches(withText("Edmonton")));
    }
    @Test
    public void testShowActivity() {

        // Add city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());


        // Add city
        onView(withId(R.id.button_add)).perform(click());

        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));

        onView(withId(R.id.button_confirm)).perform(click());
        // Click city from ListView
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        // Verify ShowActivity
        onView(withId(R.id.text_cityName))
                .check(matches(isDisplayed()));

        // Verify correct city name
        onView(withId(R.id.text_cityName))
                .check(matches(withText("Edmonton")));

    }
    @Test
    public void testBackButton() {

        // Add city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());


        // Add city
        onView(withId(R.id.button_add)).perform(click());

        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));

        onView(withId(R.id.button_confirm)).perform(click());
        // Click city from ListView
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        // Verify ShowActivity
        onView(withId(R.id.text_cityName))
                .check(matches(isDisplayed()));

        // Verify correct city name
        onView(withId(R.id.text_cityName))
                .check(matches(withText("Edmonton")));

        // Click back button
        onView(withId(R.id.button_back)).perform(click());

        // Verify we're in MainActivity
        onView(withId(R.id.city_list))
                .check(matches(isDisplayed()));


    }
}