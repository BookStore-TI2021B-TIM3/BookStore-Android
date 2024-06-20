package com.example.bookstore.Home;

public class Book {
    private int id;
    private String author;
    private String title;
    private String price;
    private float rating;
    private String imageUrl;
    private String synopsis;

    public Book(int id, String author, String title, String price, float rating, String imageUrl, String synopsis) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.synopsis = synopsis;
    }

    // Getters
    public int getId() { return id; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public float getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public String getSynopsis() { return synopsis; }
}
