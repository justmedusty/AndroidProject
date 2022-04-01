package com.cst2335.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.utilities.BaseNavActivity;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.RecipeData;

import java.util.ArrayList;
import java.util.Objects;

import com.cst2335.utilities.ApiService;

public class PopularActivity extends BaseNavActivity {

    ApiService apiService = new ApiService();
    ListAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        setupNavigation("Dustyn Gibb", "Popular Activity", "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.popular_activity);
        stub.inflate();

        recyclerView = findViewById(R.id.recycler);
        ArrayList<RecipeData> list = new ArrayList<>();

        adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PopularActivity.this));
        apiService.apiCall(null, adapter);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            Intent goToHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToHome);

        } else {
            Toast.makeText(getApplicationContext(),
                    "This is the popular page, you can view a list of the top recipes.",
                    Toast.LENGTH_LONG)
                    .show();
        }

        return true;
    }
}
