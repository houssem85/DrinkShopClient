package com.programasoft.drinkshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.programasoft.drinkshop.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
public TextView txt_id;
public TextView txt_price;
public TextView txt_status;
public TextView txt_comment;


    public OrderViewHolder(View itemView) {
        super(itemView);
        txt_id=(TextView)itemView.findViewById(R.id.id);
        txt_price=(TextView)itemView.findViewById(R.id.txt_price);
        txt_status=(TextView)itemView.findViewById(R.id.txt_status);
        txt_comment=(TextView)itemView.findViewById(R.id.txt_comment);
    }
}
