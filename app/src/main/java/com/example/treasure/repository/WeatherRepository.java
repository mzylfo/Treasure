package com.example.treasure.repository;

import static com.example.treasure.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.treasure.R;
import com.example.treasure.database.WeatherDAO;
import com.example.treasure.database.WeatherRoomDatabase;
import com.example.treasure.model.WeatherApiResponse;
import com.example.treasure.service.ServiceLocator;
import com.example.treasure.service.WeatherApiService;
import com.example.treasure.util.ResponseCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository implements IWeatherRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();

    private final Application application;
    private final WeatherApiService weatherAPIService;
    private final WeatherDAO weatherDAO;
    private final ResponseCallback responseCallback;
    private final ExecutorService executorService;

    public WeatherRepository(Application application, ResponseCallback responseCallback) {
        this.application = application;
        this.weatherAPIService = ServiceLocator.getInstance().getWeatherAPIService();
        this.weatherDAO = ServiceLocator.getInstance().getWeatherDB(application).weatherDAO();
        this.responseCallback = responseCallback;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void fetchWeather(String city, String conditions, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > FRESH_TIMEOUT) {
            Call<WeatherApiResponse> weatherResponseCall = weatherAPIService.getWeather(
                    application.getString(R.string.news_api_key), city, conditions);

            weatherResponseCall.enqueue(new Callback<WeatherApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<WeatherApiResponse> call,
                                       @NonNull Response<WeatherApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Weather Response: " + response.body().toString());
                        saveDataInDatabase(response.body());
                    } else {
                        Log.e(TAG, "Errore nella risposta del server: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WeatherApiResponse> call, @NonNull Throwable throwable) {
                    Log.e(TAG, "API call failed: " + throwable.getMessage());
                    readDataFromDatabase(lastUpdate);
                }
            });
        } else {
            readDataFromDatabase(lastUpdate);
        }
    }

    private void saveDataInDatabase(WeatherApiResponse weather) {
        executorService.execute(() -> {
            try {
                weatherDAO.insert(weather);
                responseCallback.onSuccess(weather, System.currentTimeMillis());
            } catch (Exception e) {
                Log.e(TAG, "Errore durante l'inserimento nel database", e);
            }
        });
    }

    @Override
    public void updateWeather(WeatherApiResponse weather) {
        executorService.execute(() -> {
            try {
                weatherDAO.update(weather);
                responseCallback.onSuccess(weather, System.currentTimeMillis());
            } catch (Exception e) {
                Log.e(TAG, "Errore durante l'aggiornamento nel database", e);
            }
        });
    }

    @Override
    public void deleteWeather() {
        executorService.execute(() -> {
            try {
                weatherDAO.deleteAll();
                responseCallback.onSuccess(null, System.currentTimeMillis());
            } catch (Exception e) {
                Log.e(TAG, "Errore durante l'eliminazione nel database", e);
            }
        });
    }

    private void readDataFromDatabase(long lastUpdate) {
        WeatherRoomDatabase.databaseWriteExecutor.execute(() -> {
            responseCallback.onSuccess(weatherDAO.getAll(), lastUpdate);
        });
    }
}