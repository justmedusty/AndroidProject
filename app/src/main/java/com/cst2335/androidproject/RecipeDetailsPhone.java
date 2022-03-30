package com.cst2335.androidproject;

import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.ViewStub;
import com.cst2335.utilities.BaseNavActivity;

/**
 * When on a phone and a user wants to view recipe details this
 * is the activity that is used to display recipe details for
 * as specific recipe. It makes use of the same layout as
 * the tablet does for displaying recipe details
 * layout_fragment_recipe_details.
 */
public class RecipeDetailsPhone extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        setupNavigation("Chad Rocheleau", "Recipe Details", "1.0");
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_recipe_details_phone);
        stub.inflate();

        //TODO implement the following commented code once fragment is built properly
        //Bundle args = getIntent().getExtras();
        FragmentManager fm = getSupportFragmentManager();
        RecipeDetailsFragment recipeDetails = new RecipeDetailsFragment();
        //recipeDetails.setArguments(args);

        fm.beginTransaction()
                .replace(R.id.recipe_details_phone, recipeDetails)
                .commit();

    }
}