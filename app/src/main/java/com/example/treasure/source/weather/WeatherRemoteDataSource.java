package com.example.treasure.source.weather;

import static android.content.ContentValues.TAG;
import static com.example.treasure.util.Constants.API_KEY_ERROR;
import static com.example.treasure.util.Constants.RETROFIT_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.treasure.model.Weather;
import com.example.treasure.service.WeatherApiService;
import com.example.treasure.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class to get news from a remote source using Retrofit.
 */
public class WeatherRemoteDataSource extends BaseWeatherRemoteDataSource {

    private final WeatherApiService weatherAPIService;
    private final String apiKey;

    public WeatherRemoteDataSource(String apiKey) {
        this.apiKey = apiKey;
        this.weatherAPIService = ServiceLocator.getInstance().getWeatherAPIService();
    }

    @Override
    public void getWeather(String city, String conditions) {
        Call<Weather> newsResponseCall = weatherAPIService.getWeather(apiKey, city, conditions);

        newsResponseCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "API Response: " + response.body().toString()); // Log per vedere la risposta dell'API
                    weatherCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                } else {
                    Log.e(TAG, "API Response is null or unsuccessful");
                    weatherCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                Log.e(TAG, "API Call failed: " + t.getMessage());
                weatherCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }
        });
    }
}