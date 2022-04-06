package com.cst2335.androidproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
 * The type Search activity.
 */
public class SearchActivity extends BaseNavActivity {
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;
    /**
     * The Adapter.
     */
    ListAdapter adapter;
    /**
     * The Api.
     */
    ApiService api = new ApiService();
    /**
     * The Progress bar.
     */
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);
        setupNavigation("Lucas Ross", "Search Activity", "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.search_activity);
        stub.inflate();

        progressBar = findViewById(R.id.progressBar2);
        ImageButton searchButton = findViewById(R.id.searchButton);
        EditText editText = findViewById(R.id.searchActivityRowTitle);
        searchButton.setOnClickListener(view -> {
            if (editText.getText() != null) {
                api.apiCall(editText.getText().toString(), adapter, progressBar);
            }
        });
        editText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && editText.getText() != null) {
                api.apiCall(editText.getText().toString(), adapter, progressBar);
                return true;
            } else {
                return false;
            }
        });

        ArrayList<RecipeData> list = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        //api.apiCall(null, adapter);


    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(("To use the Search activity you must enter search terms in the text field provided. " +
                "Submitting the search terms will produce a list of recipes that match the search terms. " +
                "Once a list is produced you can click on an item in the list to view details of that list item." +
                " Clicking on the bookmark or favourite button will prompt whether you want to add that item to favourites or not. " +
                " Use the nav drawer to navigate to other activities"))
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