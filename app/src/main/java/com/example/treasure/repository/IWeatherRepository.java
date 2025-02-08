package com.example.treasure.repository;

import com.example.treasure.model.WeatherApiResponse;
import com.example.treasure.service.WeatherApiService;

public interface IWeatherRepository {

    void updateWeather(WeatherApiResponse weather);
    void fetchWeather(String city, String condition, long lastUpdate);
    void deleteWeather();
}
