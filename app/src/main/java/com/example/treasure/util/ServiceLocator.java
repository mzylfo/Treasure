package com.example.treasure.util;

import android.app.Application;

import com.example.treasure.R;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.database.FeelingRoomDatabase;
import com.example.treasure.database.WeatherRoomDatabase;
import com.example.treasure.repository.user.IUserRepository;
import com.example.treasure.repository.user.UserRepository;
import com.example.treasure.repository.weather.WeatherRepository;
import com.example.treasure.service.WeatherApiService;
import com.example.treasure.source.event.BaseEventLocalDataSource;
import com.example.treasure.source.event.EventLocalDataSource;
import com.example.treasure.source.feeling.BaseFeelingLocalDataSource;
import com.example.treasure.source.feeling.FeelingLocalDataSource;
import com.example.treasure.source.user.BaseUserAuthenticationRemoteDataSource;
import com.example.treasure.source.user.BaseUserDataRemoteDataSource;
import com.example.treasure.source.user.UserAuthenticationFirebaseDataSource;
import com.example.treasure.source.user.UserFirebaseDataSource;
import com.example.treasure.source.weather.BaseWeatherLocalDataSource;
import com.example.treasure.source.weather.BaseWeatherRemoteDataSource;
import com.example.treasure.source.weather.WeatherLocalDataSource;
import com.example.treasure.source.weather.WeatherMockDataSource;
import com.example.treasure.source.weather.WeatherRemoteDataSource;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized (ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .build();
                return chain.proceed(request);
            })
            .build();

    public WeatherApiService getWeatherAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHERAPI_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(WeatherApiService.class);
    }


    /**
     * Returns an instance of WeatherRoomDatabase class to manage Room database.
     * @param application Param for accessing the global application state.
     * @return An instance of NewsRoomDatabase.
     */
    public WeatherRoomDatabase getWeatherDao(Application application) {
        return WeatherRoomDatabase.getDatabase(application);
    }


    public EventRoomDatabase getEventDao(Application application) {
        return EventRoomDatabase.getDatabase(application);
    }


    public FeelingRoomDatabase getFeelingDao(Application application) {
        return FeelingRoomDatabase.getDatabase(application);
    }

    /**
     * Returns an instance of WeatherRepositoryWithLiveData.
     * @param application Param for accessing the global application state.
     * @param debugMode Param to establish if the application is run in debug mode.
     * @return An instance of INewsRepositoryWithLiveData.
     */
    public WeatherRepository getWeatherRepository(Application application, boolean debugMode) {
        BaseWeatherRemoteDataSource newsRemoteDataSource;
        BaseWeatherLocalDataSource newsLocalDataSource;
        SharedPreferencesUtils sharedPreferencesUtil = new SharedPreferencesUtils(application);

        if (debugMode) {
            JSONParserUtils jsonParserUtil = new JSONParserUtils(application);
            newsRemoteDataSource =
                    new WeatherMockDataSource(jsonParserUtil);
        } else {
            newsRemoteDataSource =
                    new WeatherRemoteDataSource(application.getString(R.string.weather_api_key));
        }

        newsLocalDataSource = new WeatherLocalDataSource(getWeatherDao(application), sharedPreferencesUtil);

        return new WeatherRepository(newsRemoteDataSource, newsLocalDataSource);
    }

    public IUserRepository getUserRepository(Application application) {
        SharedPreferencesUtils sharedPreferencesUtil = new SharedPreferencesUtils(application);

        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationFirebaseDataSource();

        BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserFirebaseDataSource(sharedPreferencesUtil);

        BaseWeatherLocalDataSource weatherLocalDataSource =
                new WeatherLocalDataSource(getWeatherDao(application), sharedPreferencesUtil);

        BaseEventLocalDataSource eventLocalDataSource =
                new EventLocalDataSource(getEventDao(application), sharedPreferencesUtil);

        BaseFeelingLocalDataSource feelingLocalDataSource =
                new FeelingLocalDataSource(getFeelingDao(application), sharedPreferencesUtil);

        return new UserRepository(userRemoteAuthenticationDataSource,
                userDataRemoteDataSource, weatherLocalDataSource, eventLocalDataSource, feelingLocalDataSource);
    }
}