package udacity.android.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.object.Ingredient;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    public static final String BAKING_WIDGET_UPDATE = "udacity.android.com.bakingapp.recipe.ingredients";
    public static final String BAKING_WIDGET_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String BAKING_WIDGET_RECIPE_INGREDIENTS_LIST = "recipe_ingredients_list";
    private static ArrayList<Ingredient> mIngredientValuesWidget;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.putParcelableArrayListExtra(BAKING_WIDGET_RECIPE_INGREDIENTS_LIST, mIngredientValuesWidget);
        views.setRemoteAdapter(R.id.appwidget_list, intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_list);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent;
        RemoteViews remoteViews;
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_list_item);
            intent = new Intent(context, BakingWidgetService.class);
            intent.putParcelableArrayListExtra(BAKING_WIDGET_RECIPE_INGREDIENTS_LIST, mIngredientValuesWidget);
            remoteViews.setRemoteAdapter(R.id.appwidget_list, intent);

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
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
        mIngredientValuesWidget = intent.hasExtra(BAKING_WIDGET_RECIPE_INGREDIENTS_LIST)
                ? intent.<Ingredient>getParcelableArrayListExtra(BAKING_WIDGET_RECIPE_INGREDIENTS_LIST)
                : null;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] views = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidget.class));
        this.onUpdate(context, appWidgetManager, views);
    }

}

