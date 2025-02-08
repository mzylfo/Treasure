package com.example.treasure.util;

import android.content.Context;

import com.example.treasure.model.EventApiResponse;
import com.example.treasure.model.TimeZoneResponse;
import com.example.treasure.model.WeatherApiResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONParserUtils {

    public Context context;

    public JSONParserUtils(Context context){
        this.context = context;
    }

    public WeatherApiResponse parseJSONFileWithGSon(String filename) throws IOException {
        InputStream inputStream = context.getAssets().open(filename);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return new Gson().fromJson(bufferedReader, WeatherApiResponse.class);
    }

    public EventApiResponse parseJSONEventWithGSon(String filename) throws IOException {
        InputStream inputStream = context.getAssets().open(filename);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return new Gson().fromJson(bufferedReader, EventApiResponse.class);
    }


}
