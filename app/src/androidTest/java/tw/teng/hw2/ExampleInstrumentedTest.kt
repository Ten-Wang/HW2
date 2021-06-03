package tw.teng.hw2

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tw.teng.hw2.resource.utils.DataUtils
import tw.teng.hw2.ui.MainActivity


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("tw.teng.hw2", appContext.packageName)
    }

    //宣告要測試的Activity，請Runner執行Test區塊前直接開啟
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ITEM_COUNT = DataUtils.getItemList().size
    val LIST_ITEMS = DataUtils.getItemList()

    // check recycler view in main activity
    @Test
    fun test_isRecyclerVisible_in_MainActivity() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }
}