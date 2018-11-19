package udacity.android.com.bakingapp.Presenter;

import android.content.Context;

import java.util.ArrayList;

import retrofit2.Call;
import udacity.android.com.bakingapp.Model.RetrofitConfig;
import udacity.android.com.bakingapp.Object.Recipe;
import udacity.android.com.bakingapp.Utils.NetworkUtils;

public class RecipeListPresenterImpl implements RecipeListPresenter {

    @Override
    public ArrayList<Recipe> retrieveRecipesFromServer(Context context) {
        if(NetworkUtils.checkInternetConnection(context)){
            Call<Recipe []> recipeCall = new RetrofitConfig().getBakingService().getRecipes();
            recipeCall.enqueue(NetworkUtils.retrieveRecipes());
        } else {
            // TODO: implement no internet connection
        }
        return null;
    }

}
