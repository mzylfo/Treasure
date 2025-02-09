package com.example.treasure.source;

import com.example.treasure.model.Weather;

public interface WeatherCallback {
    void onSuccessFromRemote(Weather weatherApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(Weather weather);
    void onFailureFromLocal(Exception exception);
    void onWeatherStatusChanged(Weather weather);
}