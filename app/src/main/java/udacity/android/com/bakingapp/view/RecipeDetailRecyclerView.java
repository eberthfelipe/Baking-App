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
import udacity.android.com.bakingapp.object.Ingredient;

public class RecipeDetailRecyclerView extends RecyclerView.Adapter<RecipeDetailRecyclerView.RecipeDetailViewHolder> {

    private ArrayList<Ingredient> mIngredientValues;

    public RecipeDetailRecyclerView(ArrayList<Ingredient> ingredients) {
        mIngredientValues = new ArrayList<>(ingredients);
    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_list_detail_content, viewGroup, false);
        return new RecipeDetailViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder recipeDetailViewHolder, int position) {
        recipeDetailViewHolder.bind(mIngredientValues.get(position).toString());
        recipeDetailViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mIngredientValues.size();
    }

    public static class RecipeDetailViewHolder extends RecyclerView.ViewHolder{
        ViewDataBinding viewDataBinding;

        public RecipeDetailViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        private void bind(String ingredientName){
            viewDataBinding.setVariable(BR.ingredientName, ingredientName);
            viewDataBinding.executePendingBindings();
        }
    }
}
