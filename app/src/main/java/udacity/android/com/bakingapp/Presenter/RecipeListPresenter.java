package udacity.android.com.bakingapp.Presenter;

import android.content.Context;

import java.util.ArrayList;

import udacity.android.com.bakingapp.Object.Recipe;

public interface RecipeListPresenter {

    ArrayList<Recipe> retrieveRecipesFromServer(Context context);
}
