package com.example.bookstore.Api;

import android.os.AsyncTask;
import com.example.bookstore.Home.Book;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBook extends AsyncTask<Void, Void, ArrayList<Book>> {

    @Override
    protected ArrayList<Book> doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.81.67/Web_BookStore/Connection/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Book>> call = apiService.fetchBooks();

        ArrayList<Book> books = new ArrayList<>();
        try {
            Response<ArrayList<Book>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                books = response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    protected void onPostExecute(ArrayList<Book> books) {
        // Handle UI updates here if necessary
    }
}
