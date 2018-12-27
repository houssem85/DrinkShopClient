package com.programasoft.drinkshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.programasoft.drinkshop.DrinkActivity;
import com.programasoft.drinkshop.R;
import com.programasoft.drinkshop.model.menu;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 20/12/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    public MenuAdapter(Context context, List<menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    private Context context;
    private List<menu> menus;


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.menu_item_layout,null);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        //load image
        Picasso.with(context).load(menus.get(position).getLink()).into(holder.image);
        //load text
        holder.name.setText(menus.get(position).getName());
        //events
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, DrinkActivity.class);
                i.putExtra("menu_id",menus.get(position).getID());
                i.putExtra("menu_name",menus.get(position).getName());
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
}
