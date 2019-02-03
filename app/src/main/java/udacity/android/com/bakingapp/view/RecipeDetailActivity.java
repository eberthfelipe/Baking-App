package udacity.android.com.bakingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Objects;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.databinding.ActivityRecipeDetailBinding;
import udacity.android.com.bakingapp.object.Recipe;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepClickListener, StepNavigationClickListener {

    public static final String CURRENT_RECIPE = "current_recipe";
    public static final String RECIPE_FRAGMENT = "recipe_fragment";
    public static final String RECIPE_DETAIL_TWO_PANE = "recipe_detail_two_pane";
    private ActivityRecipeDetailBinding mActivityRecipeDetailBinding;
    private Recipe mCurrentRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRecipeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Toolbar toolbar = mActivityRecipeDetailBinding.detailToolbar;
        setSupportActionBar(toolbar);

        if (findViewById(R.id.recipe_detail_step_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-sw600dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle bundleReceived = getIntent().getBundleExtra(RecipeDetailFragment.ARG_RECIPE);
            mCurrentRecipe = bundleReceived.getParcelable(RecipeDetailFragment.ARG_RECIPE);
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.ARG_RECIPE, mCurrentRecipe);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!savedInstanceState.isEmpty()){
            mCurrentRecipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, Objects.requireNonNull(getSupportFragmentManager().getFragment(savedInstanceState, RECIPE_FRAGMENT)))
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mActivityRecipeDetailBinding != null && mCurrentRecipe != null){
            outState.putParcelable(CURRENT_RECIPE, mCurrentRecipe);
            getSupportFragmentManager().putFragment(outState, RECIPE_FRAGMENT, getSupportFragmentManager().getFragments().get(0));
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onBackPressed() {
        if(!getSupportFragmentManager().getFragments().isEmpty()
                && getSupportFragmentManager().getFragments().get(0).getClass() == RecipeDetailStepFragment.class){
            FragmentManager.BackStackEntry firstFragment = getSupportFragmentManager().getBackStackEntryAt(0);
            getSupportFragmentManager().popBackStack(firstFragment.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepSelected(int position) {
        replaceStepFragment(position);
    }

    @Override
    public void onPreviousSelected(int position) {
        replaceStepFragment(--position);
    }

    @Override
    public void onNextSelected(int position) {
        replaceStepFragment(++position);
    }

    public void replaceStepFragment(int position){
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailStepFragment.ARG_STEP,mCurrentRecipe.getSteps().get(position));
        arguments.putInt(RecipeDetailStepFragment.ARG_STEP_MAX, mCurrentRecipe.getSteps().size()-1);
        arguments.putInt(RecipeDetailStepFragment.ARG_STEP_POSITION, position);
        arguments.putBoolean(RECIPE_DETAIL_TWO_PANE, mTwoPane);
        RecipeDetailStepFragment stepFragment = new RecipeDetailStepFragment();
        stepFragment.setArguments(arguments);
        if(mTwoPane){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_step_container, stepFragment)
//                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, stepFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
