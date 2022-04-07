package com.cst2335.androidproject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cst2335.utilities.BaseNavActivity;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.RecipeData;
import java.util.ArrayList;
import java.util.Objects;

import com.cst2335.utilities.ApiService;

/*
File: PopularActivity.java
Author: Dustyn Gibb
Lab Section: 012
Date: April 7th 20222
 */

/**
 * The Popular activity class, for loading a list of currently popular recipes. Recipes can be favourited from the popular
 * page and will then show up in the favourites activity
 */
public class PopularActivity extends BaseNavActivity {

    /**
     * The Api service object
     */
    private final ApiService apiService = new ApiService();

    /**
     * Method called when activity is created
     *
     * @param savedInstanceState Bundle passed to this activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);
        /*
          The Progress bar.
         */
        ProgressBar progressBar = findViewById(R.id.progressBar);

        setupNavigation("Dustyn Gibb", getString(R.string.popular_activity_title), "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.popular_activity);
        stub.inflate();

        /*
          The Recycler view.
         */
        RecyclerView recyclerView = findViewById(R.id.recycler);
        ArrayList<RecipeData> list = new ArrayList<>();

        /*
          The Adapter.
         */
        ListAdapter adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PopularActivity.this));
        progressBar = this.findViewById(R.id.progressBar);

        apiService.apiCall(null, adapter, progressBar);

        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();


    }

    /**
     * Overrides onOptionsItemSelected to provide custom functionality of the info button in the
     * toolbar for this activity.
     *
     * @param item the item selected in the toolbar
     * @return boolean value
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.popular_info_message))
                .setCancelable(false)
                .setPositiveButton(R.string.okay_dialog_button, (dialog, id) -> {

                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return false;
    }
}
