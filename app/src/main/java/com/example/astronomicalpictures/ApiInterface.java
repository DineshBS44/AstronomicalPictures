package com.example.astronomicalpictures;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("apod/")
    Call<ArrayList<Picture>> getPictures(@Query("start_date") String startTime, @Query("end_date") String endTime, @Query("api_key") String api_key);

}
