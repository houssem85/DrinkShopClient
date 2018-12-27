package com.programasoft.drinkshop.Adapter;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.programasoft.drinkshop.Database.DataSource.CartRepository;
import com.programasoft.drinkshop.Database.DataSource.FavoriteRepository;
import com.programasoft.drinkshop.Database.Local.CartDataSource;
import com.programasoft.drinkshop.Database.Local.Database;
import com.programasoft.drinkshop.Database.Local.FavoriteDataSource;
import com.programasoft.drinkshop.Database.ModelDB.Favorite;
import com.programasoft.drinkshop.Database.ModelDB.cart;
import com.programasoft.drinkshop.R;
import com.programasoft.drinkshop.model.drink;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 20/12/2018.
 */

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {
    private AppCompatActivity context;
    private List<drink> drinks;

    public DrinkAdapter(AppCompatActivity context, List<drink> drinks) {
        this.context = context;
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.drink_item_layout,null);
        return new DrinkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DrinkViewHolder holder, final int position) {
        Picasso.with(context).load(drinks.get(position).getLink()).into(holder.image);
        holder.name.setText(drinks.get(position).getName());
        holder.price.setText(drinks.get(position).getPrice()+" dt");
        Database database=Database.getInstance(context);
        final FavoriteRepository repository=FavoriteRepository.getInstance(FavoriteDataSource.getInstance(database.FavoriteDao()));
        final int IsFavorite=repository.IsFavorite(drinks.get(position).getId());
        if(IsFavorite==0) {
            holder.btn_add_to_favorite.setImageResource(R.drawable.favorite_no);
        }else
        {
            holder.btn_add_to_favorite.setImageResource(R.drawable.favorite_yes);
        }
        holder.btn_add_to_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite f = new Favorite();
                f.setId(drinks.get(position).getId());
                f.setName(drinks.get(position).getName());
                f.setLink(drinks.get(position).getLink());
                f.setMenu_id(drinks.get(position).getMenu_id());
                f.setPrice(drinks.get(position).getPrice());
                if(repository.IsFavorite(drinks.get(position).getId())==0) {
                    repository.InsertFavorites(f);
                    holder.btn_add_to_favorite.setImageResource(R.drawable.favorite_yes);
                }else
                {   repository.DeleteFavorite(f);
                    holder.btn_add_to_favorite.setImageResource(R.drawable.favorite_no);
                }


            }
        });
        holder.btn_add_to_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(v.getContext());
                View vm=LayoutInflater.from(context).inflate(R.layout.add_cart_layout,null);
                TextView name=(TextView)vm.findViewById(R.id.name);
                final EditText comment=(EditText)vm.findViewById(R.id.txt_comment) ;
                ImageView image=(ImageView)vm.findViewById(R.id.image);
                final ElegantNumberButton btn_add_moin=(ElegantNumberButton)vm.findViewById(R.id.btn_add_moin);
                final RadioButton rdi_sizeM=(RadioButton)vm.findViewById(R.id.rdisizem);
                final RadioButton rdi_sizeL=(RadioButton)vm.findViewById(R.id.rdisizel);
                final RadioButton sugar_100=(RadioButton)vm.findViewById(R.id.sugar_100);
                final RadioButton sugar_70=(RadioButton)vm.findViewById(R.id.sugar_70);
                final RadioButton sugar_30=(RadioButton)vm.findViewById(R.id.sugar_30);
                final RadioButton sugar_50=(RadioButton)vm.findViewById(R.id.sugar_50);
                final RadioButton sugar_free=(RadioButton)vm.findViewById(R.id.sugar_free);
                final RadioButton ice_100=(RadioButton)vm.findViewById(R.id.ice_100);
                final RadioButton ice_70=(RadioButton)vm.findViewById(R.id.ice_70);
                final RadioButton ice_30=(RadioButton)vm.findViewById(R.id.ice_30);
                final RadioButton ice_50=(RadioButton)vm.findViewById(R.id.ice_50);
                final RadioButton ice_free=(RadioButton)vm.findViewById(R.id.ice_free);
                final RadioGroup group_size=(RadioGroup)vm.findViewById(R.id.group_size);
                final RadioGroup group_sugar=(RadioGroup)vm.findViewById(R.id.group_sugar);
                final  RadioGroup group_ice=(RadioGroup)vm.findViewById(R.id.group_ice);

                name.setText(drinks.get(position).getName());
                Picasso.with(context).load(drinks.get(position).getLink()).into(image);
                builder.setView(vm);
                android.support.v7.app.AlertDialog dialog;
                builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if((rdi_sizeL.isChecked()==true || rdi_sizeM.isChecked()==true) &&
                                (sugar_100.isChecked()==true || sugar_70.isChecked()==true || sugar_50.isChecked()==true || sugar_30.isChecked()==true || sugar_free.isChecked()==true) &&
                                (ice_100.isChecked()==true || ice_70.isChecked()==true || ice_50.isChecked()==true || ice_30.isChecked()==true || ice_free.isChecked()==true)
                                )
                        {dialog.dismiss();
                         String Comment=comment.getText().toString();
                            //////
                            int id= group_size.getCheckedRadioButtonId();
                            View radioButton = group_size.findViewById(id);
                            int radioId = group_size.indexOfChild(radioButton);
                            RadioButton btn = (RadioButton) group_size.getChildAt(radioId);
                            String size = (String) btn.getText();
                            ///////////
                            id= group_sugar.getCheckedRadioButtonId();
                            radioButton = group_sugar.findViewById(id);
                            radioId = group_sugar.indexOfChild(radioButton);
                            btn = (RadioButton) group_sugar.getChildAt(radioId);
                            String suagr = (String) btn.getText();
                            ////////////
                            id= group_ice.getCheckedRadioButtonId();
                            radioButton = group_ice.findViewById(id);
                            radioId = group_ice.indexOfChild(radioButton);
                            btn = (RadioButton) group_ice.getChildAt(radioId);
                            String ice = (String) btn.getText();
                            /////////////
                         StartConfirmAlert(Comment,size,suagr,ice,position,btn_add_moin.getNumber());

                        }else
                        {Toast.makeText(context,"Please choice sugar,size,ice",Toast.LENGTH_LONG).show();

                        }

                    }
                });
                dialog=builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    private void StartConfirmAlert(String Comment, String size, final String sugar, final String ice, final int position, final String quantite)
    {
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(context);
        View vm=LayoutInflater.from(context).inflate(R.layout.confirmation_layout,null);
        final TextView name=(TextView)vm.findViewById(R.id.name);
        final TextView price=(TextView)vm.findViewById(R.id.price);
        final TextView description=(TextView)vm.findViewById(R.id.description);
        ImageView image=(ImageView)vm.findViewById(R.id.image);
        Picasso.with(context).load(drinks.get(position).getLink()).into(image);
        name.setText(drinks.get(position).getName()+" *"+quantite+" "+size);
        description.setText("sugar: "+sugar+"  "+"ice: "+ice);
        price.setText(Integer.valueOf(quantite)*drinks.get(position).getPrice()+"dt");
        builder.setView(vm);
        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Database database = Database.getInstance(context);
                    CartRepository repository = CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));
                    cart CartItem = new cart();
                    CartItem.setName(name.getText().toString());
                    CartItem.setAcount(Integer.valueOf(quantite));
                    CartItem.setPrice(Integer.valueOf(quantite) * drinks.get(position).getPrice());
                    CartItem.setIce(Integer.valueOf(ice.substring(0, ice.length() - 1)));
                    CartItem.setSugar(Integer.valueOf(sugar.substring(0, sugar.length() - 1)));
                    CartItem.setLink(drinks.get(position).getLink());
                    repository.InsertToCart(CartItem);
                    Toast.makeText(context, "Save item to char sucess", Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        android.support.v7.app.AlertDialog dialog=builder.create();
        dialog.show();
    }


}
