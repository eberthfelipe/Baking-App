package udacity.android.com.bakingapp.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import udacity.android.com.bakingapp.BR;
import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.object.Step;

public class RecipeDetailStepRecyclerView extends RecyclerView.Adapter<RecipeDetailStepRecyclerView.RecipeDetailStepAdapter>{

    private static final String TAG = RecipeDetailStepRecyclerView.class.getName();
    private ArrayList<Step> mStepValues;
    private StepClickListener mStepCallBack;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            Step step = mStepValues.get(position);
            Log.d(TAG, "onClick step: " + step.toString());
            mStepCallBack.onStepSelected(position);
        }
    };

    public RecipeDetailStepRecyclerView(ArrayList<Step> steps, Context context) {
        mStepValues = new ArrayList<>(steps);
        mStepCallBack = (StepClickListener) context;
    }

    @NonNull
    @Override
    public RecipeDetailStepAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_list_detail_steps_content, viewGroup, false);
        return new RecipeDetailStepAdapter(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailStepAdapter recipeDetailStepAdapter, int position) {
        String aux = position == 0 ?
                mStepValues.get(position).getShort_description()
                : mStepValues.get(position).getFormattedShortDescription();
        recipeDetailStepAdapter.bind(aux);
        recipeDetailStepAdapter.itemView.setTag(position);
        recipeDetailStepAdapter.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mStepValues.size();
    }

    public static class RecipeDetailStepAdapter extends RecyclerView.ViewHolder{
        ViewDataBinding viewDataBinding;

        public RecipeDetailStepAdapter(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        private void bind(String stepName){
            viewDataBinding.setVariable(BR.stepName, stepName);
            viewDataBinding.executePendingBindings();
        }
    }
}
