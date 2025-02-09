package com.example.treasure.repository;

import com.example.treasure.model.Weather;

public interface IWeatherRepository {

    void updateWeather(Weather weather);
    void fetchWeather(String city, String condition, long lastUpdate);
    void deleteWeather();
}
