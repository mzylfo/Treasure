package com.example.treasure.ui.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.treasure.model.Result;
import com.example.treasure.repository.weather.WeatherRepository;

public class WeatherViewModel extends ViewModel {

    private static final String TAG = WeatherViewModel.class.getSimpleName();

    private final WeatherRepository weatherRepository;
    private MutableLiveData<Result> weatherListLiveData;

    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Result> getWeather(String city, String conditions, long lastUpdate) {
        fetchWeather(city, conditions, lastUpdate);
        return weatherListLiveData;
    }

    private void fetchWeather(String city, String conditions, long lastUpdate) {
        weatherRepository.fetchWeather(city, conditions, lastUpdate).observeForever(result -> {
            weatherListLiveData.setValue(result);
        });
    }
}