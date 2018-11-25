package udacity.android.com.bakingapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.android.com.bakingapp.Object.Recipe;
import udacity.android.com.bakingapp.Presenter.RecipeListPresenter;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getName();

    /**
     * Method to verify connection of device
     * @param context application
     * @return true if internet is available
     */
    public static boolean checkInternetConnection(Context context){
        boolean isConnected;
        NetworkInfo activeNetwork = null;
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null){
            activeNetwork = cm.getActiveNetworkInfo();
        }
        isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "checkInternetConnection: " + isConnected);
        return isConnected;
    }

    /**
     * Method to get the return of recipes requested to server
     * @return recipeCallback
     */
    public static Callback<Recipe []> retrieveRecipes(final RecipeListPresenter recipeListPresenter) {
        Callback<Recipe []> recipeCallback = new Callback<Recipe[]>() {
            @Override
            public void onResponse(@NonNull Call<Recipe[]> call, Response<Recipe[]> response) {
                Recipe[] recipe = response.body();
                Log.d(TAG, "onResponse: " + response.toString());
                assert recipe != null;
                parseRecipes(recipeListPresenter, recipe);
            }

            @Override
            public void onFailure(@NonNull Call<Recipe[]> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        };
        Log.d(TAG, "retrieveRecipes: " + recipeCallback.toString());
        return recipeCallback;
    }

    private static void parseRecipes(RecipeListPresenter recipeListPresenter, Recipe recipe[]){
        ArrayList<Recipe> recipesArrayList = new ArrayList<>();
        for (Recipe recipeAux: recipe) {
            Log.d(TAG, "RECIPE: " + recipeAux.toString());
            recipesArrayList.add(recipeAux);
        }
        recipeListPresenter.parseRecipes(recipesArrayList);
    }
}
