package app.wildcardcoder.creditcardinputexercise;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.wildcardcoder.creditcardinputexercise.app.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule  = new  ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testlaunch(){
        View views[] = new View[6];
        views[0] = mainActivity.findViewById(R.id.e_cardnumber);
        views[1] = mainActivity.findViewById(R.id.e_mmyy);
        views[2] = mainActivity.findViewById(R.id.e_securitycode);
        views[3] = mainActivity.findViewById(R.id.e_firstname);
        views[4] = mainActivity.findViewById(R.id.e_lastname);
        views[5] = mainActivity.findViewById(R.id.submitBtn);

        for(int i=0;i<6;i++) {
            assertNotNull(views[i]);
        }


    }
    @Test
    public void Integrationtest(){
        onView(withId(R.id.submitBtn)).perform(click()).noActivity();

    }

    @After
    public void tearDown() throws Exception {

        mainActivity = null;
    }
}