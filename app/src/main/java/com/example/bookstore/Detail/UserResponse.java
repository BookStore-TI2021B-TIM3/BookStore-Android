package com.example.bookstore.Detail;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
