package com.programasoft.drinkshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programasoft.drinkshop.Adapter.OrderAdapter;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.error;
import com.programasoft.drinkshop.model.order;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {
private RecyclerView list_orders;
private OrderAdapter adapter;
private IDrinkShopApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oders);
        list_orders=(RecyclerView)this.findViewById(R.id.list_orders);
        list_orders.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        api=Common.getApi();
        api.GetOrdersByUser(Common.CorrentUser.getPhone()).enqueue(new Callback<List<order>>() {
            @Override
            public void onResponse(Call<List<order>> call, Response<List<order>> response) {
             if(response.isSuccessful())
             { adapter=new OrderAdapter(OrdersActivity.this,response.body());
               list_orders.setAdapter(adapter);

             }else
             { try {
                 Gson gson = new GsonBuilder().setLenient().create();
                 error error = gson.fromJson(response.errorBody().string(), error.class);
                 Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             }

            }

            @Override
            public void onFailure(Call<List<order>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
