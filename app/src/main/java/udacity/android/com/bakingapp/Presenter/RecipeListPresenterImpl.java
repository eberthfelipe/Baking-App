package udacity.android.com.bakingapp.Presenter;

import android.content.Context;

import java.util.ArrayList;

import retrofit2.Call;
import udacity.android.com.bakingapp.Model.RetrofitConfig;
import udacity.android.com.bakingapp.Object.Recipe;
import udacity.android.com.bakingapp.Utils.NetworkUtils;
import udacity.android.com.bakingapp.View.RecipeView;

public class RecipeListPresenterImpl implements RecipeListPresenter {

    private RecipeView mRecipeView;

    public RecipeListPresenterImpl(RecipeView recipeView) {
        this.mRecipeView = recipeView;
    }

    @Override
    public void retrieveRecipesFromServer(Context context) {
        if(NetworkUtils.checkInternetConnection(context)){
            Call<Recipe []> recipeCall = new RetrofitConfig().getBakingService().getRecipes();
            recipeCall.enqueue(NetworkUtils.retrieveRecipes(this));
        } else {
            // TODO: implement no internet connection
        }
    }

    @Override
    public void parseRecipes(ArrayList<Recipe> recipes) {
        mRecipeView.parseRecipes(recipes);
    }

}
