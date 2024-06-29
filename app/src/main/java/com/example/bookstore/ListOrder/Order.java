package com.example.bookstore.ListOrder;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    private int id; // Unique identifier for the order

    @SerializedName("title")
    private String title;

    @SerializedName("username")
    private String username;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("price")
    private String price;

    @SerializedName("status")
    private String status;

    @SerializedName("date")
    private String date;

    @SerializedName("arrival")
    private String arrival;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getArrival() {
        return arrival;
    }
}
