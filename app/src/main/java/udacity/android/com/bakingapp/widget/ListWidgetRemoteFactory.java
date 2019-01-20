package udacity.android.com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.object.Ingredient;

public class ListWidgetRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = ListWidgetRemoteFactory.class.getName();
    private Context mContext;
    private List<Ingredient> mWidgetItems = new ArrayList<>();
    private ArrayList<Ingredient> mIngredientValuesWidget;

    public ListWidgetRemoteFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        this.mIngredientValuesWidget = intent.getParcelableArrayListExtra(BakingWidget.BAKING_WIDGET_RECIPE_INGREDIENTS_LIST);
    }

    @Override
    public void onCreate() {
        updateView();
    }

    @Override
    public void onDataSetChanged() {
        updateView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientValuesWidget == null ? 0 : mIngredientValuesWidget.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(mWidgetItems == null){
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.widget_tv_ingredient_list_content, mWidgetItems.get(i).toString());
        Log.d(TAG, "getViewAt ingredient: " + mWidgetItems.get(i).toString());
        return  remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void updateView(){
        mWidgetItems = new ArrayList<>(mIngredientValuesWidget);
        Log.d(TAG, "updatewidget ingredient: " + mWidgetItems.toString());
    }
}
