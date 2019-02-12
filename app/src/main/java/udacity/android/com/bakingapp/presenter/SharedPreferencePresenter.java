package udacity.android.com.bakingapp.presenter;

import android.content.Context;

import udacity.android.com.bakingapp.object.Recipe;

public interface SharedPreferencePresenter {
    void saveRecipe(Context context, Recipe recipe);
    Recipe getRecipeSaved(Context context);
}
