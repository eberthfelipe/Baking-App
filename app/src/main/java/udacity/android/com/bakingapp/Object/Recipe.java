package udacity.android.com.bakingapp.Object;

import java.util.Arrays;

public class Recipe {

    // Baking fields
    private int id;
    private String name;
    private Ingredient[] ingredients;
    private Step[] steps;
    private int servings;
    private String image;

    public Recipe() {
        id = 0;
        name = "";
        ingredients = new Ingredient[0];
        steps = new Step[0];
        servings = 0;
        image = "";
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", steps=" + Arrays.toString(steps) +
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

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
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
