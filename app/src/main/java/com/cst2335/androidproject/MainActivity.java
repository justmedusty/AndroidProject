package com.cst2335.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewStub;

import androidx.fragment.app.FragmentManager;

import com.cst2335.utilities.BaseNavActivity;

/**
 * Application launch screen. Will contain title screen and a posting of the last recipe saved
 * to favourites showing the details of that recipe to the user.
 */
public class MainActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);//setting base layout

        FragmentManager fm = getSupportFragmentManager(); // get fragment manager

        //****************** set up tool bar and nav drawer and load this activities layout into stub

        setupNavigation("Chad Rocheleau", "Main Activity", "1.0");
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_main);
        stub.inflate();

        // *********** if on tablet load the fragment.
        if (!getIntent().getBooleanExtra("isPhone", true)) {
            RecipeDetailsFragment recipeDetails = new RecipeDetailsFragment();
            fm.beginTransaction()
                    .replace(R.id.recipe_details_fragment, recipeDetails)
                    .commit();
        }
        //Log.d("main", "" + isPhone);
    }


}