package com.example.treasure.source.weather;

import static com.example.treasure.util.Constants.API_KEY_ERROR;

import com.example.treasure.model.Weather;
import com.example.treasure.util.Constants;
import com.example.treasure.util.JSONParserUtils;

import java.io.IOException;

/**
 * Class to get the news from a local JSON file to simulate the Web Service response.
 */
public class WeatherMockDataSource extends BaseWeatherRemoteDataSource {

    private final JSONParserUtils jsonParserUtil;

    public WeatherMockDataSource(JSONParserUtils jsonParserUtil) {
        this.jsonParserUtil = jsonParserUtil;
    }

    @Override
    public void getWeather(String country, String conditions) {
        Weather weatherApiResponse = null;

        try {
            weatherApiResponse = jsonParserUtil.parseJSONFileWithGSon(Constants.SAMPLE_WEATHER_API);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (weatherApiResponse != null) {
            weatherCallback.onSuccessFromRemote(weatherApiResponse, System.currentTimeMillis());
        } else {
            weatherCallback.onFailureFromRemote(String.valueOf(new Exception(API_KEY_ERROR)));
        }
    }
}