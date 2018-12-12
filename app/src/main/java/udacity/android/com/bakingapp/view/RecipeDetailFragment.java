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
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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

    private Recipe mRecipeItem;
    private RecipeDetailBinding mRecipeDetailBinding;
    private SimpleExoPlayer mPlayer;
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
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecipeDetailBinding = DataBindingUtil.inflate(inflater, R.layout.recipe_detail, container, false);

        // TODO: improve recipe detail view
        if (mRecipeItem != null) {
            mRecipeDetailBinding.recipeDetail.setText(mRecipeItem.toString());
            mRecipeDetailBinding.setRecipeStep(mRecipeItem.getSteps().get(positionStep));
            if(mRecipeDetailBinding.getRecipeStep().hasVideo()){
                initializePlayer();
            }
        }

        return mRecipeDetailBinding.getRoot();
    }

    //region ExoPlayer
    private void initializePlayer(){
        Context context = this.getContext();

        if(context != null){
            // Create a default TrackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            //Initialize the player
            mPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

            //TODO: check deprecated in lines 127 and 139
            //Initialize simpleExoPlayerView
            SimpleExoPlayerView simpleExoPlayerView = mRecipeDetailBinding.exoplayerRecipeDetail;
            simpleExoPlayerView.setPlayer(mPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(context, Util.getUserAgent(context, "CloudinaryExoplayer"));

            // Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // This is the MediaSource representing the media to be played.
            Uri videoUri = Uri.parse(mRecipeDetailBinding.getRecipeStep().getVideo_url());
            MediaSource videoSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory, extractorsFactory, null, null);

            // Prepare the player with the source.
            mPlayer.prepare(videoSource);
        }
    }
    //endregion
}
