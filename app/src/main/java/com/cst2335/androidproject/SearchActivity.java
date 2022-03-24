package com.cst2335.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseNavActivity {
    RecyclerView recyclerView;
    ListAdapter adapter;
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
        searchButton.setOnClickListener(view -> {
        });

        List<RecipeData> list = new ArrayList<>();
        for(int i=0; i<=100; i++) {
            list.add(new RecipeData("Title"+i, "Ingredient"+i, "url"+i));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));


    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    //TODO Hey Lucas I commented this out cause I think it might break the new setup
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }

    /*public void APISearch(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.prepare("GET", "https://tasty.p.rapidapi.com/recipes/auto-complete?prefix=chicken%20soup")
                .setHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
                .setHeader("X-RapidAPI-Key", "80beee46d0msh7e059b94b4743d4p1c199ajsn49a63a0f5b76")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }*/
}