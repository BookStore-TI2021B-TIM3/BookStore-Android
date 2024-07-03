package com.example.bookstore.ListOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.Api.ApiClient;
import com.example.bookstore.Api.ApiService;
import com.example.bookstore.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
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

        holder.cancelButton.setOnClickListener(v -> cancelOrder(order.getId(), position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    private void cancelOrder(int orderId, int position) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.cancelOrder(orderId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    orderList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Order canceled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to cancel order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView title, username, phone, address, price, status, arrival;
        Button cancelButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_book);
            username = itemView.findViewById(R.id.textViewName);
            phone = itemView.findViewById(R.id.textPhone);
            address = itemView.findViewById(R.id.textViewAddress);
            price = itemView.findViewById(R.id.textViewPrice);
            status = itemView.findViewById(R.id.textStatus);
            arrival = itemView.findViewById(R.id.textEstimated);
            cancelButton = itemView.findViewById(R.id.cancel_button);
        }
    }
}
