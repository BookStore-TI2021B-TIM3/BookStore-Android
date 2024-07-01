package com.example.bookstore.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.Detail.OrderActivity;
import com.example.bookstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private ArrayList<Book> books;
    private ArrayList<Book> filteredBooks;
    private Context context;

    public BooksAdapter(ArrayList<Book> books, Context context) {
        this.books = books;
        this.filteredBooks = new ArrayList<>(books);
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = filteredBooks.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.price.setText(book.getPrice());
        holder.rating.setText(String.valueOf(book.getRating()));

        // Load image with Picasso, handle empty imageUrl case
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            String imageUrl = "http://192.168.81.67/Web_BookStore/asset/" + book.getImageUrl(); // Replace with your server URL
            Picasso.get().load(imageUrl).into(holder.image);
        } else {
            // Handle case where imageUrl is empty or null
            holder.image.setImageResource(R.drawable.ic_launcher_background); // Placeholder image resource
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra("book", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredBooks.size();
    }

    public void filter(String query) {
        if (query.isEmpty()) {
            filteredBooks = new ArrayList<>(books);
        } else {
            filteredBooks = (ArrayList<Book>) books.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, price, rating;
        ImageView image;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.book_author);
            price = itemView.findViewById(R.id.price);
            rating = itemView.findViewById(R.id.rating);
            image = itemView.findViewById(R.id.image);
        }
    }
}
