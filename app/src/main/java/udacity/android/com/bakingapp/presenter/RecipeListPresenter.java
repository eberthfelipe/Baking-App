package udacity.android.com.bakingapp.presenter;

import android.content.Context;

import java.util.ArrayList;

import udacity.android.com.bakingapp.object.Recipe;

public interface RecipeListPresenter {

    void retrieveRecipesFromServer(Context context);
    void parseRecipes(ArrayList<Recipe> recipes);
}
