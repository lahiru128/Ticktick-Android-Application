package com.example.ticktick;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class IncomeViewTest {

    @Rule
    public ActivityTestRule<IncomeView> incomeViewActivityTestRule = new ActivityTestRule<IncomeView>(IncomeView.class);

    private IncomeView incomeView = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AddIncome.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        incomeView = incomeViewActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchNextActivityButtonOnClick() {
        //Instrumentation test for add Income form
        assertNotNull(incomeView.findViewById(R.id.incomeAddBtn));
        onView(withId(R.id.incomeAddBtn)).perform(click());

        Activity incomeView = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(incomeView);
        incomeView.finish();
    }


    @After
    public void tearDown() throws Exception {
        incomeView = null;
    }
}