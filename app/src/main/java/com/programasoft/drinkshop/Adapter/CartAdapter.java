package com.programasoft.drinkshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.programasoft.drinkshop.Database.DataSource.CartRepository;
import com.programasoft.drinkshop.Database.Local.CartDataSource;
import com.programasoft.drinkshop.Database.Local.Database;
import com.programasoft.drinkshop.Database.ModelDB.cart;
import com.programasoft.drinkshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 23/12/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private Context contextCompat;
    private List<cart> cartList;

    public CartAdapter(Context contextCompat, List<cart> cartList) {
        this.contextCompat = contextCompat;
        this.cartList = cartList;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(this.contextCompat).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final cart c=cartList.get(position);
        holder.name.setText(c.getName());
        Picasso.with(contextCompat).load(c.getLink()).into(holder.image);
        holder.price.setText(c.getPrice()+" dt");
        holder.btn_moin_plus.setNumber(c.getAcount()+"");
        holder.description.setText("ice:"+c.getIce()+"% sugar:"+c.getSugar()+"%");
        holder.btn_moin_plus.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                c.setName(c.getName().replace(oldValue+"",newValue+""));
              c.setPrice((c.getPrice()/oldValue)*newValue);
              c.setAcount(newValue);
              Database database= Database.getInstance(contextCompat);
              CartRepository repository=CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));
              repository.UpdateCart(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
