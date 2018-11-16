package udacity.android.com.bakingapp.Utils;

public class BakingJsonUtils {

    private static final String TAG = BakingJsonUtils.class.getName();

    // Baking fields
    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_INGREDIENTS = "ingredients";
    private static final String JSON_STEPS = "steps";
    private static final String JSON_SERVINGS = "servings";
    private static final String JSON_IMAGE = "image";

    //region Ingredients child
    private static final String JSON_INGREDIENTS_QUANTITY = "quantity";
    private static final String JSON_INGREDIENTS_MEASURE = "measure";
    private static final String JSON_INGREDIENTS_INGREDIENT = "ingredient";
    //endregion

    //region Steps child
    private static final String JSON_STEPS_ID = "id";
    private static final String JSON_STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_STEPS_DESCRIPTION = "description";
    private static final String JSON_STEPS_VIDEO_URL = "videoURL";
    private static final String JSON_STEPS_THUMBNAIL_URL = "thumbnailURL";
    //endregion
}
