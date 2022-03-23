package com.cst2335.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ListAdapter
        extends RecyclerView.Adapter<ListViewHolder> {

    List<RecipeData> list;

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

        // Inflate the layout

        View listRowView
                = inflater
                .inflate(R.layout.search_activity_row,
                        parent, false);

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
            @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
