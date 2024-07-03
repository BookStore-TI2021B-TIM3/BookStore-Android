package com.example.bookstore.Detail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.Api.ApiService;
import com.example.bookstore.Api.RetrofitClient;
import com.example.bookstore.Home.Book;
import com.example.bookstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";

    private ImageView bookImage;
    private TextView bookTitle, bookPrice, bookSynopsis, tvAuthor;
    private EditText etUsername, etPhone, etAddress, etDate;
    private Button buyButton;

    private Book book;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bookImage = findViewById(R.id.img_list);
        bookTitle = findViewById(R.id.tv_title);
        bookPrice = findViewById(R.id.label_price);
        bookSynopsis = findViewById(R.id.tv_synopsis);
        tvAuthor = findViewById(R.id.tv_author);
        etUsername = findViewById(R.id.username);
        etPhone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);
        etDate = findViewById(R.id.date);
        buyButton = findViewById(R.id.buy_button);

        book = getIntent().getParcelableExtra("book");

        if (book != null) {
            bookTitle.setText(book.getTitle());
            bookPrice.setText(book.getPrice());
            bookSynopsis.setText(book.getSynopsis());
            // Make sure to use the full URL here as well
            String imageUrl = "http://192.168.81.67/Web_BookStore/assets/img/" + book.getImageUrl(); // Replace with your server URL
            Picasso.get().load(imageUrl).into(bookImage);
            tvAuthor.setText(book.getAuthor());
        }

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String phone = prefs.getString("phone", "");
        String address = prefs.getString("address", "");

        // Check if there are updated details passed from UpdateUserActivity
        Intent intent = getIntent();
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username");
        }
        if (intent.hasExtra("phone")) {
            phone = intent.getStringExtra("phone");
        }
        if (intent.hasExtra("address")) {
            address = intent.getStringExtra("address");
        }

        etUsername.setText(username);
        etPhone.setText(phone);
        etAddress.setText(address);

        calendar = Calendar.getInstance();

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = book.getTitle();
                String price = book.getPrice();
                String username = etUsername.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String date = etDate.getText().toString().trim();

                if (username.isEmpty() || phone.isEmpty() || address.isEmpty() || date.isEmpty()) {
                    Toast.makeText(OrderActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    submitOrder(title, price, username, phone, address, date);
                }
            }
        });
    }

    // Method to show DatePickerDialog
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Set selected date to Calendar
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Update EditText with selected date
                updateDateEditText();
            }
        };

        // Create DatePickerDialog
        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    // Method to update EditText with selected date
    private void updateDateEditText() {
        String myFormat = "dd-MM-yyyy"; // Format date
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(calendar.getTime()));
    }

    // Method to submit order
    private void submitOrder(String title, String price, String username, String phone, String address, String date) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.submitOrder(title, price, "", username, phone, address, date);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OrderActivity.this, "Order submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderActivity.this, "Failed to submit order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
