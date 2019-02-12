package udacity.android.com.bakingapp.model;

import android.content.Context;

import udacity.android.com.bakingapp.object.Recipe;

public interface SharedPreferenceModel {

    void saveRecipe(Context context, Recipe recipe);
    Recipe getRecipeSaved(Context context);
}
