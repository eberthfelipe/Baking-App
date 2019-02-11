package udacity.android.com.bakingapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    private static final String RECIPE_NAME = "Brownies";
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
        onView(withId(R.id.no_internet_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the OrderActivity opens with the correct tea name displayed
        onView(allOf(isAssignableFrom(Toolbar.class),withParent(isAssignableFrom(CollapsingToolbarLayout.class))))
                .check(matches(withId(R.id.detail_toolbar)));
        onView(isAssignableFrom(CollapsingToolbarLayout.class)).
                check(matches(withCollapsibleToolbarTitle(is(RECIPE_NAME))));
    }

    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    /**
     * Reference: https://github.com/dragthor/android-test-kit/issues/172
     * @param textMatcher text to match with CollapsingToolbarLayout title
     * @return Matcher
     */
    public static Matcher<Object> withCollapsibleToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<Object, CollapsingToolbarLayout>(CollapsingToolbarLayout.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
            @Override
            protected boolean matchesSafely(CollapsingToolbarLayout toolbarLayout) {
                return textMatcher.matches(toolbarLayout.getTitle());
            }
        };
    }
}
