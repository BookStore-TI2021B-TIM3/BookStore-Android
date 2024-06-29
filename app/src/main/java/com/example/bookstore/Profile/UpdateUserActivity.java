package com.example.bookstore.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.Api.ApiClient;
import com.example.bookstore.Api.ApiService;
import com.example.bookstore.Detail.OrderActivity;
import com.example.bookstore.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, locationEditText, phoneEditText;
    private Button submitButton, cancelButton;
    private ProgressDialog progressDialog;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        locationEditText = findViewById(R.id.location);
        phoneEditText = findViewById(R.id.phone);
        submitButton = findViewById(R.id.submit);
        cancelButton = findViewById(R.id.cancel);

        // Get intent extras
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", 0);
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String location = intent.getStringExtra("location");
        String phone = intent.getStringExtra("phone");

        // Set initial values
        usernameEditText.setText(username);
        emailEditText.setText(email);
        passwordEditText.setText(password);
        locationEditText.setText(location);
        phoneEditText.setText(phone);

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating user...");
        progressDialog.setCancelable(false);

        // Handle submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedUsername = usernameEditText.getText().toString().trim();
                String updatedEmail = emailEditText.getText().toString().trim();
                String updatedPassword = passwordEditText.getText().toString().trim();
                String updatedLocation = locationEditText.getText().toString().trim();
                String updatedPhone = phoneEditText.getText().toString().trim();

                if (TextUtils.isEmpty(updatedUsername) || TextUtils.isEmpty(updatedEmail)) {
                    Toast.makeText(UpdateUserActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Show progress dialog
                    progressDialog.show();

                    // Create Retrofit instance
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);

                    // Create UpdateUserRequest object
                    UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, updatedUsername, updatedEmail, updatedPassword, updatedPhone, updatedLocation);

                    // Make POST request
                    Call<UserResponse> call = apiService.updateUserDetails(updateUserRequest);
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                UserResponse userResponse = response.body();
                                if (userResponse != null && userResponse.getStatus().equals("success")) {
                                    Toast.makeText(UpdateUserActivity.this, "User details updated successfully", Toast.LENGTH_SHORT).show();
                                    // Start OrderActivity with the updated username
                                    Intent intent = new Intent(UpdateUserActivity.this, OrderActivity.class);
                                    intent.putExtra("username", updatedUsername);
                                    startActivity(intent);
                                    finish(); // Close activity after successful update
                                } else {
                                    String errorMessage = "Failed to update user details";
                                    if (userResponse != null && userResponse.getMessage() != null) {
                                        errorMessage += ": " + userResponse.getMessage();
                                    }
                                    Toast.makeText(UpdateUserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UpdateUserActivity.this, "Failed to update user details", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateUserActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Handle cancel button click
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Simply finish the activity if cancel button is clicked
            }
        });
    }
}
