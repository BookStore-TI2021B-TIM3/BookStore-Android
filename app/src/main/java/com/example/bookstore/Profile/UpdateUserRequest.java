package com.example.bookstore.Profile;

public class UpdateUserRequest {
    private int id;
    private String username;
    private String email;
    private String password;
    private String location;
    private String phone;

    public UpdateUserRequest(int id, String username, String email, String password, String location, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
