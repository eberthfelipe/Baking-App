package udacity.android.com.bakingapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import udacity.android.com.bakingapp.object.Recipe;
import udacity.android.com.bakingapp.widget.BakingWidget;


public class RecipePreference implements SharedPreferenceModel {

    private static final String TAG = RecipePreference.class.getName();
    @Override
    public void saveRecipe(Context context, Recipe recipe) {
        //Reference: https://github.com/Annin7y/BakingApp
        //Store Ingredients in SharedPreferences
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefsEditor.putString(BakingWidget.BAKING_WIDGET_RECIPE_INGREDIENTS_LIST, json);
        prefsEditor.apply();
    }

    @Override
    public Recipe getRecipeSaved(Context context) {
        //code structure based on this link:
        //https://stackoverflow.com/questions/37927113/how-to-store-and-retrieve-an-object-from-gson-in-android
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Recipe recipe;
        Gson gson = new Gson();
        Type type = new TypeToken<Recipe>() {}.getType();
        String gsonString = sharedPreferences.getString(BakingWidget.BAKING_WIDGET_RECIPE_INGREDIENTS_LIST, "");
        recipe = gson.fromJson(gsonString, type);
        Log.d(TAG, "getRecipeSaved: " + recipe.toString());
        return recipe;
    }
}
