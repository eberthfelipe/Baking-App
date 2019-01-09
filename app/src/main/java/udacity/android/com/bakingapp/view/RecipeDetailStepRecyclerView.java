package udacity.android.com.bakingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import udacity.android.com.bakingapp.BR;
import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.object.Step;

public class RecipeDetailStepRecyclerView extends RecyclerView.Adapter<RecipeDetailStepRecyclerView.RecipeDetailStepAdapter>{

    private ArrayList<Step> mStepValues;

    public RecipeDetailStepRecyclerView(ArrayList<Step> steps) {
        mStepValues = new ArrayList<>(steps);
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
        recipeDetailStepAdapter.bind(mStepValues.get(position).getShort_description());
        recipeDetailStepAdapter.itemView.setTag(position);
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
