package com.example.ticktick;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

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

public class DashBoardTest {

    @Rule
    public ActivityTestRule<DashBoard> dashBoardActivityTestRule = new ActivityTestRule<DashBoard>(DashBoard.class);

    private DashBoard dashBoard = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(IncomeView.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(ExpensesView.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        dashBoard = dashBoardActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        //test cases for the income and expense total values
        View view = dashBoard.findViewById(R.id.incomeResultDashboard);
        View view1 = dashBoard.findViewById(R.id.expensesResultDashboard);
        assertNotNull(view);
        assertNotNull(view1);
    }

    @Test
    public void testLaunchNextActivityButtonOnClick() {
        //Instrumentation test for open Income dashboard
        assertNotNull(dashBoard.findViewById(R.id.ViewIncomeBtn));
        onView(withId(R.id.ViewIncomeBtn)).perform(click());

        Activity incomeView = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(incomeView);
        incomeView.finish();
    }

    @Test
    public void setDashBoardToExpenseView(){
        //Instrumentation test for open Expense dashboard
        assertNotNull(dashBoard.findViewById(R.id.ViewExpenseBtn));
        onView(withId(R.id.ViewExpenseBtn)).perform(click());

        Activity expenseView = getInstrumentation().waitForMonitorWithTimeout(monitor1, 5000);
        assertNotNull(expenseView);
        expenseView.finish();
    }


    @After
    public void tearDown() throws Exception {
        dashBoard = null;
    }
}