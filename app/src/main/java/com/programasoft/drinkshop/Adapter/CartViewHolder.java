package com.programasoft.drinkshop.Adapter;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.programasoft.drinkshop.R;

/**
 * Created by ASUS on 23/12/2018.
 */

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ElegantNumberButton btn_moin_plus;
    public TextView name;
    public TextView description;
    public TextView price;
    public ImageView image;
    public LinearLayout view_forground;
    public RelativeLayout view_background;

    public CartViewHolder(View itemView) {
        super(itemView);
        name=(TextView) itemView.findViewById(R.id.name);
        description=(TextView) itemView.findViewById(R.id.description);
        price=(TextView) itemView.findViewById(R.id.price);
        btn_moin_plus=(ElegantNumberButton)itemView.findViewById(R.id.btn_add_moin);
        image=(ImageView)itemView.findViewById(R.id.image);
        view_forground=(LinearLayout)itemView.findViewById(R.id.View_Forground);
        view_background=(RelativeLayout)itemView.findViewById(R.id.View_Background);

    }
}
