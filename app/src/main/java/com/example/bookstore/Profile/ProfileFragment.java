package com.example.bookstore.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private TextView usernameTextView, emailTextView, passwordTextView, locationTextView;
    private Button editButton, logoutButton, deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        usernameTextView = view.findViewById(R.id.username);
        emailTextView = view.findViewById(R.id.email);
        passwordTextView = view.findViewById(R.id.password);
        locationTextView = view.findViewById(R.id.location);
        editButton = view.findViewById(R.id.edit_button);
        logoutButton = view.findViewById(R.id.logout_button);
        deleteButton = view.findViewById(R.id.delete_button);
        // Get user ID from SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("user_data", MODE_PRIVATE);
        userId = prefs.getInt("user_id", 0);

        if (userId != 0) {
            fetchUserDetails(userId);
        } else {
            Toast.makeText(getContext(), "User ID not found.", Toast.LENGTH_SHORT).show();
        }

        editButton.setOnClickListener(v -> showUpdateDialog());

        logoutButton.setOnClickListener(v -> {
            // Clear user data from SharedPreferences
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("user_data", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

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
                    String location = user.getLocation();

                    // Set user data to profile views
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                    passwordTextView.setText(password);
                    locationTextView.setText(location);
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

    private void showUpdateDialog() {
        // Inflate the dialog with custom view
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_update_user, null);

        // Initialize views in the dialog
        TextView titleTextView = dialogView.findViewById(R.id.title);
        EditText usernameEditText = dialogView.findViewById(R.id.username);
        EditText emailEditText = dialogView.findViewById(R.id.email);
        EditText passwordEditText = dialogView.findViewById(R.id.password);
        EditText locationEditText = dialogView.findViewById(R.id.location);

        // Set current values
        usernameEditText.setText(usernameTextView.getText().toString());
        emailEditText.setText(emailTextView.getText().toString());
        passwordEditText.setText(passwordTextView.getText().toString());
        locationEditText.setText(locationTextView.getText().toString());

        // Create and show the dialog
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button submitButton = dialogView.findViewById(R.id.submit);
        submitButton.setOnClickListener(v -> {
            // Handle the update logic
            String updatedUsername = usernameEditText.getText().toString();
            String updatedEmail = emailEditText.getText().toString();
            String updatedLocation = locationEditText.getText().toString();
            String updatedPassword = passwordEditText.getText().toString();

            if (TextUtils.isEmpty(updatedUsername) || TextUtils.isEmpty(updatedEmail) || TextUtils.isEmpty(updatedPassword)) {
                Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Call method to update user details
                updateUserDetails(userId, updatedUsername, updatedEmail, updatedPassword, updatedLocation, dialog);
            }
        });

        Button cancelButton = dialogView.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void updateUserDetails(int userId, String username, String email, String password, String location, AlertDialog dialog) {
        // Initialize Retrofit API Service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Create UpdateUserRequest object
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, username, email, password, location);

        // Call API to update user details
        Call<UserResponse> call = apiService.updateUserDetails(updateUserRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "User updated successfully.", Toast.LENGTH_SHORT).show();

                    // Update the TextViews with the new data
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                    locationTextView.setText(location);
                    passwordTextView.setText(password);
                    // Update other fields if necessary

                    dialog.dismiss(); // Dismiss the dialog after successful update
                } else {
                    Toast.makeText(getActivity(), "Failed to update user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Delete", (dialog, which) -> deleteUserFromServer())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteUserFromServer() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<UserResponse> call = apiService.deleteUser(userId);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "Account deleted successfully.", Toast.LENGTH_SHORT).show();

                    // Clear user data from SharedPreferences
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("user_data", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();

                    // Redirect to LoginActivity or perform any other necessary action
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Failed to delete account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
