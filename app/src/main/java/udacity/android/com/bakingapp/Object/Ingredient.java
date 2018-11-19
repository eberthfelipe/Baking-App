package udacity.android.com.bakingapp.Object;

import com.fasterxml.jackson.annotation.JsonProperty;

import udacity.android.com.bakingapp.Utils.BakingJsonUtils;

// Ingredient of Recipe
public class Ingredient {

    @JsonProperty(BakingJsonUtils.JSON_INGREDIENTS_QUANTITY)
    private int quantity;
    @JsonProperty(BakingJsonUtils.JSON_INGREDIENTS_MEASURE)
    private  String measure;
    @JsonProperty(BakingJsonUtils.JSON_INGREDIENTS_INGREDIENT)
    private  String ingredient;

    public Ingredient() {
        quantity = 0;
        measure = "";
        ingredient = "";
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

    //region GET and SET

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    //endregion
}
