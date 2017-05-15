package com.example.asus.foodhero;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Saurav on 5/2/2017.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityRule= new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity lActivity=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(SearchManager.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
       lActivity=mLoginActivityRule.getActivity();
    }

    @Test
    public void testLaunch()
    {
        assertNotNull(lActivity.findViewById(R.id.email_sign_in_button));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        Activity secactivity=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(secactivity);
        secactivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        lActivity=null;
    }

}