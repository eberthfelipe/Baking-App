package udacity.android.com.bakingapp.Object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import udacity.android.com.bakingapp.Utils.BakingJsonUtils;

public class Recipe {

    // Baking fields
    @JsonProperty(BakingJsonUtils.JSON_ID)
    private int id;
    @JsonProperty(BakingJsonUtils.JSON_NAME)
    private String name;
    @JsonProperty(BakingJsonUtils.JSON_INGREDIENTS)
    private List<Ingredient> ingredients;
    @JsonProperty(BakingJsonUtils.JSON_STEPS)
    private List<Step> steps;
    @JsonProperty(BakingJsonUtils.JSON_SERVINGS)
    private int servings;
    @JsonProperty(BakingJsonUtils.JSON_IMAGE)
    private String image;

    public Recipe() {
        id = 0;
        name = "";
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        servings = 0;
        image = "";
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + Arrays.toString(new List[]{ingredients}) +
                ", steps=" + Arrays.toString(new List[]{steps}) +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }

    //region GET and SET

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //endregion
}
