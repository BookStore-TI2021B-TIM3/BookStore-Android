package com.example.bookstore.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.bookstore.Api.ApiClient;
import com.example.bookstore.Api.ApiService;
import com.example.bookstore.Login.LoginActivity;
import com.example.bookstore.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private int userId;
    private TextView usernameTextView, emailTextView, passwordTextView;
    private Button editButton, logoutButton;
    private static final int UPDATE_USER_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        usernameTextView = view.findViewById(R.id.username);
        emailTextView = view.findViewById(R.id.email);
        passwordTextView = view.findViewById(R.id.password);
        editButton = view.findViewById(R.id.edit_button);
        logoutButton = view.findViewById(R.id.logout_button);

        // Get user ID from SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("user_data", MODE_PRIVATE);
        userId = prefs.getInt("user_id", 0);

        if (userId != 0) {
            fetchUserDetails(userId);
        } else {
            Toast.makeText(getContext(), "User ID not found.", Toast.LENGTH_SHORT).show();
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateUserActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("username", usernameTextView.getText().toString());
            intent.putExtra("email", emailTextView.getText().toString());
            intent.putExtra("password", passwordTextView.getText().toString());
            startActivityForResult(intent, UPDATE_USER_REQUEST);
        });

        logoutButton.setOnClickListener(v -> {
            // Clear user data from SharedPreferences
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("user_data", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish(); // Close the current activity
        });

        return view;
    }

    private void fetchUserDetails(int userId) {
        // Initialize Retrofit API Service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Call the API to get user details
        Call<UserResponse> call = apiService.getUserDetails(userId);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse.User user = response.body().getData();
                    String username = user.getUsername();
                    String email = user.getEmail();
                    String password = user.getPassword();

                    // Set user data to profile views
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                    passwordTextView.setText(password);
                } else {
                    Toast.makeText(getContext(), "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_USER_REQUEST && resultCode == getActivity().RESULT_OK) {
            String updatedUsername = data.getStringExtra("updated_username");
            String updatedEmail = data.getStringExtra("updated_email");
            String updatedPassword = data.getStringExtra("updated_password");

            usernameTextView.setText(updatedUsername);
            emailTextView.setText(updatedEmail);
            passwordTextView.setText(updatedPassword);
        }
    }
}
