package com.programasoft.drinkshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.programasoft.drinkshop.R;

/**
 * Created by ASUS on 20/12/2018.
 */

public class DrinkViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView name;
    public TextView price;
    public ImageButton btn_add_to_chart;
    public ImageButton btn_add_to_favorite;

    public DrinkViewHolder(View itemView) {
        super(itemView);
        image=(ImageView)itemView.findViewById(R.id.image);
        name=(TextView)itemView.findViewById(R.id.name);
        price=(TextView)itemView.findViewById(R.id.price);
        btn_add_to_chart=(ImageButton)itemView.findViewById(R.id.btn_add_chart);
        btn_add_to_favorite=(ImageButton)itemView.findViewById(R.id.btn_add_favorite);
    }
}
