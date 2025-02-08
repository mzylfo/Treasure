package com.example.treasure.repository;

import android.app.Application;

import com.example.treasure.database.WeatherDAO;
import com.example.treasure.database.WeatherRoomDatabase;
import com.example.treasure.model.WeatherApiResponse;
import com.example.treasure.service.ServiceLocator;
import com.example.treasure.util.Constants;
import com.example.treasure.util.JSONParserUtils;
import com.example.treasure.util.ResponseCallback;

import java.io.IOException;

public class WeatherMockRepository implements IWeatherRepository {
    private final Application application;
    private final ResponseCallback responseCallback;
    private final WeatherDAO weatherDAO;

    public WeatherMockRepository(Application application, ResponseCallback responseCallback) {
        this.application = application;
        this.responseCallback = responseCallback;
        this.weatherDAO = ServiceLocator.getInstance().getWeatherDB(application).weatherDAO();
    }

    @Override
    public void fetchWeather(String city, String conditions, long lastUpdate) {
        JSONParserUtils jsonParserUtils = new JSONParserUtils(application.getApplicationContext());

        try {
            WeatherApiResponse weatherApiResponse = jsonParserUtils.parseJSONFileWithGSon(Constants.SAMPLE_WEATHER_API);
            if (weatherApiResponse != null) {
                saveDataInDatabase(weatherApiResponse);
            } else {
                responseCallback.onFailure("Failed to parse weather data");
            }
        } catch (IOException e) {
            responseCallback.onFailure("IOException occurred while parsing weather data: " + e.getMessage());
        }
    }

    private void saveDataInDatabase(WeatherApiResponse weather) {
        WeatherRoomDatabase.databaseWriteExecutor.execute(() -> {
            try {
                weatherDAO.insert(weather);
                responseCallback.onSuccess(weather, System.currentTimeMillis());
            } catch (Exception e) {
                responseCallback.onFailure("Failed to save weather data to database: " + e.getMessage());
            }
        });
    }

    @Override
    public void updateWeather(WeatherApiResponse weather) {
        // Implementazione del metodo updateWeather se necessario
    }

    @Override
    public void deleteWeather() {
        // Implementazione del metodo deleteWeather se necessario
    }

    private void readDataFromDatabase(long lastUpdate) {
        WeatherRoomDatabase.databaseWriteExecutor.execute(() -> {
            responseCallback.onSuccess(weatherDAO.getAll(), lastUpdate);
        });
    }
}