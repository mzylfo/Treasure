package com.example.treasure.ui.home.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.treasure.repository.WeatherRepository;
import com.example.treasure.ui.home.viewmodel.WeatherViewModel;

/**
 * Custom ViewModelProvider to be able to have a custom constructor
 * for the NewsViewModel class.
 */
public class WeatherViewModelFactory implements ViewModelProvider.Factory {

    private final WeatherRepository weatherRepository;

    public WeatherViewModelFactory(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModel(weatherRepository);
    }
}