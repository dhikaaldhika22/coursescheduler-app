package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.R
import org.junit.Test
import org.junit.Rule

class HomeActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {
        onView(withId(R.id.action_add)).perform(click())
        onView(withText("Add Course")).check(matches(isDisplayed()))
    }
}
