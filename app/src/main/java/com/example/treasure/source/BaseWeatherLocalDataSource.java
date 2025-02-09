package com.example.treasure.source;

import com.example.treasure.model.Weather;

import java.util.List;

public abstract class BaseWeatherLocalDataSource {
    protected WeatherCallback weatherCallback;

    public void setWeatherCallback(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    public abstract void getWeather();

    public abstract void updateWeather(Weather weather);

    public abstract void insertWeather(Weather weather);
}