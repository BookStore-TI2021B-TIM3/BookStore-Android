package com.example.bookstore.Login;

public class LoginResponse {
    private String status;
    private String message;
    private int user_id; // Add this field

    // Existing getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Add getter and setter for user_id
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
}
