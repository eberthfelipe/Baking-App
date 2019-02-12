package udacity.android.com.bakingapp.presenter;

import android.content.Context;

import udacity.android.com.bakingapp.model.RecipePreference;
import udacity.android.com.bakingapp.model.SharedPreferenceModel;
import udacity.android.com.bakingapp.object.Recipe;

public class RecipePreferenceImpl implements SharedPreferencePresenter{

    private SharedPreferenceModel mSharedPreferenceModel;

    public RecipePreferenceImpl() {
        this.mSharedPreferenceModel = new RecipePreference();
    }

    @Override
    public void saveRecipe(Context context, Recipe recipe) {
        mSharedPreferenceModel.saveRecipe(context, recipe);
    }

    @Override
    public Recipe getRecipeSaved(Context context) {
        return mSharedPreferenceModel.getRecipeSaved(context);
    }
}
