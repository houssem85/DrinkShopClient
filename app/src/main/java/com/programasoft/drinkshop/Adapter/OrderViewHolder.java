package com.programasoft.drinkshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.programasoft.drinkshop.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
public TextView txt_id;
public TextView txt_price;
public TextView txt_comment;
    public TextView name;
    public TextView description;
    public TextView price;
    public ImageView image;
    public LinearLayout view_forground;
    public TextView txt_details;
    public Button btn_cancel;

    public OrderViewHolder(View itemView) {
        super(itemView);
        txt_id=(TextView)itemView.findViewById(R.id.id);
        txt_price=(TextView)itemView.findViewById(R.id.txt_price);
        txt_comment=(TextView)itemView.findViewById(R.id.txt_comment);
        name=(TextView) itemView.findViewById(R.id.name);
        description=(TextView) itemView.findViewById(R.id.description);
        price=(TextView) itemView.findViewById(R.id.price);
        image=(ImageView)itemView.findViewById(R.id.image);
        view_forground=(LinearLayout)itemView.findViewById(R.id.View_Forground);
        txt_details=(TextView) itemView.findViewById(R.id.txt_detail);
        btn_cancel=(Button)itemView.findViewById(R.id.btn_cancel);
        }
}
