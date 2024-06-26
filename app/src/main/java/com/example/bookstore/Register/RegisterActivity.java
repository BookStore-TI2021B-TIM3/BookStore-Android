package com.example.bookstore.Register;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.Api.ApiClient;
import com.example.bookstore.Api.ApiService;
import com.example.bookstore.Api.RegisterUser;
import com.example.bookstore.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword, edtPhone, edtLocation;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edt_username_login);
        edtEmail = findViewById(R.id.edt_email_login);
        edtPassword = findViewById(R.id.edt_password_login);
        btnRegister = findViewById(R.id.registerButton);
        edtLocation = findViewById(R.id.LocationET);
        edtPhone = findViewById(R.id.PhoneET);

        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String location = edtLocation.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim(); // Phone as String

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                registerUser(username, email, password, phone, location.isEmpty() ? null : location);
            } else {
                Toast.makeText(RegisterActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });

        CheckBox passwordVisibleCheckbox = findViewById(R.id.passwordVisible);
        EditText passwordEditText = findViewById(R.id.edt_password_login);

        passwordVisibleCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                // Hide password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    private void registerUser(String username, String email, String password, String phone, String location) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RegisterUser user = new RegisterUser(username, email, password, phone, location);

        Call<RegisterResponse> call = apiService.registerUser(user);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();
                    if (registerResponse.getStatus().equals("success")) {
                        // Save user data to SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("phone", phone);
                        editor.apply();

                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
