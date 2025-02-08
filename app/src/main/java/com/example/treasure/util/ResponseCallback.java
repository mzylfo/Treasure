package com.example.treasure.util;

import com.example.treasure.model.Location;
import com.example.treasure.model.WeatherApiResponse;

import java.util.List;

public interface ResponseCallback {
    void onSuccess(WeatherApiResponse weather, long lastUpdate);
    void onFailure(String errorMessage);
}