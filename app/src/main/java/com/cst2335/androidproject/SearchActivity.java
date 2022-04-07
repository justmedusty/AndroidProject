package com.cst2335.androidproject;
/*
File: SearchActivity.java
Author: Lucas Ross
Lab Section: 012
Date: April 7, 2022
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cst2335.utilities.ApiService;
import com.cst2335.utilities.BaseNavActivity;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.RecipeData;

import java.util.ArrayList;
import java.util.List;

/**
 * The search activity is responsible for allowing the user to enter keywords or recipe names in the search field,
 * and returns a list of recipes based on the entered terms. The activity also uses shared preferences to store
 * the most recent search term and display the results when the activity is opened again.
 */
public class SearchActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);
        setupNavigation("Lucas Ross", getString(R.string.search_activity_title), "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.search_activity);
        stub.inflate();

        /*
        Creates and stores references for activity resources (Shared Preferences, Editor, ApiService, ArrayList,
        RecyclerView, ListAdapter, ProgressBar, ImageButton, and EditText.
         */
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        ApiService api = new ApiService();
        ArrayList<RecipeData> list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ListAdapter adapter = new ListAdapter(list, getApplication());
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        ImageButton searchButton = findViewById(R.id.searchButton);
        EditText editText = findViewById(R.id.searchActivityRowTitle);

        /*
        Sets RecyclerView resources for this activity (ListAdapter and LayoutManager)
         */
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        /*
        Creates search button OnClickListener
         */
        searchButton.setOnClickListener(view -> {
            if (editText.getText() != null) {
                api.apiCall(editText.getText().toString(), adapter, progressBar);
                editor.putString("searchString", editText.getText().toString())
                        .apply();
            }
        });

        /*
        Creates "ENTER" search field OnKeyListener
         */
        editText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && editText.getText() != null) {
                api.apiCall(editText.getText().toString(), adapter, progressBar);
                editor.putString("searchString", editText.getText().toString())
                        .apply();
                return true;
            } else {
                return false;
            }
        });

        /*
        Automatically fills search field from most recent search and
        sends an api call on activity startup.
         */
        editText.setText(pref.getString("searchString", null));
        if (editText != null) {
            api.apiCall(editText.getText().toString(), adapter, progressBar);
        }
    }

    /**
     * Default parent functionality when user presses the back button.
     */
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Handles functionality for user pressing the info button, displaying an alert dialog containing
     * a basic description of this activity's functionality and purpose.
     *
     * @param item The info ImageButton selected in the toolbar.
     * @return boolean value, not used anywhere.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((getString(R.string.search_info_dialog)))
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