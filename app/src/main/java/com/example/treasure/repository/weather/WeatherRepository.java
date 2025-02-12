package com.example.treasure.repository.weather;

import static com.example.treasure.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.example.treasure.model.Weather;
import com.example.treasure.source.weather.BaseWeatherLocalDataSource;
import com.example.treasure.source.weather.BaseWeatherRemoteDataSource;
import com.example.treasure.model.Result;

/**
 * Repository class to get the news from local or from a remote source.
 */
public class WeatherRepository implements WeatherResponseCallback {

    private static final String TAG = WeatherRepository.class.getSimpleName();

    private final MutableLiveData<Result> weatherMutableLiveData;
    private final BaseWeatherRemoteDataSource weatherRemoteDataSource;
    private final BaseWeatherLocalDataSource weatherLocalDataSource;

    public WeatherRepository(BaseWeatherRemoteDataSource weatherRemoteDataSource,
                             BaseWeatherLocalDataSource weatherLocalDataSource) {

        weatherMutableLiveData = new MutableLiveData<>();
        this.weatherRemoteDataSource = weatherRemoteDataSource;
        this.weatherLocalDataSource = weatherLocalDataSource;
        this.weatherRemoteDataSource.setWeatherCallback(this);
        this.weatherLocalDataSource.setWeatherResponseCallback(this);
    }

    public MutableLiveData<Result> fetchWeather(String city, String conditions, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        // It gets the news from the Web Service if the last download
        // of the news has been performed more than FRESH_TIMEOUT value ago
        if (currentTime - lastUpdate > FRESH_TIMEOUT) {
            weatherRemoteDataSource.getWeather(city, conditions);
        } else {
            weatherLocalDataSource.getWeather(city);
        }

        return weatherMutableLiveData;
    }

    @Override
    public void onSuccessFromRemote(Weather weather, long lastUpdate) {
        weatherMutableLiveData.postValue(new Result.Success(weather));
        weatherLocalDataSource.insertWeather(weather);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        weatherMutableLiveData.postValue(new Result.Error(exception.getMessage()));
    }

    @Override
    public void onSuccessFromLocal(Weather weather) {
        weatherMutableLiveData.postValue(new Result.Success(weather));
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        weatherMutableLiveData.postValue(new Result.Error(exception.getMessage()));
    }

    @Override
    public void onWeatherStatusChanged(Weather weather) {

    }

}