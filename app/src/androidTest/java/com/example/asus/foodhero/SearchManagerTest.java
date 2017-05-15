package com.example.asus.foodhero;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Saurav on 5/2/2017.
 */
public class SearchManagerTest {
    @Rule
    public ActivityTestRule<SearchManager> mActivityRule=new ActivityTestRule<SearchManager>(SearchManager.class);

    @Test
    public void IsSearchworking()
    {
        onView(withText("My Location..")).check(matches(isDisplayed()));
    }

}