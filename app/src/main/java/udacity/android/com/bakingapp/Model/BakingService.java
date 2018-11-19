package udacity.android.com.bakingapp.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import udacity.android.com.bakingapp.Object.Recipe;

public interface BakingService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Recipe []> getRecipes();
}
