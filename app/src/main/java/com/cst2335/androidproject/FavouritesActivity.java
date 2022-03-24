package com.cst2335.androidproject;

import android.os.Bundle;
import android.view.ViewStub;
import androidx.annotation.Nullable;

public class FavouritesActivity extends BaseNavActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        setupNavigation();
        // get the ViewStub into which this activities layout will be loaded.
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.favourites_activity);
        stub.inflate();
    }
}
