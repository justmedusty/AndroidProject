package com.cst2335.androidproject;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.EditText;

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

public class SearchActivity extends BaseNavActivity {
    RecyclerView recyclerView;
    ListAdapter adapter;
    ApiService api = new ApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);
        setupNavigation("Lucas Ross", "Search Activity", "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.search_activity);
        stub.inflate();

        ImageButton searchButton = findViewById(R.id.searchButton);
        EditText editText = findViewById(R.id.searchActivityRowTitle);
        searchButton.setOnClickListener(view -> {
            if (editText.getText() != null) {
                api.apiCall(editText.getText().toString(), adapter);
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

            Toast.makeText(getApplicationContext(),
                    "This is the search page, please enter key words or recipe titles into the search field" +
                            "to find a lit of recipes matching your search terms.",
                    Toast.LENGTH_LONG)
                    .show();


        return true;
    }
}