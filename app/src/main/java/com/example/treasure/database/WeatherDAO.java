package com.example.treasure.database;

import com.example.treasure.model.Current;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.treasure.model.WeatherApiResponse;
import com.example.treasure.service.WeatherApiService;

@Dao
public interface WeatherDAO {
    @Query("SELECT * FROM WeatherApiResponse")
    WeatherApiResponse getAll();

    @Query("SELECT * FROM WeatherApiResponse")
    WeatherApiResponse getLatestWeather();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherApiResponse... weathers);

    @Update
    void update(WeatherApiResponse weather);

    @Insert
    void insertAll(WeatherApiResponse... weathers);

    @Delete
    void deleteAll(WeatherApiResponse... weathers);
}