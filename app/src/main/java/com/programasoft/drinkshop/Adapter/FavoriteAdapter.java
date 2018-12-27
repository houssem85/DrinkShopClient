package com.programasoft.drinkshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programasoft.drinkshop.Database.ModelDB.Favorite;
import com.programasoft.drinkshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 25/12/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private Context context;
    private List<Favorite> favoriteList;

    public FavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
    holder.btn_moin_plus.setVisibility(View.INVISIBLE);
    holder.description.setVisibility(View.INVISIBLE);
    holder.name.setText(favoriteList.get(position).getName());
    holder.price.setText(favoriteList.get(position).getPrice()+" dt");
    Picasso.with(context).load(favoriteList.get(position).getLink()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
}
