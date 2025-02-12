package com.example.treasure.source.weather;

import com.example.treasure.repository.weather.WeatherResponseCallback;

/**
 * Base class to get weather from a remote source.
 */
public abstract class BaseWeatherRemoteDataSource {
    protected WeatherResponseCallback weatherCallback;

    public void setWeatherCallback(WeatherResponseCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    public abstract void getWeather(String city, String conditions);
}