package udacity.android.com.bakingapp.Utils;

/**
 * class helper to parse json objects trough Jackson
 */
public class BakingJsonUtils {

    // Baking fields
    public static final String JSON_ID = "id";
    public static final String JSON_NAME = "name";
    public static final String JSON_INGREDIENTS = "ingredients";
    public static final String JSON_STEPS = "steps";
    public static final String JSON_SERVINGS = "servings";
    public static final String JSON_IMAGE = "image";

    //region Ingredients child
    public static final String JSON_INGREDIENTS_QUANTITY = "quantity";
    public static final String JSON_INGREDIENTS_MEASURE = "measure";
    public static final String JSON_INGREDIENTS_INGREDIENT = "ingredient";
    //endregion

    //region Steps child
    public static final String JSON_STEPS_ID = "id";
    public static final String JSON_STEPS_SHORT_DESCRIPTION = "shortDescription";
    public static final String JSON_STEPS_DESCRIPTION = "description";
    public static final String JSON_STEPS_VIDEO_URL = "videoURL";
    public static final String JSON_STEPS_THUMBNAIL_URL = "thumbnailURL";
    //endregion
}
