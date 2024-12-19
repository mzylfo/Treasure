package com.example.treasure.service;

import com.example.treasure.model.TimeApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface TimeApiService {
    @GET("timezone/Etc/UTC")
    Call<TimeApiResponse> getCurrentTime();
}