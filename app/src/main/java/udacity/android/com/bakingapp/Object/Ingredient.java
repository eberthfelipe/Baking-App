package udacity.android.com.bakingapp.Object;

// Ingredient of Recipe
public class Ingredient {

    private int quantity;
    private  String measure;
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
