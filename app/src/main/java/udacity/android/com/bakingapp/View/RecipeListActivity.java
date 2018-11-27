package udacity.android.com.bakingapp.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import udacity.android.com.bakingapp.Object.Recipe;
import udacity.android.com.bakingapp.Presenter.RecipeListPresenterImpl;
import udacity.android.com.bakingapp.R;
import udacity.android.com.bakingapp.dummy.DummyContent;

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
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private LinearLayout mLoadingView;
    private final int MY_PERMISSIONS_INTERNET = 0;
    private RecipeListPresenterImpl mRecipeListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //TODO: Replace findViewById for dataBinding
        mRecyclerView = findViewById(R.id.recipe_list);
        mLoadingView = findViewById(R.id.ll_loading_recipes);
        assert mRecyclerView != null && mLoadingView != null;
        showProgress(true);
//        setupRecyclerView((RecyclerView) recyclerView);

        mRecipeListPresenter = new RecipeListPresenterImpl(this);
        getRecipes();
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
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipes, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private final ArrayList<Recipe> mRecipeValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: replace on click in DummyItem, fatal exception
//                java.lang.ClassCastException: udacity.android.com.bakingapp.Object.Recipe cannot be cast to udacity.android.com.bakingapp.dummy.DummyContent$DummyItem
//                at udacity.android.com.bakingapp.View.RecipeListActivity$SimpleItemRecyclerViewAdapter$1.onClick(RecipeListActivity.java:116)
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, item.id);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      ArrayList<Recipe> recipeItems,
                                      boolean twoPane) {
            mRecipeValues = recipeItems;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.mRecipeTitle.setText(mRecipeValues.get(position).getName());
            holder.itemView.setTag(mRecipeValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mRecipeValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mRecipeTitle;

            ViewHolder(View view) {
                super(view);
                mRecipeTitle = view.findViewById(R.id.tv_recipe_title);
            }
        }
    }

    public void showProgress(boolean show){
        if(show){
            mRecyclerView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.GONE);
        }
    }
    public void getRecipes(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            mRecipeListPresenter.retrieveRecipesFromServer(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, MY_PERMISSIONS_INTERNET);
        }
    }

    //region RecipeView Interface

    @Override
    public void parseRecipes(ArrayList<Recipe> recipes) {
        setupRecyclerView(mRecyclerView, recipes);
        showProgress(false);
    }

    //endregion
}
