package com.example.astronomicalpictures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "hOv2QoNA7XhV42uizj77imic0dxjaWwYhP3MtgaH";
    private final static String startDate = "2020-11-01";
    private final static String endDate = "2020-11-30";
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.main_progress);
        progressBar.setVisibility(View.VISIBLE);

        fetchData();
    }

    void fetchData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<Picture>> call = apiService.getPictures(startDate, endDate, API_KEY);

        call.enqueue(new Callback<ArrayList<Picture>>() {
            @Override
            public void onResponse(Call<ArrayList<Picture>> call, Response<ArrayList<Picture>> response) {
                ArrayList<Picture> pictures = response.body();

                progressBar.setVisibility(View.GONE);

                recyclerView = findViewById(R.id.pictures_rv);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(new PictureAdapter(pictures));

                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Picture picture = pictures.get(position);

                        Intent intentContestDetail = new Intent(MainActivity.this, PictureDetail.class);
                        Bundle extras = new Bundle();
                        extras.putParcelable("EXTRA_PICTURE", picture);
                        intentContestDetail.putExtras(extras);
                        startActivity(intentContestDetail);
                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<Picture>> call, Throwable t) {

            }
        });
    }

}