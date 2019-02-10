package udacity.android.com.bakingapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import udacity.android.com.bakingapp.view.RecipeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    public static final String RECIPE_NAME = "Brownies";
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeListActivity = new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void setUp(){
        mIdlingResource = mRecipeListActivity.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void clickItemRecipeList(){
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the OrderActivity opens with the correct tea name displayed
//        onView(withId(R.id.toolbar_layout)).check(matches(withText(RECIPE_NAME)));

//        onView(withText(RECIPE_NAME)).check(matches(withParent(withId(R.id.toolbar_layout))));
        onView(allOf(isAssignableFrom(Toolbar.class),withParent(isAssignableFrom(CollapsingToolbarLayout.class))))
                .check(matches(withId(R.id.detail_toolbar)));
//                .check(matches(withText(RECIPE_NAME)));
    }

    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
