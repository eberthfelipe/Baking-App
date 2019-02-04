package udacity.android.com.bakingapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import udacity.android.com.bakingapp.BR;
import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.databinding.ActivityRecipeListBinding;
import udacity.android.com.bakingapp.object.Recipe;
import udacity.android.com.bakingapp.presenter.RecipeListPresenterImpl;
import udacity.android.com.bakingapp.presenter.RecipePreferenceImpl;
import udacity.android.com.bakingapp.widget.BakingWidget;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements RecipeView{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private static final String TAG = RecipeListActivity.class.getName();
    public static final String RECIPE_LIST = "recipe_list";
    private ActivityRecipeListBinding mActivityRecipeListBinding;
    private final int MY_PERMISSIONS_INTERNET = 0;
    private RecipeListPresenterImpl mRecipeListPresenter;
    private ArrayList<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if(savedInstanceState == null || savedInstanceState.isEmpty()){
            showProgress(true);
            getRecipes();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!savedInstanceState.isEmpty()){
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            setupRecyclerView(mActivityRecipeListBinding.recipeListIncluded.recipeList, mRecipes);
            showProgress(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mActivityRecipeListBinding != null && mActivityRecipeListBinding.recipeListIncluded.recipeList != null){
            if(mRecipes != null && !mRecipes.isEmpty()){
                outState.putParcelableArrayList(RECIPE_LIST, mRecipes);
                super.onSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_INTERNET:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getRecipes();
                } else {
                    //DONE: show a message to user instead of close the app
                    Toast.makeText(this, R.string.permissions, Toast.LENGTH_LONG).show();
                }

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ArrayList<Recipe> recipes) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipes));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private final ArrayList<Recipe> mRecipeValues;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Context context = view.getContext();
                Recipe recipe = mRecipeValues.get(position);
                Log.d(TAG, "onClick Recipe: " + recipe.toString());

                mParentActivity.updateWidget(context, recipe);

                Bundle arguments = new Bundle();
                arguments.putParcelable(RecipeDetailFragment.ARG_RECIPE, recipe);
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra(RecipeDetailFragment.ARG_RECIPE, arguments);
                context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      ArrayList<Recipe> recipeItems) {
            mRecipeValues = recipeItems;
            mParentActivity = parent;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_list_content, parent, false);
            return new ViewHolder(viewDataBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.bind(mRecipeValues.get(position).getName());
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mRecipeValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final ViewDataBinding viewDataBinding;

            ViewHolder(ViewDataBinding viewDataBinding) {
                super(viewDataBinding.getRoot());
                this.viewDataBinding = viewDataBinding;
            }

            private void bind(String recipeName){
                viewDataBinding.setVariable(BR.recipeName, recipeName);
                viewDataBinding.executePendingBindings();
            }
        }
    }

    public void init(){
        mActivityRecipeListBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);
        Toolbar toolbar = mActivityRecipeListBinding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecipeListPresenter = new RecipeListPresenterImpl(this);
    }

    public void showProgress(boolean show){
        if(show){
            mActivityRecipeListBinding.recipeListIncluded.setShow(false);
            mActivityRecipeListBinding.recipeListIncluded.viewLoadingRecipesIncluded.setShow(true);
        } else {
            mActivityRecipeListBinding.recipeListIncluded.setShow(true);
            mActivityRecipeListBinding.recipeListIncluded.viewLoadingRecipesIncluded.setShow(false);
        }
    }
    public void getRecipes(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            mRecipeListPresenter.retrieveRecipesFromServer(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, MY_PERMISSIONS_INTERNET);
        }
    }

    public void updateWidget(Context context, Recipe recipe){
        Intent intent = new Intent(context, BakingWidget.class);
        intent.setAction(BakingWidget.BAKING_WIDGET_UPDATE);
        if(recipe != null){
            RecipePreferenceImpl recipePreference = new RecipePreferenceImpl();
            recipePreference.saveRecipe(context, recipe);
        }
        context.sendBroadcast(intent);
    }

    //region RecipeView Interface

    @Override
    public void parseRecipes(ArrayList<Recipe> recipes) {
        mRecipes = new ArrayList<>(recipes);
        setupRecyclerView(mActivityRecipeListBinding.recipeListIncluded.recipeList, mRecipes);
        showProgress(false);
    }

    @Override
    public void showNoInternetConnection(boolean show) {
        mActivityRecipeListBinding.setNoInternet(show);
    }

    //endregion
}
