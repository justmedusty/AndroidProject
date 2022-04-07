package com.cst2335.androidproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ProgressBar;
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

/**
 * The type Popular activity.
 */
public class PopularActivity extends BaseNavActivity {

    /**
     * The Api service.
     */
    ApiService apiService = new ApiService();
    /**
     * The Adapter.
     */
    ListAdapter adapter;
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;
    /**
     * The Progress bar.
     */
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);
        progressBar = findViewById(R.id.progressBar);

        setupNavigation("Dustyn Gibb", getString(R.string.popular_activity_title), "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.popular_activity);
        stub.inflate();

        recyclerView = findViewById(R.id.recycler);
        ArrayList<RecipeData> list = new ArrayList<>();

        adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PopularActivity.this));
        progressBar = this.findViewById(R.id.progressBar);
        apiService.apiCall(null, adapter,progressBar);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.popular_info_message))
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
