package com.example.treasure.source;

/**
 * Base class to get weather from a remote source.
 */
public abstract class BaseWeatherRemoteDataSource {
    protected WeatherCallback weatherCallback;

    public void setWeatherCallback(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    public abstract void getWeather(String city, String conditions);
}