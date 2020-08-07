package com.example.materialdialogstesting

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.materialdialogstesting.MainActivity.Companion.buildToastMessage
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest() {
    @Test
    fun test_showDialog_captureNameInput() {
        //open activity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        //setup
        val expectedName = "Asmaa"

        // open dialog
        onView(withId(R.id.button_launch_dialog)).perform(click())

        //check if the dialog is displayed
        onView(withText(R.string.text_enter_name)).check(matches(isDisplayed()))

        //try to close the dialog on empty text
        onView(withText(R.string.text_ok)).perform(click())

        // make sure dialog is still visible (can't click ok without entering a name)
        onView(withText(R.string.text_enter_name)).check(matches(isDisplayed()))

        // enter a name
        onView(withId(R.id.md_input_message)).perform(typeText(expectedName))

        //click ok
        onView(withText(R.string.text_ok)).perform(click())

        // make sure dialog is gone
        onView(withText(R.string.text_enter_name)).check(doesNotExist())

        // Is the input name matches the text on textview
        onView(withId(R.id.text_name)).check(matches(withText(expectedName)))

        // Is toast displayed and is the message correct?
        onView(withText(buildToastMessage(expectedName)))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }
}