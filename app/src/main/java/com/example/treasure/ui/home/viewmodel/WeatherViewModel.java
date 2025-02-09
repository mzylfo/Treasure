package com.example.treasure.ui.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.treasure.model.Weather;
import com.example.treasure.model.Result;
import com.example.treasure.repository.WeatherRepository;


public class WeatherViewModel extends ViewModel {

    private static final String TAG = WeatherViewModel.class.getSimpleName();

    private final WeatherRepository weatherRepository;
    private MutableLiveData<Result> weatherListLiveData;
    private MutableLiveData<Result> favoriteNewsListLiveData;

    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public MutableLiveData<Result> getWeather(String city, String conditions, long lastUpdate) {
        if (weatherListLiveData == null) {
            fetchWeather(city, conditions, lastUpdate);
        }
        return weatherListLiveData;
    }

    private void fetchWeather(String city, String conditions, long lastUpdate) {
        weatherListLiveData = weatherRepository.fetchWeather(city, conditions, lastUpdate);
    }

}