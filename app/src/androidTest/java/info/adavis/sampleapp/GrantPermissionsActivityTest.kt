package info.adavis.sampleapp

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.intent.matcher.IntentMatchers.isInternal
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GrantPermissionsActivityTest {

    @get:Rule var permissionRule = GrantPermissionRule.grant(android.Manifest.permission.CALL_PHONE)
    @get:Rule var activityRule = IntentsTestRule(GrantPermissionsActivity::class.java)

    @Before
    fun stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun callButtonIsDisplayed() {
        onView(withId(R.id.button)).check(matches(isDisplayed()))
    }

    @Test
    fun callButtonOpensDialer() {
        onView(withId(R.id.button)).perform(click())

        intended(allOf(hasAction(Intent.ACTION_CALL),
                       hasData(GrantPermissionsActivity.PHONE_NUMBER)))
    }

}
