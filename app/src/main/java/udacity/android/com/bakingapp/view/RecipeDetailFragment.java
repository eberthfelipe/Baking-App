package udacity.android.com.bakingapp.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.databinding.RecipeDetailBinding;
import udacity.android.com.bakingapp.object.Ingredient;
import udacity.android.com.bakingapp.object.Recipe;
import udacity.android.com.bakingapp.object.Step;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_RECIPE = "recipe_object";

    private Recipe mRecipeItem;
    private RecipeDetailBinding mRecipeDetailBinding;
    private CollapsingToolbarLayout mAppBarLayout;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        if (activity != null) {
            mAppBarLayout = activity.findViewById(R.id.toolbar_layout);
        }
        if (savedInstanceState == null || savedInstanceState.isEmpty()){
            if(getArguments() != null && getArguments().containsKey(ARG_RECIPE)) {
                mRecipeItem = new Recipe((Recipe) Objects.requireNonNull(getArguments().getParcelable(ARG_RECIPE)));
                if (mAppBarLayout != null) {
                    mAppBarLayout.setTitle(mRecipeItem.getName());
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null && !savedInstanceState.isEmpty()){
            mRecipeItem = savedInstanceState.getParcelable(ARG_RECIPE);
            if (mAppBarLayout != null && mRecipeItem != null) {
                mAppBarLayout.setTitle(mRecipeItem.getName());
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(mRecipeDetailBinding != null && mRecipeItem != null){
            outState.putParcelable(ARG_RECIPE, mRecipeItem);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecipeDetailBinding = DataBindingUtil.inflate(inflater, R.layout.recipe_detail, container, false);

        if (mRecipeItem != null) {
            // Send Ingredients to RecyclerView
            mRecipeDetailBinding.recipeIngredients.setAdapter(new RecipeDetailIngredientRecyclerView((ArrayList<Ingredient>) mRecipeItem.getIngredients()));
            // Send Steps to RecyclerView
            mRecipeDetailBinding.recipeSteps.setAdapter(new RecipeDetailStepRecyclerView((ArrayList<Step>) mRecipeItem.getSteps(), this.getActivity()));
        }

        return mRecipeDetailBinding.getRoot();
    }

}
