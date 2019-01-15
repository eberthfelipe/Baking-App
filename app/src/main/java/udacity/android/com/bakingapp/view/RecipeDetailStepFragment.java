package udacity.android.com.bakingapp.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public static final String CURRENT_STEP = "current_step";
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
    private StepNavigationClickListener mStepNavigationCallBack;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.id_previous_button:
                    Log.d(TAG, "PREVIOUS");
                    mStepNavigationCallBack.onPreviousSelected(mStep.getId());
                    break;
                case R.id.id_next_button:
                    Log.d(TAG, "NEXT");
                    mStepNavigationCallBack.onNextSelected(mStep.getId());
                    break;
            }
//            mStepCallBack.onStepSelected(position);
        }
    };

    public RecipeDetailStepFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStepNavigationCallBack = (StepNavigationClickListener) getContext();
        if(savedInstanceState == null || savedInstanceState.isEmpty()){
            if (hasValidArguments(getArguments())) {
                mStep = new Step((Step) Objects.requireNonNull(getArguments().getParcelable(ARG_STEP)));
                maxPositionStep = getArguments().getInt(ARG_STEP_MAX);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null && !savedInstanceState.isEmpty()){
            mStep = savedInstanceState.getParcelable(CURRENT_STEP);
            maxPositionStep = savedInstanceState.getInt(ARG_STEP_MAX);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(mRecipeDetailStepBinding != null && mStep!=null){
            outState.putParcelable(CURRENT_STEP, mStep);
            outState.putInt(ARG_STEP_MAX, maxPositionStep);
            super.onSaveInstanceState(outState);
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
            mRecipeDetailStepBinding.idPreviousButton.setOnClickListener(mOnClickListener);
            mRecipeDetailStepBinding.idNextButton.setOnClickListener(mOnClickListener);
            validateNavigation();
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

    public void validateNavigation(){
        if(mStep.getId() == 0){
            mRecipeDetailStepBinding.idPreviousButton.setEnabled(false);
            mRecipeDetailStepBinding.idPreviousButton.setAlpha((float) 0.30);
        } else if(mStep.getId() == maxPositionStep){
            mRecipeDetailStepBinding.idNextButton.setEnabled(false);
            mRecipeDetailStepBinding.idNextButton.setAlpha((float) 0.30);
        } else {
            mRecipeDetailStepBinding.idPreviousButton.setEnabled(true);
            mRecipeDetailStepBinding.idNextButton.setEnabled(true);
        }
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
