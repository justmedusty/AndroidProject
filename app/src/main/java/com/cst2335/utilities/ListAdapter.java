package com.cst2335.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.androidproject.FavouritesActivity;
import com.cst2335.androidproject.R;
import com.cst2335.androidproject.SearchActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapter
        extends RecyclerView.Adapter<ListViewHolder> {

    List<RecipeData> list;
    View listRowView;
    Context context;

    public ListAdapter(List<RecipeData> list,
                       Context context) {
        this.list = list;
        this.context = context;
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
        if (context instanceof SearchActivity) {
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

        return new ListViewHolder(listRowView);
    }

    @Override
    public void
    onBindViewHolder(final ListViewHolder viewHolder,
                     final int position) {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.titleView
                .setText(list.get(position).title);
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


}
