package com.example.bookstore.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private TextView usernameTextView, emailTextView, passwordTextView, phoneTextView, locationTextView;
    private Button editButton, logoutButton, deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        usernameTextView = view.findViewById(R.id.username);
        emailTextView = view.findViewById(R.id.email);
        passwordTextView = view.findViewById(R.id.password);
        phoneTextView = view.findViewById(R.id.phone);
        locationTextView = view.findViewById(R.id.location);
        editButton = view.findViewById(R.id.edit_button);
        logoutButton = view.findViewById(R.id.logout_button);
        deleteButton = view.findViewById(R.id.delete_button);

        // Get user ID from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_data", MODE_PRIVATE);
        userId = prefs.getInt("user_id", 0);

        if (userId != 0) {
            fetchUserDetails(userId);
        } else {
            Toast.makeText(requireContext(), "User ID not found.", Toast.LENGTH_SHORT).show();
        }

        editButton.setOnClickListener(v -> showUpdateDialog());

        logoutButton.setOnClickListener(v -> {
            // Clear user data from SharedPreferences
            SharedPreferences.Editor editor = requireActivity().getSharedPreferences("user_data", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
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
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse.User user = response.body().getData();
                    String username = user.getUsername();
                    String email = user.getEmail();
                    String password = user.getPassword();
                    String phone = user.getPhone(); // Changed to String
                    String location = user.getLocation();

                    // Set user data to profile views
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                    passwordTextView.setText(password);
                    phoneTextView.setText(phone);
                    locationTextView.setText(location);
                } else {
                    Toast.makeText(requireContext(), "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
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
        EditText phoneEditText = dialogView.findViewById(R.id.phone);
        EditText locationEditText = dialogView.findViewById(R.id.location);

        // Set current values
        usernameEditText.setText(usernameTextView.getText().toString());
        emailEditText.setText(emailTextView.getText().toString());
        passwordEditText.setText(passwordTextView.getText().toString());
        phoneEditText.setText(phoneTextView.getText().toString());
        locationEditText.setText(locationTextView.getText().toString());

        // Create and show the dialog
        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button submitButton = dialogView.findViewById(R.id.submit);
        submitButton.setOnClickListener(v -> {
            // Handle the update logic
            String updatedUsername = usernameEditText.getText().toString();
            String updatedEmail = emailEditText.getText().toString();
            String updatedPassword = passwordEditText.getText().toString();
            String updatedPhone = phoneEditText.getText().toString();
            String updatedLocation = locationEditText.getText().toString();

            if (TextUtils.isEmpty(updatedUsername) || TextUtils.isEmpty(updatedEmail) || TextUtils.isEmpty(updatedPassword)) {
                Toast.makeText(requireActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Call method to update user details
                updateUserDetails(userId, updatedUsername, updatedEmail, updatedPassword, updatedPhone, updatedLocation, dialog);
            }
        });

        Button cancelButton = dialogView.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void updateUserDetails(int userId, String username, String email, String password, String phone, String location, AlertDialog dialog) {
        // Initialize Retrofit API Service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Create UpdateUserRequest object
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, username, email, password, location, phone);

        // Call API to update user details
        Call<UserResponse> call = apiService.updateUserDetails(updateUserRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(requireActivity(), "User updated successfully.", Toast.LENGTH_SHORT).show();

                    // Update the TextViews with the new data
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                    passwordTextView.setText(password);
                    phoneTextView.setText(phone);
                    locationTextView.setText(location);

                    dialog.dismiss(); // Dismiss the dialog after successful update
                } else {
                    Toast.makeText(requireActivity(), "Failed to update user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_delete, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button deleteButton = dialogView.findViewById(R.id.delete_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);

        deleteButton.setOnClickListener(v -> {
            deleteUserFromServer();
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void deleteUserFromServer() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<UserResponse> call = apiService.deleteUser(userId);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(requireActivity(), "Account deleted successfully.", Toast.LENGTH_SHORT).show();

                    // Clear user data from SharedPreferences
                    SharedPreferences.Editor editor = requireActivity().getSharedPreferences("user_data", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();

                    // Redirect to LoginActivity or perform any other necessary action
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireActivity(), "Failed to delete account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
