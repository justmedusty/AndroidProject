package com.cst2335.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.androidproject.R;
import com.cst2335.androidproject.RecipeDetailsFragment;
import com.cst2335.androidproject.RecipeDetailsPhone;

public class ListViewHolder
        extends RecyclerView.ViewHolder implements View.OnClickListener {
    ListAdapter adapter;
    TextView titleView;
    ImageButton favouriteButtonView;
    View view;
    Context context;
    boolean isPhone;
    DatabaseHelper helper;


    ListViewHolder(View itemView, Context context) {
        super(itemView);
        titleView
                = itemView
                .findViewById(R.id.searchActivityRowTitle);
        favouriteButtonView
                = itemView
                .findViewById(R.id.searchActivityRowButton);
        view = itemView;
        this.context = context;
        titleView.setOnClickListener(this);
        if (favouriteButtonView != null) {
            favouriteButtonView.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        if (isPhone && view.equals(titleView)) {
            Intent goToFragment = new Intent(context, RecipeDetailsPhone.class);
            goToFragment.putExtra("title", adapter.list.get(getAdapterPosition()).getTitle());
            goToFragment.putExtra("ingredients", adapter.list.get(getAdapterPosition()).getIngredients());
            goToFragment.putExtra("url", adapter.list.get(getAdapterPosition()).getURL());
            context.startActivity(goToFragment);
        } else if (view.equals(titleView)) {
            RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment(
                    adapter.list.get(getLayoutPosition()).getTitle(),
                    adapter.list.get(getAdapterPosition()).getIngredients(),
                    adapter.list.get(getAdapterPosition()).getURL());
            AppCompatActivity activity = (AppCompatActivity) context;

            int ft = activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_details_fragment, detailsFragment)
                    .commit();
        } else {

            String title = adapter.list.get(getLayoutPosition()).getTitle();
            String ingredients = adapter.list.get(getAdapterPosition()).getIngredients();
            String url = adapter.list.get(getAdapterPosition()).getURL();

            helper = new DatabaseHelper(context.getApplicationContext(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
            this.favouriteButtonView.setImageResource(R.drawable.favourited);
            // TODO fix the favourite button changing image for 2 on the list when only one is clicked

            helper.insertIntoDatabase(title, ingredients, url);


        }
    }
}
