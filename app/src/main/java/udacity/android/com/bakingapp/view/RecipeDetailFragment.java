package udacity.android.com.bakingapp.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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
    private int positionStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: implement on restore instance to save position list
        positionStep = 0;

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
    public void onPause() {
        super.onPause();
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
