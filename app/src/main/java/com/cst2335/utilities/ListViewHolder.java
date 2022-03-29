package com.cst2335.utilities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.androidproject.R;

public class ListViewHolder
        extends RecyclerView.ViewHolder {
    TextView titleView;
    ImageButton favouriteButtonView;
    View view;

    ListViewHolder(View itemView)
    {
        super(itemView);
            titleView
                    = (TextView) itemView
                    .findViewById(R.id.searchActivityRowTitle);
            favouriteButtonView
                    = (ImageButton) itemView
                    .findViewById(R.id.searchActivityRowButton);
            view = itemView;
    }
}
