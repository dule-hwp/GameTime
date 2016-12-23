package hwp.gametime.activities


import android.support.test.espresso.ViewInteraction
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.Locale

import hwp.gametime.R

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import java.text.SimpleDateFormat

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivity2Test {

    @Rule
    var mActivityTestRule = ActivityTestRule(MainActivity2::class.java)

    @Test
    fun mainActivity2Test() {

        val ft = SimpleDateFormat("EEE, MMM d", Locale.US)

        val bottomNavigationItemView = onView(
                allOf(withId(R.id.action_favourites), isDisplayed()))
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
                allOf(withId(R.id.action_search), isDisplayed()))
        bottomNavigationItemView2.perform(click())

        val recyclerView = onView(
                allOf(withId(R.id.recyclerView),
                        withParent(allOf(withId(R.id.viewPager),
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0))),
                        isDisplayed()))
        recyclerView.check(matches(isDisplayed()))

        val bottomNavigationItemView3 = onView(
                allOf(withId(R.id.action_games), isDisplayed()))
        bottomNavigationItemView3.perform(click())

        val imageButton = onView(
                allOf(withId(R.id.btnPreviousDay), withContentDescription("Previous Day"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                0),
                        isDisplayed()))
        imageButton.check(matches(isDisplayed()))

        val imageButton2 = onView(
                allOf(withId(R.id.btnNextDay), withContentDescription("Next Day"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                2),
                        isDisplayed()))
        imageButton2.check(matches(isDisplayed()))


        val textView = onView(
                allOf(withId(R.id.tvGamesDate), withText("Wed, Dec 21 8 games"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("Wed, Dec 21 8 games")))

        val frameLayout = onView(
                allOf(withId(R.id.action_favourites),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()))
        frameLayout.check(matches(isDisplayed()))

        val appCompatImageButton = onView(
                allOf(withId(R.id.btnPreviousDay), withContentDescription("Previous Day"), isDisplayed()))
        appCompatImageButton.perform(click())

        val textView2 = onView(
                allOf(withId(R.id.tvGamesDate), withText("Tue, Dec 20 11 games"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("Tue, Dec 20 11 games")))

        val textView3 = onView(
                allOf(withId(R.id.tvGamesDate), withText("Tue, Dec 20 11 games"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView3.check(matches(withText("Tue, Dec 20 11 games")))

        val appCompatImageButton2 = onView(
                allOf(withId(R.id.btnNextDay), withContentDescription("Next Day"), isDisplayed()))
        appCompatImageButton2.perform(click())

        val textView4 = onView(
                allOf(withId(R.id.tvGamesDate), withText("Wed, Dec 21 8 games"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView4.check(matches(withText("Wed, Dec 21 8 games")))

        val bottomNavigationItemView4 = onView(
                allOf(withId(R.id.action_favourites), isDisplayed()))
        bottomNavigationItemView4.perform(click())

    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
