package com.example.bookstore.Api;

import com.example.bookstore.Home.Book;
import com.example.bookstore.ListOrder.Order;
import com.example.bookstore.Login.LoginResponse;
import com.example.bookstore.Profile.UpdateUserRequest;
import com.example.bookstore.Profile.UserResponse;
import com.example.bookstore.Register.RegisterResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @DELETE("delete_user.php")
    Call<UserResponse> deleteUser(@Query("id") int userId);

    @GET("get_orders.php")
    Call<List<Order>> getOrdersByUsername(@Query("username") String username);

    @FormUrlEncoded
    @POST("create_order.php")
    Call<ResponseBody> submitOrder(
            @Field("title") String title,
            @Field("price") String price,
            @Field("status") String status,
            @Field("username") String username,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("date") String date
    );

    @POST("submitOrder.php")
    Call<ResponseBody> submitOrder(
            @Query("title") String title,
            @Query("price") String price,
            @Query("username") String username,
            @Query("phone") String phone,
            @Query("address") String address,
            @Query("date") String date
    );


}
