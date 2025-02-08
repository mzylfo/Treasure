package com.example.treasure.service;

import com.example.treasure.model.WeatherApiResponse;
import com.example.treasure.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET(Constants.CURRENT)
    Call<WeatherApiResponse> getWeather(
            @Header("key") String apiKey,
            @Query("q") String city,
            @Query("aqi") String conditions);

}
