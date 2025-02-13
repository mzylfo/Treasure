package com.example.treasure.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.treasure.model.Weather;

import java.util.List;

@Dao
public interface WeatherDAO {
    @Query("SELECT * FROM Weather")
    List<Weather> getAll();

    @Query("SELECT * FROM Weather ORDER BY uid DESC LIMIT 1")
    Weather getWeather();

    @Query("SELECT * FROM Weather WHERE uid = :uid")
    Weather getWeather(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Weather... weathers);

    @Update
    int update(Weather weather);

    @Insert
    void insertAll(Weather... weathers);

    @Delete
    void deleteAll(Weather... weathers);
}