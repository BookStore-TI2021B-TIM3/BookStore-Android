package com.example.bookstore.Api;

public class RegisterUser {
    private String username;
    private String email;
    private String password;
    private String phone; // Changed to String
    private String location;

    public RegisterUser(String username, String email, String password, String phone, String location) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
