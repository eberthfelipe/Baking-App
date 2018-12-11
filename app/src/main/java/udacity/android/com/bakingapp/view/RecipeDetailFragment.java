package udacity.android.com.bakingapp.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.databinding.RecipeDetailBinding;
import udacity.android.com.bakingapp.object.Recipe;

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

    /**
     * The dummy content this fragment is presenting.
     */
    private Recipe mRecipeItem;

    private RecipeDetailBinding mRecipeDetailBinding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_RECIPE)) {

            mRecipeItem = new Recipe((Recipe) Objects.requireNonNull(getArguments().getParcelable(ARG_RECIPE)));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = null;
            if (activity != null) {
                appBarLayout = activity.findViewById(R.id.toolbar_layout);
            }
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRecipeItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecipeDetailBinding = DataBindingUtil.inflate(inflater, R.layout.recipe_detail, container, false);

        // TODO: create recipe detail view
        if (mRecipeItem != null) {
            mRecipeDetailBinding.recipeDetail.setText(mRecipeItem.toString());
        }

        return mRecipeDetailBinding.getRoot();
    }
}
