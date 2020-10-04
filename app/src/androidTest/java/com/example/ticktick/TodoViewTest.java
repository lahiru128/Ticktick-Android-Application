package com.example.ticktick;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class TodoViewTest {

    @Rule
    public ActivityTestRule<TodoView> todoViewActivityTestRule = new ActivityTestRule<TodoView>(TodoView.class);

    private TodoView todoView = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(TodoAdd.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        todoView = todoViewActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        //test cases for the view add values
        View view = todoView.findViewById(R.id.todoAddBtnResultDashboard);
        assertNotNull(view);
    }

    @Test
    public void testLaunchNextActivityButtonOnClick() {
        //Instrumentation test for open to_do view dashboard
        assertNotNull(todoView.findViewById(R.id.todoSaveBtn));
        onView(withId(R.id.todoSaveBtn)).perform(click());

        Activity todoview = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(todoview);
        todoView.finish();
    }



    @After
    public void tearDown() throws Exception {
        todoView = null;
    }
}
