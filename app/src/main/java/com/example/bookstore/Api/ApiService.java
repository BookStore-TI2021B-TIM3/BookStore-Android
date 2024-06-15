package com.example.bookstore.Api;

import com.example.bookstore.Login.LoginResponse;
import com.example.bookstore.Register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login.php")
    Call<LoginResponse> loginUser(@Body LoginUser loginuser);

    @POST("register.php")
    Call<RegisterResponse> registerUser(@Body RegisterUser registeruser);
}
