package com.cst2335.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.androidproject.FavouritesActivity;
import com.cst2335.androidproject.PopularActivity;
import com.cst2335.androidproject.R;
import com.cst2335.androidproject.SearchActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter
        extends RecyclerView.Adapter<ListViewHolder> {

    ArrayList<RecipeData> list;
    View listRowView;
    Context context;
    DatabaseHelper helper;
    public ListAdapter(ArrayList<RecipeData> list,
                       Context context) {
        this.list = list;
        this.context = context;
        helper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
    }
    public void setList(ArrayList<RecipeData> list){
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


        // Inflate the layout based on what activity is being used
        if (context instanceof SearchActivity || context instanceof PopularActivity || context instanceof FavouritesActivity) {
            listRowView = inflater
                    .inflate(R.layout.search_activity_row, parent, false);
        } else if (context instanceof FavouritesActivity) {
            listRowView = inflater
                    .inflate(R.layout.favourites_row, parent, false);
        }
//        View listRowView
//                = inflater
//                .inflate(R.layout.search_activity_row,
//                        parent, false);
        for(RecipeData recipe : list){
            if(helper.checkForRecord(recipe.url)){
                recipe.isFavourited = true;
            }
        }
        return new ListViewHolder(listRowView, context);
    }

    @Override
    public void
    onBindViewHolder(final ListViewHolder viewHolder,
                     final int position) {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.titleView
                .setText(list.get(position).title);
        if (list.get(position).isFavourited && viewHolder.favouriteButtonView != null){
            viewHolder.favouriteButtonView
                    .setImageResource(R.drawable.favourited);
        } else if (viewHolder.favouriteButtonView != null){
            viewHolder.favouriteButtonView
                    .setImageResource(R.drawable.favourite);
        }
        viewHolder.adapter=this;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }


}
