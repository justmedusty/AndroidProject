package com.cst2335.androidproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cst2335.utilities.DatabaseHelper;
import com.cst2335.utilities.ListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This fragment subclass of Fragment is responsible for presenting a single selected Recipe's details
 * the Title, ingredients list, and a button to visit original recipe site and a floating action
 * button is provided for favouriting or unfavouriting the recipe being viewed in detail.
 */
public class RecipeDetailsFragment extends Fragment {


    // the fragment initialization parameters
    private static final String ARG_RECIPE_TITLE = "title";
    private static final String ARG_RECIPE_INGREDIENTS = "ingredients";
    private static final String ARG_RECIPE_URL = "url";

    // the values of fragment parameters to be used in setting fields of fragment layout.
    private String title;
    private String ingredients;
    private String url;

    RecyclerView recyclerView;
    ListAdapter adapter;
    static ListAdapter recipeListAdapter;
    String[]  ingredientArray;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title       the recipe title.
     * @param ingredients the list of ingredients for the recipe.
     * @param url         the url to the recipe as obtained from api call.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailsFragment newInstance(String title,
                                                    String ingredients,
                                                    String url,
                                                    ListAdapter recipeListAdapter) {

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();


        // putting arguments into args Bundle for unpacking
        args.putString(ARG_RECIPE_TITLE, title);
        args.putString(ARG_RECIPE_INGREDIENTS, ingredients);
        args.putString(ARG_RECIPE_URL, url);

        fragment.setArguments(args);
        RecipeDetailsFragment.recipeListAdapter = recipeListAdapter;
        return fragment;
    }

    /**
     * When This fragment is created get arguments if any and save them as instance members of
     * this fragment class.
     *
     * @param savedInstanceState Bundle containing any arguments that need
     *                           to be used by the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_RECIPE_TITLE);
            ingredients = getArguments().getString(ARG_RECIPE_INGREDIENTS);
            url = getArguments().getString(ARG_RECIPE_URL);
        }
    }

    /**
     * All fragment creation business is taken care of here.
     * @param inflater the inflater used to inflate this fragment layout
     * @param container the container in which this fragment will be inflated
     * @param savedInstanceState Bundle of data to be used by this fragment
     * @return the fragment view as inflated in the container / FrameLayout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentDetails = inflater.inflate(R.layout.layout_fragment_recipe_details, container, false);
        TextView fragTitle = fragmentDetails.findViewById(R.id.recipe_title);

        //TextView fragIngredients = fragmentDetails.findViewById(R.id.recipe_ingredients);


        ingredients = ingredients.replaceAll(Pattern.quote("\""), " ");
        ingredients = ingredients.replaceAll(Pattern.quote("["), "");
        ingredients = ingredients.replaceAll(Pattern.quote("]"), "");
        ingredients = ingredients.replaceAll(Pattern.quote("\\"), " ");
        ingredientArray = ingredients.split(",");

        fragTitle.setText(title);

        return fragmentDetails;
    }

    /**
     * Any implementation of items in the fragment take place here. Button behavior and populating
     * the ingredients list view that needs populating within this fragment with a list of the
     * ingredients belonging to the recipe being featured in this details fragment.
     * @param view The fragment view that has been created and inflated
     * @param savedInstanceState Bundle of saved information used by this fragment.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isFavourite;

        Button goToWeb = view.findViewById(R.id.recipe_url);
        goToWeb.setOnClickListener( click -> {
            Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // get the floating action button and database helper
        FloatingActionButton favourite = view.findViewById(R.id.favouriteActionButton);
        final DatabaseHelper helper = new DatabaseHelper(getContext(),
                DatabaseHelper.DATABASE_NAME,
                null,
                DatabaseHelper.VERSION);

        // set the image of the floating action button to reflect whether or not the recipe is favourite
        if (helper.checkForRecord(url)) {
            favourite.setImageResource(R.drawable.favourited);
        } else {
            favourite.setImageResource(R.drawable.favourite);
        }

        // deal with onclick of favourite floating action button.
        favourite.setOnClickListener( new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if (helper.checkForRecord(url)) {
                    helper.removeFromDatabase(url);
                    favourite.setImageResource(R.drawable.favourite);


                } else {
                    helper.insertIntoDatabase(title, ingredients, url);
                    favourite.setImageResource(R.drawable.favourited);

                }

            }

        });
        // populate the recycler view in fragment with ingredients list
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new ListAdapter(getContext(), ingredientArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}