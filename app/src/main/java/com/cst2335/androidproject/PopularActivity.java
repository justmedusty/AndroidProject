package com.cst2335.androidproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        ViewStub stub = findViewById(R.id.layout_stub);
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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("This page loads popular recipes , you may refresh to see more as well as click the bookmark icon on the right to both favourite and unfavourite recipes. You may click the recipe to see the details as well.")
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
