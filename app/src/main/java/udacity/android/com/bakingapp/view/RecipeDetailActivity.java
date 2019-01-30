package udacity.android.com.bakingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
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
public class RecipeDetailActivity extends BakingActivity {

    public static final String CURRENT_RECIPE = "current_recipe";
    public static final String RECIPE_FRAGMENT = "recipe_fragment";
    private ActivityRecipeDetailBinding mActivityRecipeDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityRecipeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Toolbar toolbar = mActivityRecipeDetailBinding.detailToolbar;
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle bundleReceived = getIntent().getBundleExtra(RecipeDetailFragment.ARG_RECIPE);
            setCurrentRecipe((Recipe) bundleReceived.getParcelable(RecipeDetailFragment.ARG_RECIPE));
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.ARG_RECIPE, getCurrentRecipe());
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
            setCurrentRecipe((Recipe) savedInstanceState.getParcelable(CURRENT_RECIPE));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, Objects.requireNonNull(getSupportFragmentManager().getFragment(savedInstanceState, RECIPE_FRAGMENT)))
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mActivityRecipeDetailBinding != null && getCurrentRecipe() != null){
            outState.putParcelable(CURRENT_RECIPE, getCurrentRecipe());
            getSupportFragmentManager().putFragment(outState, RECIPE_FRAGMENT, getSupportFragmentManager().getFragments().get(0));
            super.onSaveInstanceState(outState);
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

}
