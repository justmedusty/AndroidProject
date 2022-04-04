package com.cst2335.androidproject;
/*
File: BaseNavActivity.java
Author: Chad Rocheleau
Lab Section: 012
Date: March 24 2022
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.utilities.BaseNavActivity;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.RecipeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cst2335.utilities.*;

/**
 *
 */
public class FavouritesActivity extends BaseNavActivity {

    RecyclerView recyclerView;
    ListAdapter adapter;
    Context context;
    DatabaseHelper databaseHelper;
    ArrayList<RecipeData> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        // initialize toolbar and navigation drawer with custom header info for nav drawer
        setupNavigation("Chad Rocheleau", "Favourites Activity", "1.0");

        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.favourites_activity);
        stub.inflate();

        initRecyclerList();
        ListViewHolder.setIsPhone(findViewById(R.id.recipe_details_fragment) == null);
        FragmentManager fm = getSupportFragmentManager(); // get fragment manager

        if (findViewById(R.id.recipe_details_fragment) != null &&
                findViewById(R.id.recipe_details_layout ) != null) {
            SplashFragment splash = new SplashFragment();
            fm.beginTransaction()
                    .replace(R.id.recipe_details_fragment, splash)
                    .commit();
        }

        if (!getIntent().getBooleanExtra("isPhone", true)) {
            RecipeDetailsFragment recipeDetails = new RecipeDetailsFragment();
            fm.beginTransaction()
                    .replace(R.id.recipe_details_fragment, recipeDetails)
                    .commit();
        }


    } // end onCreate()

    /**
     *
     */
    public void initRecyclerList() {

        adapter = new ListAdapter(list, getApplication());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouritesActivity.this));
        databaseHelper = new DatabaseHelper(getApplicationContext(), null, null, DatabaseHelper.VERSION);
        Cursor cursor = databaseHelper.selectAll();
        recyclerView.setAdapter(adapter);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE));
                    @SuppressLint("Range") String ingredients = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_INGREDIENTS));
                    @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_URL));

                    list.add(new RecipeData(title, ingredients, url));

                } while (cursor.moveToNext());
                cursor.close();
            }
        }

        adapter.setList(list);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(("To use the favourites activity you can click on a favourited item to view the details." +
                "Clicking on the bookmark button will prompt for confirmation that you want to delete an item from favourites. " +
                "Use the navigation drawer to navigate to other activities." ))
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return false;
    }
}
