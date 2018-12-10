package udacity.android.com.bakingapp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import udacity.android.com.bakingapp.object.Recipe;

public interface BakingService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Recipe []> getRecipes();
}
