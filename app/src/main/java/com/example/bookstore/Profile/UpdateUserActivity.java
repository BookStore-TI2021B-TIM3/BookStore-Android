package com.example.bookstore.Profile;

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
import com.example.bookstore.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, newPasswordEditText;
    private Button submitButton;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password); // Change here
        newPasswordEditText = findViewById(R.id.new_password);
        submitButton = findViewById(R.id.submit);

        // Get intent extras
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", 0);
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // Set initial values
        usernameEditText.setText(username);
        emailEditText.setText(email);
        passwordEditText.setText(password); // Set the retrieved password here

        // Handle submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedUsername = usernameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(updatedUsername) || TextUtils.isEmpty(updatedEmail)) {
                    Toast.makeText(UpdateUserActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call method to update user details
                    updateUserDetails(userId, updatedUsername, updatedEmail, password, newPassword);
                }
            }
        });
    }

    private void updateUserDetails(int userId, String username, String email, String password, String newPassword) {
        // Initialize Retrofit API Service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Create UpdateUserRequest object
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, username, email, password, newPassword);

        // Call API to update user details
        Call<UserResponse> call = apiService.updateUserDetails(updateUserRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(UpdateUserActivity.this, "User updated successfully.", Toast.LENGTH_SHORT).show();

                    // After successful update, send back updated data to previous Activity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updated_username", username);
                    resultIntent.putExtra("updated_email", email);
                    resultIntent.putExtra("updated_password", password);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(UpdateUserActivity.this, "Failed to update user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(UpdateUserActivity.this, "Failed to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

