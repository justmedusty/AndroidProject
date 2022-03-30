package com.cst2335.androidproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
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

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }
    public RecipeDetailsFragment(String title, String ingredients, String url) {
        this.title = title;
        this.ingredients = ingredients;
        this.url = url;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title the recipe title.
     * @param ingredients the list of ingredients for the recipe.
     * @param url the url to the recipe as obtained from api call.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailsFragment newInstance(String title,
                                                    String ingredients,
                                                    String url) {

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        // putting arguments into args Bundle for unpacking
        args.putString(ARG_RECIPE_TITLE, fragment.title);
        args.putString(ARG_RECIPE_INGREDIENTS, fragment.ingredients);
        args.putString(ARG_RECIPE_URL, fragment.url);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * When This fragment is created get arguments if any and
     * @param savedInstanceState
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_fragment_recipe_details, container, false);
        //TODO implement setting the fields of the fragment layout with arguments as passed in bundle
        //TODO layout_fragment_recipe_details.xml must be constructed with the fields that will
        //      be necessary for displaying arguments passed in bundle.
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO should any fragment specific button behavior be needed it
        //      can be implemented here.
    }
}