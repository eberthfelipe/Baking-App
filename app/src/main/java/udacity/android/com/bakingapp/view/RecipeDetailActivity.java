package udacity.android.com.bakingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.databinding.ActivityRecipeDetailBinding;
import udacity.android.com.bakingapp.object.Recipe;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepClickListener {

    private ActivityRecipeDetailBinding mActivityRecipeDetailBinding;
    private Recipe mCurrentRecipe;

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

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            mCurrentRecipe = getIntent().getParcelableExtra(RecipeDetailFragment.ARG_RECIPE);
            arguments.putParcelable(RecipeDetailFragment.ARG_RECIPE, mCurrentRecipe);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            if(getSupportFragmentManager().getFragments().get(0).getClass() == RecipeDetailFragment.class){
                navigateUpTo(new Intent(this, RecipeListActivity.class));
            } else {
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepSelected(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailStepFragment.ARG_STEP,mCurrentRecipe.getSteps().get(position));
        arguments.putInt(RecipeDetailStepFragment.ARG_STEP_MAX,mCurrentRecipe.getSteps().size());
        RecipeDetailStepFragment stepFragment = new RecipeDetailStepFragment();
        stepFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, stepFragment)
                .addToBackStack(null)
                .commit();
    }
}
