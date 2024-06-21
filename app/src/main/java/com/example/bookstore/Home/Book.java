package com.example.bookstore.Home;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int id;
    private String author;
    private String title;
    private String price;
    private float rating;
    private String imageUrl;
    private String synopsis;

    // Constructor
    public Book(int id, String author, String title, String price, float rating, String imageUrl, String synopsis) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.synopsis = synopsis;
    }

    // Parcelable implementation
    protected Book(Parcel in) {
        id = in.readInt();
        author = in.readString();
        title = in.readString();
        price = in.readString();
        rating = in.readFloat();
        imageUrl = in.readString();
        synopsis = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeFloat(rating);
        dest.writeString(imageUrl);
        dest.writeString(synopsis);
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
