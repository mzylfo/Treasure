package com.example.treasure.source.weather;

import com.example.treasure.model.Weather;
import com.example.treasure.repository.weather.WeatherResponseCallback;

public abstract class BaseWeatherLocalDataSource {
    protected WeatherResponseCallback weatherCallback;

    public void setWeatherResponseCallback(WeatherResponseCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    public abstract void getWeather(String city);

    public abstract void updateWeather(Weather weather);

    public abstract void insertWeather(Weather weather);
}