package com.programasoft.drinkshop;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.programasoft.drinkshop.Adapter.CartAdapter;
import com.programasoft.drinkshop.Database.DataSource.CartRepository;
import com.programasoft.drinkshop.Database.Local.CartDataSource;
import com.programasoft.drinkshop.Database.Local.Database;
import com.programasoft.drinkshop.Database.ModelDB.cart;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.Utils.RecycleItemTouchHelper;
import com.programasoft.drinkshop.Utils.RecycleItemTouchHelperListner;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private CartAdapter adapter;
private List<cart> list;
private LinearLayout rootLyout;
private IDrinkShopApi Mservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mservice=Common.getApi();
        recyclerView=(RecyclerView)this.findViewById(R.id.list_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rootLyout=(LinearLayout)this.findViewById(R.id.rootLyout);
        Database database= Database.getInstance(this);
        CartRepository repository=CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));


        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(repository.getCarts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<cart>>() {
            @Override
            public void accept(List<cart> carts) throws Exception {
                list=carts;
                adapter=new CartAdapter(CartActivity.this,carts);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }
        }));

        //iTEM Helper
        ItemTouchHelper.SimpleCallback callback=new RecycleItemTouchHelper(0, ItemTouchHelper.LEFT, new RecycleItemTouchHelperListner() {
            @Override
            public void OnSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
             final cart ItemDeleted=list.get(viewHolder.getAdapterPosition());
                //Delete Item
                Database database= Database.getInstance(getApplicationContext());
                final CartRepository repository=CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));
                repository.DeleteCart(ItemDeleted);
                //Ask for retry
                Snackbar snackbar=Snackbar.make(rootLyout,ItemDeleted.getName()+" Romoved From Favorites List",Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repository.InsertToCart(ItemDeleted);
                    }
                });
                snackbar.show();
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    public void PlaceOrder(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Submit order");
        View v= LayoutInflater.from(this).inflate(R.layout.submit_order_layout,null);
        final EditText txt_comment=(EditText)v.findViewById(R.id.txt_comment);
        final EditText txt_address=(EditText)v.findViewById(R.id.txt_address);
        final RadioButton user_addr=(RadioButton)v.findViewById(R.id.user_addr);
        final RadioButton other_addr=(RadioButton)v.findViewById(R.id.other_addr);
        user_addr.setChecked(true);
        txt_address.setEnabled(false);
        user_addr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(user_addr.isChecked()==true && other_addr.isChecked()==false )
             {txt_address.setEnabled(false);
             }else
             {txt_address.setEnabled(true);
             }
            }
        });

        other_addr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(user_addr.isChecked()==true && other_addr.isChecked()==false )
                {txt_address.setEnabled(false);
                }else
                {txt_address.setEnabled(true);
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String comment=txt_comment.getText().toString();
               String id_user=Common.CorrentUser.getPhone();
               boolean status=false;
               String address;
               if((other_addr.isChecked() && txt_address.getText().toString().isEmpty()==false) || user_addr.isChecked()==true) {
                   if (user_addr.isChecked()) {
                       address = Common.CorrentUser.getAddress();

                   } else {
                       address=txt_address.getText().toString();
                   }
                    for(final cart c:list)
                    {double price=c.getPrice();
                     int amount=c.getAcount();
                     String name=c.getName();
                     int ice=c.getIce();
                     int sugar=c.getSugar();
                     final String link=c.getLink();
                        Mservice.InsertNewOrder(status,price,amount,name,ice,sugar,link,comment,id_user,address).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getApplicationContext(),"Submit Orders"+list.indexOf(c),Toast.LENGTH_SHORT).show();
                                Database database= Database.getInstance(CartActivity.this);
                                CartRepository repository=CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));
                                repository.DeleteCart(c);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }




               }else{
                   Toast.makeText(getApplicationContext(), "Enter your address", Toast.LENGTH_SHORT).show();
               }
            }
        });
        builder.setView(v);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
