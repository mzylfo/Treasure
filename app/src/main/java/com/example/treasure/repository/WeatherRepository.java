package com.example.treasure.repository;

import static com.example.treasure.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.example.treasure.model.Weather;
import com.example.treasure.source.BaseWeatherLocalDataSource;
import com.example.treasure.source.BaseWeatherRemoteDataSource;
import com.example.treasure.source.WeatherCallback;
import com.example.treasure.model.Result;
import java.util.List;



/**
 * Repository class to get the news from local or from a remote source.
 */
public class WeatherRepository implements WeatherCallback {

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
        this.weatherLocalDataSource.setWeatherCallback(this);
    }

    public MutableLiveData<Result> fetchWeather(String city, String conditions, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        // It gets the news from the Web Service if the last download
        // of the news has been performed more than FRESH_TIMEOUT value ago
        if (currentTime - lastUpdate > FRESH_TIMEOUT) {
            weatherRemoteDataSource.getWeather(city, conditions);
        } else {
            weatherLocalDataSource.getWeather();
        }

        return weatherMutableLiveData;
    }


    public MutableLiveData<Result> getWeather() {
        weatherLocalDataSource.getWeather();
        return weatherMutableLiveData;
    }

    public void updateWeather(Weather weather) {
        weatherLocalDataSource.updateWeather(weather);
    }

    public void onSuccessFromRemote(Weather weatherApiResponse, long lastUpdate) {
        weatherLocalDataSource.insertWeather(weatherApiResponse);
    }

    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        weatherMutableLiveData.postValue(result);
    }

    public void onSuccessFromLocal(Weather weather){
        Result.Success result = new Result.Success(new Weather());
        weatherMutableLiveData.postValue(result);
    }

    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        weatherMutableLiveData.postValue(resultError);
        weatherMutableLiveData.postValue(resultError);
    }

    public void onWeatherStatusChanged(Weather weather) {
        weatherMutableLiveData.postValue(new Result.Success(new Weather()));
    }
}