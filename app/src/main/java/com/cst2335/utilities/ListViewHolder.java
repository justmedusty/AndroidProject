package com.cst2335.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

public class ListViewHolder
        extends RecyclerView.ViewHolder implements View.OnClickListener {
    ListAdapter adapter;
    TextView titleView;
    TextView ingredientView;
    ImageButton favouriteButtonView;
    View view;
    Context context;
    static boolean isPhone = false;
    DatabaseHelper helper;

    public static void setIsPhone(boolean isPhoneParam) {
        isPhone = isPhoneParam;
    }

    ListViewHolder(View itemView, Context context) {
        super(itemView);
        if (itemView.findViewById(R.id.recipe_row_layout) != null) {
            titleView
                    = itemView
                    .findViewById(R.id.searchActivityRowTitle);
            favouriteButtonView
                    = itemView
                    .findViewById(R.id.searchActivityRowButton);

            titleView.setOnClickListener(this);
            if (favouriteButtonView != null) {
                favouriteButtonView.setOnClickListener(this);
            }
        } else {
            ingredientView = itemView.findViewById(R.id.ingredient);
        }
        view = itemView;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        if (isPhone && view.equals(titleView)) {
            Intent goToFragment = new Intent(context.getApplicationContext(), RecipeDetailsPhone.class);
            goToFragment.putExtra("title", adapter.list.get(getAdapterPosition()).getTitle());
            goToFragment.putExtra("ingredients", adapter.list.get(getAdapterPosition()).getIngredients());
            goToFragment.putExtra("url", adapter.list.get(getAdapterPosition()).getURL());
            context.startActivity(goToFragment);
        } else if (view.equals(titleView)) {
            RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(
                    adapter.list.get(getLayoutPosition()).getTitle(),
                    adapter.list.get(getAdapterPosition()).getIngredients(),
                    adapter.list.get(getAdapterPosition()).getURL(),
                    adapter);
            AppCompatActivity activity = (AppCompatActivity) context;

            int ft = activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_details_fragment, detailsFragment)
                    .commit();
        } else if (view.equals(favouriteButtonView)) {

            String title = adapter.list.get(getLayoutPosition()).getTitle();
            String ingredients = adapter.list.get(getAdapterPosition()).getIngredients();
            String url = adapter.list.get(getAdapterPosition()).getURL();

            helper = new DatabaseHelper(context.getApplicationContext(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);

            if (adapter.list.get(getLayoutPosition()).isFavourited) {
                showConfirmAlertDialog(url);

            } else {
                adapter.list.get(getLayoutPosition()).isFavourited = true;
                adapter.notifyDataSetChanged();
                helper.insertIntoDatabase(title, ingredients, url);
                snackBarMaker(url);
            }


        }
    }

    public void showConfirmAlertDialog(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bookmark Removal.");
        builder.setMessage("Do you want to remove this recipe from your favourites?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.list.get(getLayoutPosition()).isFavourited = false;
                helper.removeFromDatabase(url);
                adapter.notifyDataSetChanged();
                if(context instanceof FavouritesActivity) {
                    adapter.removeItem(getLayoutPosition());
                }
                toastMaker("Bookmark removed.", context);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                toastMaker("Bookmark not removed.", context);
            }
        });
        builder.create();
        builder.show();
    }

    public void toastMaker(String message, Context context) {
        Toast toast = Toast.makeText(context,
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }

    public void snackBarMaker(String url) {
        Snackbar.make(favouriteButtonView, "Recipe has been added to favourites", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.list.get(getLayoutPosition()).isFavourited = false;
                        helper.removeFromDatabase(url);
                        adapter.notifyDataSetChanged();
                    }
                })
                .show();

        class SnackbarUndoListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                adapter.list.get(getLayoutPosition()).isFavourited = false;
                helper.removeFromDatabase(url);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
