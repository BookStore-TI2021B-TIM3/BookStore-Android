package com.example.bookstore.Api;

import com.example.bookstore.Home.Book;
import com.example.bookstore.Login.LoginResponse;
import com.example.bookstore.Profile.UpdateUserRequest;
import com.example.bookstore.Profile.UserResponse;
import com.example.bookstore.Register.RegisterResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login.php")
    Call<LoginResponse> loginUser(@Body LoginUser loginUser);

    @POST("register.php")
    Call<RegisterResponse> registerUser(@Body RegisterUser registerUser);

    @GET("get_users_details.php")
    Call<UserResponse> getUserDetails(@Query("user_id") int userId);

    @POST("update_user.php")
    Call<UserResponse> updateUserDetails(@Body UpdateUserRequest updateUserRequest);

    @GET("fetch_book.php")
    Call<ArrayList<Book>> fetchBooks();
}
