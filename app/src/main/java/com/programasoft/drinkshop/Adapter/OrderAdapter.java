package com.programasoft.drinkshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programasoft.drinkshop.R;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.error;
import com.programasoft.drinkshop.model.order;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private Context context;
    private List<order> orderList;

    public OrderAdapter(Context context, List<order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {

        View v=LayoutInflater.from(context).inflate(R.layout.order_item_layout,parent,false);
        final OrderViewHolder holder=new OrderViewHolder(v);
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order order=orderList.get(position);
                IDrinkShopApi api=Common.getApi();
                api.UpdateStatusOrder(order.getId(),1).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful())
                        {    if(response.body())
                             {  orderList.remove(position);
                                OrderAdapter.this.notifyDataSetChanged();
                                holder.view_forground.setVisibility(View.GONE);
                                Toast.makeText(context,"Order Canceled",Toast.LENGTH_SHORT).show();

                             }else
                             {
                              Toast.makeText(context,"Fail Try againt",Toast.LENGTH_LONG).show();
                             }


                        }else {
                            try {
                                 Gson gson = new GsonBuilder().setLenient().create();
                                 error error = gson.fromJson(response.errorBody().string(), error.class);
                                 Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                     Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return holder;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {
        order order=orderList.get(position);
        holder.txt_id.setText("#"+order.getId());
        holder.txt_price.setText(order.getPrice()+" dt");
        holder.txt_comment.setText("Comment :"+order.getComment());
        holder.name.setText(order.getName());
        Picasso.with(context).load(order.getLink()).into(holder.image);
        holder.price.setText(order.getPrice()+" dt");
        holder.description.setText("ice:"+order.getIce()+"% sugar:"+order.getSugar()+"%");
        holder.txt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.view_forground.getVisibility()==View.GONE)
                holder.view_forground.setVisibility(View.VISIBLE);
                else
                {holder.view_forground.setVisibility(View.GONE);
                }
            }
        });

        if(order.getStatus()!=0)
        { holder.btn_cancel.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
