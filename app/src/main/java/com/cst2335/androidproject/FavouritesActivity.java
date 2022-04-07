package com.cst2335.androidproject;
/*
File: BaseNavActivity.java
Author: Chad Rocheleau
Lab Section: 012
Date: March 24 2022
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewStub;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cst2335.utilities.BaseNavActivity;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.RecipeData;
import java.util.ArrayList;
import java.util.Objects;

import com.cst2335.utilities.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This activity loads a list of saved recipes that have been bookmarked as favourites. It loads
 * the saved recipes from a database. It is the main activity of the application and is launched
 * on startup.
 */
public class FavouritesActivity extends BaseNavActivity {

    /**
     *
     */
    private RecyclerView recyclerView;

    /**
     *
     */
    private ListAdapter adapter = null;

    /**
     *
     */
    RecipeDetailsFragment recipeDetails;
    /**
     *
     */
    private DatabaseHelper databaseHelper;

    /**
     *
     */
    private ArrayList<RecipeData> list = new ArrayList<>();



    /**
     * Overrides onCreate to build activity specific behavior and load activity specific
     * layouts.
     * @param savedInstanceState Bundle passed to this activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        // initialize toolbar and navigation drawer with custom header info for nav drawer
        setupNavigation("Chad Rocheleau", getString(R.string.favourites_activity_title), "1.0");

        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.favourites_activity);
        stub.inflate();

        initRecyclerList();

        ListViewHolder.setIsPhone(findViewById(R.id.recipe_details_fragment) == null);
        FragmentManager fm = getSupportFragmentManager(); // get fragment manager

        if (findViewById(R.id.recipe_details_fragment) != null ) {
            SplashFragment splash = new SplashFragment();
            fm.beginTransaction()
                    .replace(R.id.recipe_details_fragment, splash)
                    .commit();
        }

    } // end onCreate()

    /**
     * Contains all list creation code to be called in onCreate. Simply removes and isolates the
     * code related to populating the recycler list view in this activity. Loads the Recipes from
     * the database of saved or favourited recipes.
     */
    public void initRecyclerList() {

        adapter = new ListAdapter(list, getApplication());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouritesActivity.this));

        recyclerView.setAdapter(adapter);
        refreshListDataUpdate(adapter);

    }

    /**
     *
     * @param adapter
     */
    public void refreshListDataUpdate(ListAdapter adapter) {
        databaseHelper = new DatabaseHelper(getApplicationContext(), null, null, DatabaseHelper.VERSION);
        Cursor cursor = databaseHelper.selectAll();
        list.clear();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE));
                    @SuppressLint("Range") String ingredients = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_INGREDIENTS));
                    @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_URL));

                    list.add(new RecipeData(title, ingredients, url, true));

                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        adapter.setRecipeList(list);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * When back button is used then the list needs to be updated
     */
    @Override
    public void onResume() {
        super.onResume();
        refreshListDataUpdate(adapter);
    }

    /**
     * Overrides onOptionsItemSelected to provide custom functionality of the info button in the
     * toolbar for this activity.
     * @param item the item selected in the toolbar
     * @return boolean value expected .
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((getString(R.string.favourites_info_message)))
                .setCancelable(false)
                .setPositiveButton(R.string.okay_dialog_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return false;
    }
}
