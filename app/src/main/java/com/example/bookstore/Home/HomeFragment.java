package com.example.bookstore.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookstore.Api.ApiBook;
import com.example.bookstore.R;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private RecyclerView rvBook;
    private RecyclerView rvBanner;
    private BooksAdapter booksAdapter;
    private BannerAdapter bannerAdapter;

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBook = view.findViewById(R.id.rv_book);
        rvBanner = view.findViewById(R.id.rv_banner);

        // Setup RecyclerView with GridLayoutManager
        rvBook.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Setup Banner RecyclerView with LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvBanner.setLayoutManager(layoutManager);

        // Sample banner images from drawable
        ArrayList<Integer> bannerImages = new ArrayList<>(Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3
        ));

        // Set up banner adapter
        bannerAdapter = new BannerAdapter(bannerImages);
        rvBanner.setAdapter(bannerAdapter);

        // Execute AsyncTask to fetch data and update RecyclerView
        new ApiBook() {
            @Override
            protected void onPostExecute(ArrayList<Book> books) {
                booksAdapter = new BooksAdapter(books);
                rvBook.setAdapter(booksAdapter);
            }
        }.execute();

        return view;
    }
}
