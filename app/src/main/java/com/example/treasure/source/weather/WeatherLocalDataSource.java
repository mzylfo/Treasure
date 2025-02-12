package com.example.treasure.source.weather;

import static com.example.treasure.util.Constants.UNEXPECTED_ERROR;

import com.example.treasure.database.WeatherDAO;
import com.example.treasure.database.WeatherRoomDatabase;
import com.example.treasure.model.Weather;
import com.example.treasure.util.SharedPreferencesUtils;

import java.util.List;

/**
 * Class to get weather from local database using Room.
 */
public class WeatherLocalDataSource extends BaseWeatherLocalDataSource {

    private final WeatherDAO weatherDAO;
    private final SharedPreferencesUtils sharedPreferencesUtil;

    public WeatherLocalDataSource(WeatherRoomDatabase weatherRoomDatabase,
                                  SharedPreferencesUtils sharedPreferencesUtil) {
        this.weatherDAO = weatherRoomDatabase.weatherDAO();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    /**
     * Gets the weather from the local database.
     * The method is executed with an ExecutorService defined in WeatherRoomDatabase class
     * because the database access cannot be executed in the main thread.
     */
    @Override
    public void getWeather(String city) {
        WeatherRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Weather> weatherList = weatherDAO.getAll();
            if (weatherList != null && !weatherList.isEmpty()) {
                Weather weather = weatherList.get(0); // Recupera il primo elemento
                weatherCallback.onSuccessFromLocal(weather);
            } else {
                weatherCallback.onFailureFromLocal(new Exception("No weather data found"));
            }
        });
    }

    @Override
    public void updateWeather(Weather weather) {
        WeatherRoomDatabase.databaseWriteExecutor.execute(() -> {
            int rowUpdatedCounter = weatherDAO.update(weather);

            // It means that the update succeeded because only one row had to be updated
            if (rowUpdatedCounter == 1) {
                Weather updatedWeather = weatherDAO.getWeather(weather.getUid());
                weatherCallback.onWeatherStatusChanged(updatedWeather);
            } else {
                weatherCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    /**
     * Saves the weather in the local database.
     * The method is executed with an ExecutorService defined in WeatherRoomDatabase class
     * because the database access cannot be executed in the main thread.
     */
    @Override
    public void insertWeather(Weather weather) {
        WeatherRoomDatabase.databaseWriteExecutor.execute(() -> {
            weatherDAO.insert(weather);
            sharedPreferencesUtil.writeStringData("com.example.treasure.preference",
                    "last_update", String.valueOf(System.currentTimeMillis()));
            weatherCallback.onSuccessFromLocal(weather);
        });
    }
}