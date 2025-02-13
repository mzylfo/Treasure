package com.example.treasure.repository.weather;

import com.example.treasure.model.Weather;

public interface WeatherResponseCallback {
    void onSuccessFromRemote(Weather weatherApiResponse, long lastUpdate);
    void onFailureFromRemote(String exception);
    void onSuccessFromLocal(Weather weather);
    void onFailureFromLocal(String exception);
    void onWeatherStatusChanged(Weather weather);

}