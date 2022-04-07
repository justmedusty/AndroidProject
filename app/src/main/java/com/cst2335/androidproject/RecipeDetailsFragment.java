package com.cst2335.androidproject;
/*
File: RecipeDetailsFragment.java
Author: Chad Rocheleau
Lab Section: 012
Date: March 24 2022

 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.cst2335.utilities.DatabaseHelper;
import com.cst2335.utilities.ListAdapter;
import com.cst2335.utilities.ListViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.regex.Pattern;

/**
 * This fragment subclass of Fragment is responsible for presenting a single selected Recipe's details
 * the Title, ingredients list, and a button to visit original recipe site and a floating action
 * button is provided for favouriting or unfavouriting the recipe being viewed in detail.
 */
public class RecipeDetailsFragment extends Fragment {

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
                                                    String url) {

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();


        // putting arguments into args Bundle for unpacking
        args.putString(ARG_RECIPE_TITLE, title);
        args.putString(ARG_RECIPE_INGREDIENTS, ingredients);
        args.putString(ARG_RECIPE_URL, url);


        fragment.setArguments(args);
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
     * Essential operations to be performed when this fragment is created. The list of ingredients
     * is formatted for use with a list view (converted to an array of ingredient items), the title
     * of the fragment is set and the layout inflated.
     *
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

        // formatting the list of ingredients so we can split into an array of ingredients.
        ingredients = ingredients.replaceAll(Pattern.quote("\""), " ");
        ingredients = ingredients.replaceAll(Pattern.quote("["), "");
        ingredients = ingredients.replaceAll(Pattern.quote("]"), "");
        ingredients = ingredients.replaceAll(Pattern.quote("\\"), " ");
        ingredientArray = ingredients.split(",");

        fragTitle.setText(title);

        return fragmentDetails;
    }

    /**
     * Implements fragment button behavior and handles populating the recyclerview used for displaying
     * the list of ingredients for this recipe to the user in the fragment details.
     *
     * @param view The fragment view that has been created and inflated
     * @param savedInstanceState Bundle of saved information used by this fragment.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // using the url of the fragment to perform action to take user to website based on url
        Button goToWeb = view.findViewById(R.id.recipe_url);
        goToWeb.setOnClickListener( click -> {
            Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // get the floating action button and database helper so recipes can be favourited from
        // within a fragment.
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
        favourite.setOnClickListener(v -> {
            if (helper.checkForRecord(url)) {
                helper.removeFromDatabase(url);
                favourite.setImageResource(R.drawable.favourite);
                if(!ListViewHolder.getIsPhone()) {
                    recipeListAdapter.removeItem(position);
                    getParentFragmentManager().beginTransaction()
                            .remove(RecipeDetailsFragment.this)
                            .commitNowAllowingStateLoss();
                }
            } else {
                helper.insertIntoDatabase(title, ingredients, url);
                favourite.setImageResource(R.drawable.favourited);

            }

        });
        // populate the recycler view in fragment with ingredients list

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ListAdapter adapter = new ListAdapter(getContext(), ingredientArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * required empty public constructor
     */
    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    //************************** Setters ********************
    /**
     * sets the position value representing the position in the recipe list that was selected
     * to show the recipe details specifically
     * @param position the position in the list that reflects the recipe details being shown.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * sets the reference to the adapter used for the recipe list
     * @param recipeListAdapter the adapter used for the recipe list.
     */
    public void setRecipeListAdapter(ListAdapter recipeListAdapter) {
        this.recipeListAdapter = recipeListAdapter;
    }

    // ***************** all class variables. *****************************
    /**
     * constant key for title value
     */
    private static final String ARG_RECIPE_TITLE = "title";

    /**
     * constant key for ingredients value
     */
    private static final String ARG_RECIPE_INGREDIENTS = "ingredients";

    /**
     * constant key for url value
     */
    private static final String ARG_RECIPE_URL = "url";


    // the values of fragment parameters to be used in setting fields of fragment layout.
    /**
     * value for title of the selected item being displayed in the details fragment.
     */
    private String title;

    /**
     * value for ingredients of the selected item being displayed in the details fragment.
     */
    private String ingredients;

    /**
     * value for url of the selected item being displayed in the details fragment.
     */
    private String url;

    /**
     * position of the item selected for which fragment details are displayed.
     */
    private int position;

    /**
     * the adapter responsible for listing the recipe titles
     */
    private ListAdapter recipeListAdapter;

    /**
     * the array containing the list of ingredients for this details fragment.
     */
    private String[]  ingredientArray;
}