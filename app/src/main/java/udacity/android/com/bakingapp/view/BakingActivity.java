package udacity.android.com.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.object.Recipe;
import udacity.android.com.bakingapp.presenter.RecipePreferenceImpl;
import udacity.android.com.bakingapp.widget.BakingWidget;

public class BakingActivity extends AppCompatActivity implements StepClickListener, StepNavigationClickListener  {

    private Recipe mCurrentRecipe;

    public Recipe getCurrentRecipe() {
        return mCurrentRecipe;
    }

    public void setCurrentRecipe(Recipe mCurrentRecipe) {
        this.mCurrentRecipe = mCurrentRecipe;
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
        RecipeDetailStepFragment stepFragment = new RecipeDetailStepFragment();
        stepFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, stepFragment)
                .addToBackStack(null)
                .commit();
    }

    public void updateWidget(Context context){
        Intent intent = new Intent(context, BakingWidget.class);
        intent.setAction(BakingWidget.BAKING_WIDGET_UPDATE);
        if(mCurrentRecipe != null){
            RecipePreferenceImpl recipePreference = new RecipePreferenceImpl();
            recipePreference.saveRecipe(context, mCurrentRecipe);
        }
        context.sendBroadcast(intent);
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
}
