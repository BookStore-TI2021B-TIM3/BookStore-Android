package com.example.bookstore.Detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookstore.Home.Book;
import com.example.bookstore.R;
import com.squareup.picasso.Picasso;

public class OrderActivity extends AppCompatActivity {

    private ImageView bookImage;
    private TextView bookAuthor, bookTitle, bookPrice, bookSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bookImage = findViewById(R.id.img_list);
        bookAuthor = findViewById(R.id.tv_author);
        bookTitle = findViewById(R.id.tv_title);
        bookPrice = findViewById(R.id.label_price);
        bookSynopsis = findViewById(R.id.tv_synopsis);

        Book book = getIntent().getParcelableExtra("book");

        if (book != null) {
            bookAuthor.setText(book.getAuthor());
            bookTitle.setText(book.getTitle());
            bookPrice.setText(String.valueOf(book.getPrice()));
            bookSynopsis.setText(book.getSynopsis());
            Picasso.get().load(book.getImageUrl()).into(bookImage);
        }
    }
}
