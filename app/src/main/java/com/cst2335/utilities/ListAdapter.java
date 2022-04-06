package com.cst2335.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.androidproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * ListAdapter to act as controller for RecyclerView, inflating and binding data to ViewHolder
 * objects, displaying recipes or ingredients.
 */
public class ListAdapter
        extends RecyclerView.Adapter<ListViewHolder> {

    /**
     * ArrayList object used to store a list of RecipeData objects.
     */
    private ArrayList<RecipeData> recipeList;
    /**
     * String array used to store a list of ingredients for a single recipe.
     */
    private String[] ingredientList;
    /**
     * View object used to store reference to inflated row view for both recipes and ignredients.
     */
    private View listRowView;
    /**
     * DatabaseHelper object reference to allow adapter to perform CRUD operations on database.
     */
    private DatabaseHelper helper;

    /**
     * Instantiates a new ListAdapter for any activity displaying a recipe list, creating
     * a DatabasseHelper object for the adapter to perform CRUD operations on the database as well.
     *
     * @param recipeList ArrayList object to store RecipeData objects.
     * @param context Context of the caller for use with the DatabaseHelper object.
     */
    public ListAdapter(ArrayList<RecipeData> recipeList,
                       Context context) {
        this.recipeList = recipeList;
        helper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
    }

    /**
     * Instantiates a new ListAdapter for any activity displaying an ingredient list, creating
     * a DatabasseHelper object for the adapter to perform CRUD operations on the database as well.
     *
     * @param ingredientList String array used to store ingredient strings.
     * @param context Context of the caller for use with the DatabaseHelper object.
     */
    public ListAdapter(Context context,
                       String[] ingredientList) {
        this.ingredientList = ingredientList;
        helper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
    }

    /**
     * Accessor method for the recipeList variable.
     *
     * @return ArrayList object containing a list of RecipeData objects.
     */
    public ArrayList<RecipeData> getRecipeList() {
        return recipeList;
    }

    /**
     * Sets the recipeList variable to a new list of recipes.
     *
     * @param recipeList ArrayList object containing the new list of recipes to be used by this adapter.
     */
    public void setRecipeList(ArrayList<RecipeData> recipeList) {
        this.recipeList = recipeList;
    }

    /**
     * Create method for ViewHolder, inflating the relevant row view and performing a check
     * for each recipe in the database to set their isFavourited property depending on whether
     * recipe is favourited or not.
     *
     * @param parent ViewGroup that this ViewHolder will be added to.
     * @param viewType Represents type of view, irrelevant here because this RecyclerView
     *                 only contains one type of view.
     * @return ListViewHolder object to be added to ViewGroup.
     */
    @Override
    @NotNull
    public ListViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        if (recipeList != null) {
            listRowView = inflater
                    .inflate(R.layout.recipe_row, parent, false);
            for (RecipeData recipe : recipeList) {
                if (helper.checkForRecord(recipe.url)) {
                    recipe.isFavourited = true;
                }
            }
        } else {
            listRowView = inflater
                    .inflate(R.layout.ingredient_row, parent, false);
        }

        return new ListViewHolder(listRowView, context, this);
    }

    /**
     * Method used to bind data to each ViewHolder's views. Decision structure
     * determines whether data to be bound is recipe data or ingredient data.
     *
     * @param viewHolder Reference to ViewHolder object being bound.
     * @param position Reference to position of ViewHolder object within RecyclerView.
     */
    @Override
    public void
    onBindViewHolder(final ListViewHolder viewHolder,
                     final int position) {
        if (recipeList != null) { // this is a list of recipe data objects
            bindActivityLVH(viewHolder, position);
        } else { // this is a list of ingredients
            viewHolder.getIngredientView()
                    .setText(ingredientList[position]);
        }
    }

    /**
     * Attaches ViewHolder object to relevant RecyclerView object.
     *
     * @param recyclerView Reference to relevant RecyclerView object.
     */
    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Returns the size of this adapter's list, decision structure used to determine
     * if list being used is recipe ArrayList or ingredient array.
     *
     * @return size of recipeList if not null, otherwise, size of ingredientList.
     */
    @Override
    public int getItemCount() {

        if (recipeList != null) {
            return recipeList.size();
        } else {
            return ingredientList.length;
        }
    }


    /**
     * Removes a Recipe from the recipeList and notify's the adapter that a recipe has been removed
     * so the adapter updates the RecyclerView.
     *
     * @param position the position of the recipe within the list.
     */
    public void removeItem(int position) {
        recipeList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Internal method used by onBindViewHolder to bind data to recipe ViewHolder object and set the favourite button
     * depending on whether recipe exists in the database already or not. Also sets listeners for view objects within
     * ViewHolder.
     *
     * @param viewHolder Relevant ViewHolder object to be bound.
     * @param position the index of the recipe within the recipeList.
     */
    private void bindActivityLVH(ListViewHolder viewHolder, int position) {
        viewHolder.getTitleView()
                .setText(recipeList.get(position).title);
        viewHolder.setTitleListener();
        if (recipeList.get(position).isFavourited) {
            viewHolder.getFavouriteButtonView()
                    .setImageResource(R.drawable.favourited);
        } else {
            viewHolder.getFavouriteButtonView()
                    .setImageResource(R.drawable.favourite);
        }
        viewHolder.setFavouriteButtonListener();
    }
}
