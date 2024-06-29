package com.example.bookstore.Detail;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.Home.Book;
import com.example.bookstore.R;

public class OrderBookActivity extends AppCompatActivity {

    private TextView tvTitle;
    private EditText etStatus;

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_book); // Ensure you have this layout file

        // Initialize views
        tvTitle = findViewById(R.id.tv_title);
        etStatus = findViewById(R.id.status);

        // Get the Book object from the intent
        book = getIntent().getParcelableExtra("book");

        // Display the book title
        if (book != null) {
            tvTitle.setText(book.getTitle());
        }

        // Set the status field to be empty by default
        etStatus.setText("");
    }
}
