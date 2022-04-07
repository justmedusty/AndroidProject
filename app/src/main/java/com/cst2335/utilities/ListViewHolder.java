package com.cst2335.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.androidproject.FavouritesActivity;
import com.cst2335.androidproject.R;
import com.cst2335.androidproject.RecipeDetailsFragment;
import com.cst2335.androidproject.RecipeDetailsPhone;
import com.google.android.material.snackbar.Snackbar;

/**
 * ListViewHolder class represents each row object in the recycler view,
 * used primarily to store functionality and listeners related to recyclerview rows.
 */
public class ListViewHolder
        extends RecyclerView.ViewHolder {


    /**
     * Stores a reference to the adapter responsible for this ListViewHolder.
     */
    private final ListAdapter adapter;


    /**
     * TextView used to display each recipe title.
     */
    private TextView titleView;
    /**
     * TextView only used for RecipeDetailsFragment to display one row of ingredients.
     */
    private TextView ingredientView;
    /**
     * ImageButton used to display whether each recipe is favourited or not.
     */
    private ImageButton favouriteButtonView;
    /**
     * Context of the activity that contains this ViewHolder.
     */
    private final Context context;
    /**
     * Application-wide boolean value to represent whether application is being used on a phone or tablet.
     * The value will be true if the device is a phone and false if the device is a tablet.
     */
    private static boolean isPhone = false;
    /**
     * Instance of DatabaseHelper object to allow ViewHolder to perform CRUD operations on the database.
     */
    private DatabaseHelper helper;

    /**
     * Instantiates a new ListViewHolder, setting the layout references for relevant View objects,
     * as well as storing references for the responsible adapter and responsible activity's context.
     *
     * @param itemView View reference representing relevant layout.
     * @param context  Context of the activity containing this ViewHolder.
     */
    ListViewHolder(View itemView, Context context, ListAdapter adapter) {
        super(itemView);
        if (itemView.findViewById(R.id.recipe_row_layout) != null) {
            titleView
                    = itemView
                    .findViewById(R.id.searchActivityRowTitle);
            favouriteButtonView
                    = itemView
                    .findViewById(R.id.searchActivityRowButton);
        } else {
            ingredientView = itemView.findViewById(R.id.ingredient);
        }
        this.context = context;
        this.adapter = adapter;
    }

    /**
     * Sets the value of the isPhone variable so ViewHolder knows whether the device is a phone or tablet.
     *
     * @param isPhoneParam boolean representing whether device is a phone or tablet, true for phone, false for tablet
     */

    public static void setIsPhone(boolean isPhoneParam) {
        isPhone = isPhoneParam;
    }

    public static boolean getIsPhone() {
        return isPhone;
    }
    /**
     * Returns a reference to the view responsible for storing ingredients,
     * used by the adapter to bind data to the view.
     *
     * @return TextView used to display one ingredient row.
     */
    public TextView getIngredientView() {
        return ingredientView;
    }

    /**
     * Returns a reference to the view responsible for storing status of RecipeData object,
     * used by adapter to update image resource depending on favourited status.
     *
     * @return ImageButton used to represent whether recipe is favourited or not.
     */
    public ImageButton getFavouriteButtonView() {
        return favouriteButtonView;
    }

    /**
     * Returns a reference to the view responsible for storing recipe titles,
     * used by the adapter to bind data to the view.
     *
     * @return TextView used to display on recipe title.
     */
    public TextView getTitleView() {
        return titleView;
    }

    /**
     * Method called by adapter to set onClick function to the favourite button.
     */
    public void setFavouriteButtonListener() {
        favouriteButtonView.setOnClickListener(favouriteButtonListener);
    }

    /**
     * Method called by the adapter to set onClick function to the recipe title.
     */
    public void setTitleListener() {
        titleView.setOnClickListener(titleListener);
    }

    /**
     * Creates and shows an alert dialog confirming whether user wants to remove a recipe from their favourites,
     * "Yes" will remove the recipe from the database, change the isFavourited boolean value, and notify the adapter
     * to update the recyclerview, no will cancel the action and result in nothing.
     *
     * @param url String recipe URL used to locate recipe in database.
     */
    public void showConfirmAlertDialog(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bookmark Removal.");
        builder.setMessage("Do you want to remove this recipe from your favourites?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            adapter.getRecipeList().get(getLayoutPosition()).isFavourited = false;
            helper.removeFromDatabase(url);
            adapter.notifyDataSetChanged();
            if (context instanceof FavouritesActivity) {
                adapter.removeItem(getLayoutPosition());
            }
            toastMaker("Bookmark removed.", context);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.cancel();
            toastMaker("Bookmark not removed.", context);
        });
        builder.create();
        builder.show();
    }

    /**
     * Used by ListViewHolder to re-use boilerplate code for creating and showing Toasts.
     *
     * @param message String to be output to user through Toast.
     * @param context Context of activity responsible for this ViewHolder.
     */
    private void toastMaker(String message, Context context) {
        Toast toast = Toast.makeText(context,
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Method used to create SnackBar informing user that recipe has been added to favourites,
     * as well as allowing the user to undo the action, removing the recipe from the database.
     *
     * @param url String URL for relevant recipe, used to locate recipe in database.
     */
    private void snackBarMaker(String url) {
        Snackbar.make(favouriteButtonView, "Recipe has been added to favourites", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> {
                    adapter.getRecipeList().get(getLayoutPosition()).isFavourited = false;
                    helper.removeFromDatabase(url);
                    adapter.notifyDataSetChanged();
                })
                .show();
    }

    /**
     * Listener responsible for click actions on recipe titles, either moving to entirely new activity for phones
     * or creating fragment in current activity for tablets, as well as passing all relevant data to fragment.
     */
    private final View.OnClickListener titleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isPhone) {
                Intent goToFragment = new Intent(context.getApplicationContext(), RecipeDetailsPhone.class);
                goToFragment.putExtra("title", adapter.getRecipeList().get(getAdapterPosition()).getTitle());
                goToFragment.putExtra("ingredients", adapter.getRecipeList().get(getAdapterPosition()).getIngredients());
                goToFragment.putExtra("url", adapter.getRecipeList().get(getAdapterPosition()).getURL());
                goToFragment.putExtra("position", getAdapterPosition());
                context.startActivity(goToFragment);
            } else {
                RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(
                        adapter.getRecipeList().get(getLayoutPosition()).getTitle(),
                        adapter.getRecipeList().get(getAdapterPosition()).getIngredients(),
                        adapter.getRecipeList().get(getAdapterPosition()).getURL());
                detailsFragment.setPosition(getAdapterPosition());
                detailsFragment.setRecipeListAdapter(adapter);
                AppCompatActivity activity = (AppCompatActivity) context;
                int ft = activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.recipe_details_fragment, detailsFragment)
                        .commit();
            }
        }
    };

    /**
     * Listener responsible for click actions on the favourite button, either adding or removing the recipe
     * from the database and notifying the adapter to update the favourite button's image resource.
     */
    private final View.OnClickListener favouriteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = adapter.getRecipeList().get(getLayoutPosition()).getTitle();
            String ingredients = adapter.getRecipeList().get(getAdapterPosition()).getIngredients();
            String url = adapter.getRecipeList().get(getAdapterPosition()).getURL();

            helper = new DatabaseHelper(context.getApplicationContext(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);

            if (adapter.getRecipeList().get(getLayoutPosition()).isFavourited) {
                showConfirmAlertDialog(url);

            } else {
                adapter.getRecipeList().get(getLayoutPosition()).isFavourited = true;
                adapter.notifyDataSetChanged();
                helper.insertIntoDatabase(title, ingredients, url);
                snackBarMaker(url);
            }
        }
    };
}
