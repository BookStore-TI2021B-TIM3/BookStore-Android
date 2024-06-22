package com.example.bookstore.Detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookstore.Home.Book;
import com.example.bookstore.R;
import com.squareup.picasso.Picasso;

public class OrderActivity extends AppCompatActivity {

    private ImageView bookImage;
    private TextView bookAuthor, bookTitle, bookPrice, bookSynopsis;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bookImage = findViewById(R.id.img_list);
        bookAuthor = findViewById(R.id.tv_author);
        bookTitle = findViewById(R.id.tv_title);
        bookPrice = findViewById(R.id.label_price);
        bookSynopsis = findViewById(R.id.tv_synopsis);
        buyButton = findViewById(R.id.buy_button);

        Book book = getIntent().getParcelableExtra("book");

        if (book != null) {
            bookAuthor.setText(book.getAuthor());
            bookTitle.setText(book.getTitle());
            bookPrice.setText(String.valueOf(book.getPrice()));
            bookSynopsis.setText(book.getSynopsis());
            Picasso.get().load(book.getImageUrl()).into(bookImage);
        }

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderDialog();
            }
        });
    }

    private void showOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setView(R.layout.text_dialog);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
