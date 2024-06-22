package com.example.bookstore.Profile;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private User data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }

    public static class User {
        @SerializedName("username")
        private String username;

        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        @SerializedName("location")
        private String location;

        public String getUsername() {

            return username;
        }

        public String getEmail() {

            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getLocation() {
            return location;
        }
    }
}
