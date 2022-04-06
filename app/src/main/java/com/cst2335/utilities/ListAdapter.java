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
 * The type List adapter.
 */
public class ListAdapter
        extends RecyclerView.Adapter<ListViewHolder> {

    /**
     * The List.
     */
    ArrayList<RecipeData> list;
    /**
     * The Ingredient list.
     */
    String[] ingredientList;
    /**
     * The List row view.
     */
    View listRowView;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Helper.
     */
    DatabaseHelper helper;

    /**
     * Instantiates a new List adapter.
     *
     * @param list    the list
     * @param context the context
     */
    public ListAdapter(ArrayList<RecipeData> list,
                       Context context) {
        this.list = list;
        this.context = context;
        helper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
    }

    /**
     * Instantiates a new List adapter.
     *
     * @param context the context
     * @param list    the list
     */
    public ListAdapter(Context context,
                       String[] list) {
        this.ingredientList = list;
        this.context = context;
        helper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
    }

    /**
     * Set list.
     *
     * @param list the list
     */
    public void setList(ArrayList<RecipeData> list) {
        this.list = new ArrayList<>();
        this.list = list;
    }

    @Override
    @NotNull
    public ListViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        if (list != null) {
            listRowView = inflater
                    .inflate(R.layout.recipe_row, parent, false);
            for (RecipeData recipe : list) {
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

    @Override
    public void
    onBindViewHolder(final ListViewHolder viewHolder,
                     final int position) {
        if (list != null) { // this is a list of recipe data objects
            buildActivityLVH(viewHolder, position);
        } else { // this is a list of ingredients
            viewHolder.getIngredientView()
                    .setText(ingredientList[position]);
        }
    }

    @Override
    public int getItemCount() {

        if (list != null) {
            return list.size();
        } else {
            return ingredientList.length;
        }
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Remove item.
     *
     * @param position the position
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    private void buildActivityLVH(ListViewHolder viewHolder, int position) {
        viewHolder.getTitleView()
                .setText(list.get(position).title);
        viewHolder.setTitleListener();
        if (list.get(position).isFavourited) {
            viewHolder.getFavouriteButtonView()
                    .setImageResource(R.drawable.favourited);
        } else {
            viewHolder.getFavouriteButtonView()
                    .setImageResource(R.drawable.favourite);
        }
        viewHolder.setFavouriteButtonListener();
    }
}
