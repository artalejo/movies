package com.android.movies

import android.app.Activity
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.core.internal.deps.guava.collect.Iterables
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.android.movies.conditionWatcher.ConditionWatcher
import com.android.movies.conditionWatcher.instructions.PopularShowsEndlessInstruction
import com.android.movies.conditionWatcher.instructions.PopularShowsInstruction
import com.android.movies.conditionWatcher.instructions.SimilarShowsInstruction
import com.android.movies.ui.popularShows.PopularShowsActivity
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.hasToString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PopularShowsActivityTest {

    private val FIRST_ITEM_POS = 0
    private val HOMELAND_ITEM_POS = 1
    private val LIMIT_POSITION = 15
    private val ENDLESS_NEW_POSITION = 30
    private val HOMELAND_TITLE = "Homeland"
    private val SIMPSONS_TITLE = "The Simpsons"
    private val BLACK_MIRROR = "Black Mirror"

    @Rule @JvmField  var popularShowsRule: ActivityTestRule<PopularShowsActivity> = ActivityTestRule(PopularShowsActivity::class.java)

    private val popularShowsInstruction = PopularShowsInstruction()
    private val endlessShowsInstruction = PopularShowsEndlessInstruction()
    private val similarShowsInstruction = SimilarShowsInstruction()

    @Test
    fun clickOnPopularShowNavigatesToDetailActivityAndBack() {
        val activity = popularShowsRule.activity
        ConditionWatcher.waitForCondition(activity, popularShowsInstruction)
        onView(withId(R.id.toolbar_title)).check(matches(withText(activity.resources.getString(R.string.popular_shows))))
        onData(hasToString(containsString(HOMELAND_TITLE)))
        onData(hasToString(containsString(SIMPSONS_TITLE)))

        onView(withId(R.id.shows_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(HOMELAND_ITEM_POS, click()))

        onView(withId(R.id.show_title)).check(matches(withText(HOMELAND_TITLE)))
        onView(withId(R.id.toolbar_back)).perform(click())
        onData(hasToString(containsString(HOMELAND_TITLE)))
    }

    @Test
    fun navigateToLimitAndTestIfEndlessScrollWorks() {
        val activity = popularShowsRule.activity
        ConditionWatcher.waitForCondition(popularShowsRule.activity, popularShowsInstruction)
        onView(withId(R.id.toolbar_title)).check(matches(withText(activity.resources.getString(R.string.popular_shows))))
        onView(withId(R.id.shows_recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(LIMIT_POSITION))
        onView(withId(R.id.shows_recycler)).perform(ViewActions.swipeUp())
        // Endless Scroll will be triggered after swipe up then will wait till loads new data and
        // navigate to position 30.
        ConditionWatcher.waitForCondition(popularShowsRule.activity, endlessShowsInstruction)
        onView(withId(R.id.shows_recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(ENDLESS_NEW_POSITION))
        onData(hasToString(containsString(BLACK_MIRROR)))
    }

    @Test
    fun navigateToSimilarShowDetail() {
        val activity = popularShowsRule.activity
        ConditionWatcher.waitForCondition(activity, popularShowsInstruction)
        onView(withId(R.id.toolbar_title)).check(matches(withText(activity.resources.getString(R.string.popular_shows))))
        onData(hasToString(containsString(HOMELAND_TITLE)))
        onData(hasToString(containsString(SIMPSONS_TITLE)))

        onView(withId(R.id.shows_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(HOMELAND_ITEM_POS, click()))

        onView(withId(R.id.show_title)).check(matches(withText(HOMELAND_TITLE)))
        val currentActivity = getCurrentActivity()
        ConditionWatcher.waitForCondition(currentActivity as AppCompatActivity, similarShowsInstruction)
        onView(withId(R.id.similar_shows_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(FIRST_ITEM_POS, click()))
        onView(hasToString(containsString(currentActivity.getString(R.string.show_average))))
        onView(hasToString(containsString(currentActivity.getString(R.string.first_appearance))))
    }

    private fun getCurrentActivity(): Activity {
        getInstrumentation().waitForIdleSync()
        val activity = arrayOfNulls<Activity>(1)
        getInstrumentation().runOnMainSync(Runnable {
            val activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            activity[0] = Iterables.getOnlyElement(activities)
        })
        return activity[0] as Activity
    }
}