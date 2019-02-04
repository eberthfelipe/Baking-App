package udacity.android.com.bakingapp.presenter;

import android.content.Context;

import java.util.ArrayList;

import retrofit2.Call;
import udacity.android.com.bakingapp.model.RetrofitConfig;
import udacity.android.com.bakingapp.object.Recipe;
import udacity.android.com.bakingapp.utils.NetworkUtils;
import udacity.android.com.bakingapp.view.RecipeView;

public class RecipeListPresenterImpl implements RecipeListPresenter {

    private RecipeView mRecipeView;

    public RecipeListPresenterImpl(RecipeView recipeView) {
        this.mRecipeView = recipeView;
    }

    @Override
    public void retrieveRecipesFromServer(Context context) {
        if(NetworkUtils.checkInternetConnection(context)){
            mRecipeView.showNoInternetConnection(false);
            Call<Recipe []> recipeCall = new RetrofitConfig().getBakingService().getRecipes();
            recipeCall.enqueue(NetworkUtils.retrieveRecipes(this));
        } else {
            mRecipeView.showNoInternetConnection(true);
        }
    }

    @Override
    public void parseRecipes(ArrayList<Recipe> recipes) {
        mRecipeView.parseRecipes(recipes);
    }

}
