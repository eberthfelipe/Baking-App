package udacity.android.com.bakingapp.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.util.Objects;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.databinding.RecipeDetailStepBinding;
import udacity.android.com.bakingapp.object.Step;

public class RecipeDetailStepFragment extends Fragment {

    public static final String ARG_STEP = "step_object";
    public static final String ARG_STEP_MAX = "step_max_position";
    private static final String TAG = RecipeDetailStepFragment.class.getName();
    private Step mStep;
    private RecipeDetailStepBinding mRecipeDetailStepBinding;
    private int maxPositionStep;
    private SimpleExoPlayer mPlayer;
    private int mPlayerCurrentWindow = 0;
    private long mPlayerPosition = 0;
    private boolean mPlayerWhenReady = true;

    public RecipeDetailStepFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasValidArguments(getArguments())) {
            mStep = new Step((Step) Objects.requireNonNull(getArguments().getParcelable(ARG_STEP)));
            maxPositionStep = getArguments().getInt(ARG_STEP_MAX);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeDetailStepBinding = DataBindingUtil.inflate(inflater, R.layout.recipe_detail_step, container, false);

        if(mStep != null){
            Log.d(TAG, "onCreateView: " + mStep.toString());
            mRecipeDetailStepBinding.setStepDescription(mStep.getDescription());
            mRecipeDetailStepBinding.setHasVideo(mStep.hasVideo());
            if(mStep.hasVideo()){
                initializePlayer();
            }
        }
        return mRecipeDetailStepBinding.getRoot();
    }

    public boolean hasValidArguments(Bundle args){
        return args != null
                && args.containsKey(ARG_STEP)
                && args.containsKey(ARG_STEP_MAX);
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

            //Initialize exoPlayerView
            PlayerView exoPlayerView = mRecipeDetailStepBinding.exoplayerRecipeDetailStep;
            exoPlayerView.setPlayer(mPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(context, Util.getUserAgent(context, "BakingApp"));

            // This is the MediaSource representing the media to be played.
            Uri videoUri = Uri.parse(mStep.getVideo_url());
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            // Prepare the player with the source.
            mPlayer.prepare(videoSource);
            mPlayer.seekTo(mPlayerCurrentWindow, mPlayerPosition);
            mPlayer.setPlayWhenReady(mPlayerWhenReady);
        }
    }

    private void releasePlayer(){
        if(mPlayer != null){
            mPlayerPosition = mPlayer.getCurrentPosition();
            mPlayerCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayerWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }
    //endregion
}
