package udacity.android.com.bakingapp.Model;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * class to configure and instantiate Retrofit
 */
public class RetrofitConfig {

    private final String TAG = RetrofitConfig.class.getName();
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        Log.d(TAG, "RetrofitConfig: " + retrofit.toString());
    }

    public BakingService getBakingService(){
        return this.retrofit.create(BakingService.class);
    }
}
