package com.cst2335.androidproject;
/*
File: BaseNavActivity.java
Author: Chad Rocheleau
Lab Section: 012
Date: March 24 2022
 */
import android.os.Bundle;
import android.view.ViewStub;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.utilities.BaseNavActivity;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.RecipeData;

import java.util.ArrayList;
import java.util.List;
import com.cst2335.utilities.*;

/**
 *
 */
public class FavouritesActivity extends BaseNavActivity {

    RecyclerView recyclerView;
    ListAdapter adapter;
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

        // TODO replace this with the code necessary to load the list with actual recipe data
        //      from the database.
        List<RecipeData> list = new ArrayList<>();
        for(int i=0; i<=100; i++) {
            list.add(new RecipeData("Title"+i, "Ingredient"+i, "url"+i));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouritesActivity.this));

    }

    public void onBackPressed(){
        super.onBackPressed();
    }
}
