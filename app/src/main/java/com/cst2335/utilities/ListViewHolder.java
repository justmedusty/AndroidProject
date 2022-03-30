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

    ListViewHolder(View itemView, Context context)
    {
        super(itemView);
            titleView
                    = (TextView) itemView
                    .findViewById(R.id.searchActivityRowTitle);
            favouriteButtonView
                    = (ImageButton) itemView
                    .findViewById(R.id.searchActivityRowButton);
            view = itemView;
            this.context = context;
            titleView.setOnClickListener(this);
            favouriteButtonView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (isPhone && view.equals(titleView)) {
            Intent goToFragment = new Intent(context, RecipeDetailsPhone.class);
            goToFragment.putExtra("title", adapter.list.get(getAdapterPosition()).getTitle());
            goToFragment.putExtra("ingredients", adapter.list.get(getAdapterPosition()).getIngredients());
            goToFragment.putExtra("URL", adapter.list.get(getAdapterPosition()).getURL());
            context.startActivity(goToFragment);
        } else if (view.equals(titleView)){
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
            //DATABASE STUFF

        }
    }
}
