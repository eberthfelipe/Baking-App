package udacity.android.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.object.Ingredient;
import udacity.android.com.bakingapp.object.Recipe;
import udacity.android.com.bakingapp.presenter.RecipePreferenceImpl;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    public static final String BAKING_WIDGET_UPDATE = "udacity.android.com.bakingapp.widget.UPDATE";
    public static final String BAKING_WIDGET_RECIPE_INGREDIENTS_LIST = "recipe_ingredients_list";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views;
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            Toast.makeText(context, "onUpdate WIDGET", Toast.LENGTH_SHORT).show();
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(BAKING_WIDGET_UPDATE.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), BakingWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            Toast.makeText(context, "onRECEIVE WIDGET", Toast.LENGTH_SHORT).show();

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);
            for (int appWidgetId: appWidgetIds) {
                updateWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    // Reference: https://github.com/djkovrik/BakingApp
    public void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        views.removeAllViews(R.id.appwidget_list);
        RemoteViews ingredientView;

        RecipePreferenceImpl recipePreference = new RecipePreferenceImpl();
        Recipe recipe =  recipePreference.getRecipeSaved(context);
        if(recipe != null){
            views.setTextViewText(R.id.widget_tv_recipe_name, recipe.getName());
            for (Ingredient ingredient: recipe.getIngredients()) {
                ingredientView = new RemoteViews(context.getPackageName(),
                        R.layout.widget_list_item);

                ingredientView.setTextViewText(R.id.widget_tv_ingredient_list_content, ingredient.toString());
                views.addView(R.id.appwidget_list, ingredientView);
            }
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}

