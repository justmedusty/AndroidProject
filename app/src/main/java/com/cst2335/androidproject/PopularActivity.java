package com.cst2335.androidproject;

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
import com.cst2335.utilities.ApiService;

public class PopularActivity extends BaseNavActivity {

    ApiService apiService = new ApiService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        setupNavigation("Dustyn Gibb", "Popular Activity", "1.0");
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.popular_activity);
        stub.inflate();
        List<RecipeData> list = new ArrayList<>();
        for(int i=0; i<=100; i++) {
            list.add(new RecipeData("Title"+i, "Ingredient"+i, "url"+i));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ListAdapter adapter = new ListAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PopularActivity.this));

    }
}
