package com.example.treasure.util;

public class Constants {
    public static final String TIME_ZONE = "Europe/Rome";
    public static final String SAMPLE_JSON_FILENAME = "sample_api_response.json";
    public static final int DATABASE_VERSION = 1;
    public static final String SAVED_DATE_DATABASE = "Date_db";
    public static final String SAVED_EVENT_DATABASE = "Event_db";
    public static final String SAVED_FEELING_DATABASE = "Feeling_db";
    public static final String SAVED_WEATHER_DATABASE = "Weather_db";
    public static final String SAMPLE_EVENT_LIST = "sample_event_list.json";
    public static final String SAMPLE_WEATHER_API = "sample_weather_api.json";

    // CONSTANTS FOR WEATHERAPI.COM
    public static final String WEATHERAPI_BASE_URL = "https://api.weatherapi.com/v1/";
    public static final String CURRENT = "current.json";
    public static final String CURRENT_CITY = "q";
    public static final String CONDITIONS = "aqi";
    public static final String HEADER = "key";
    public static final int FRESH_TIMEOUT = 1000 * 60; // 1 minute in milliseconds

    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";
    public static final String UNEXPECTED_ERROR = "unexpected_error";

    public static final String SHARED_PREFERENCES_FILENAME = "com.example.unimib.preferences";
    public static final String SHARED_PREFERNECES_LAST_UPDATE = "last_update";
}
