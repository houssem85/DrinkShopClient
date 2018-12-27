package com.programasoft.drinkshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.programasoft.drinkshop.Adapter.DrinkAdapter;
import com.programasoft.drinkshop.Adapter.MenuAdapter;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.drink;
import com.programasoft.drinkshop.model.menu;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DrinkActivity extends AppCompatActivity {
private int menu_id;
   private TextView menu_name;

   private IDrinkShopApi mServise;
   private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        Intent intent = getIntent();
        menu_id= intent.getIntExtra("menu_id",0);
        menu_name=(TextView)this.findViewById(R.id.name_menu);
        menu_name.setText(intent.getStringExtra("menu_name"));
        mServise= Common.getApi();
        recyclerView=(RecyclerView)this.findViewById(R.id.list_drinks);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mServise.GetDrinks(menu_id+"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<drink>>() {
            @Override
            public void accept(List<drink> drink_list) throws Exception {
                DrinkAdapter drinkAdapter=new DrinkAdapter(DrinkActivity.this,drink_list);
                recyclerView.setAdapter(drinkAdapter);

            }
        });

    }
}
