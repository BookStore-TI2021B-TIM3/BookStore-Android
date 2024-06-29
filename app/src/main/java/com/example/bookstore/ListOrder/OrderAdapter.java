package com.example.bookstore.ListOrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookstore.R;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.title.setText(order.getTitle());
        holder.username.setText(order.getUsername());
        holder.phone.setText(order.getPhone());
        holder.address.setText(order.getAddress());
        holder.price.setText(order.getPrice());
        holder.status.setText(order.getStatus());
        holder.arrival.setText(order.getArrival());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView title, username, phone, address, price, status, arrival;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_book);
            username = itemView.findViewById(R.id.textViewName);
            phone = itemView.findViewById(R.id.textPhone);
            address = itemView.findViewById(R.id.textViewAddress);
            price = itemView.findViewById(R.id.textViewPrice);
            status = itemView.findViewById(R.id.textStatus);
            arrival = itemView.findViewById(R.id.textEstimated);
        }
    }
}
