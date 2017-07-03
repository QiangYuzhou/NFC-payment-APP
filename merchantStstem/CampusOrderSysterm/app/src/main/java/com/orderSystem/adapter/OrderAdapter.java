package com.orderSystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orderSystem.R;
import com.orderSystem.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Order> mlist;
    private LayoutInflater inflater;
    public OrderAdapter(Context context,
                        List<Order> list) {
        this.context=context;
        this.mlist=list;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder orderholder = (OrderViewHolder) holder;
        if (orderholder != null) {

            Order order = mlist.get(position);
            orderholder.orderName.setText(order.getName());
            orderholder.orderPrice.setText(order.getPrice()+"");
            orderholder.orderTime.setText(order.getOrderTime());
            orderholder.orderLayout.setContentDescription(position+"");

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    private class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView orderName;
        private TextView orderPrice;
        private TextView orderTime;
        private LinearLayout orderLayout;


        public OrderViewHolder(View itemView) {
            super(itemView);
            orderName = (TextView)itemView.findViewById(R.id.order_name);
            orderPrice = (TextView)itemView.findViewById(R.id.order_price);
            orderTime = (TextView) itemView.findViewById(R.id.order_time);
            orderLayout = (LinearLayout)itemView.findViewById(R.id.order_list);
        }

    }

}
