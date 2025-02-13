package com.example.treasure.util;

import com.example.treasure.model.Weather;

public interface ResponseCallback {
    void onSuccess(Weather weather, long lastUpdate);
    void onFailure(String errorMessage);
}