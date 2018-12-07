package udacity.android.com.bakingapp.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import udacity.android.com.bakingapp.Utils.BakingJsonUtils;

// Ingredient of Recipe
public class Ingredient implements Parcelable {

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

    //region Parcelable Ingredient
    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
    //endregion
}
