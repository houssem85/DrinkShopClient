package com.programasoft.drinkshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programasoft.drinkshop.R;
import com.programasoft.drinkshop.model.order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private Context context;
    private List<order> orderList;

    public OrderAdapter(Context context, List<order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.order_item_layout,parent,false);
        OrderViewHolder holder=new OrderViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        order order=orderList.get(position);
        holder.txt_id.setText("#"+order.getId());
        holder.txt_price.setText(order.getPrice()+" dt");
        holder.txt_comment.setText("Comment :"+order.getComment());
        String OrderStatus="Placed";
        if(order.isStatus())
        {OrderStatus=" not Placed";
        }
        holder.txt_status.setText("Order Status :"+OrderStatus);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
