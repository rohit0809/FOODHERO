package com.example.asus.foodhero;

import android.support.test.rule.ActivityTestRule;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Saurav on 5/2/2017.
 */
public class RegisterTest {
    public ActivityTestRule<Register> mActivityRule=new ActivityTestRule<Register>(Register.class);
    @Test
    public void IsRegistrationWorking()
    {
        onView(withText("Name")).check(matches(isDisplayed()));
        onView(withText("Phone")).check(matches(isDisplayed()));
        onView(withText("Email")).check(matches(isDisplayed()));
        onView(withText("Password")).check(matches(isDisplayed()));
    }

}
