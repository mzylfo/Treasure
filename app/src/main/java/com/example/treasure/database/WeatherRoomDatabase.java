package com.example.treasure.database;

import static com.example.treasure.util.Constants.DATABASE_VERSION;

import android.content.Context;

import com.example.treasure.model.Current;
import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.example.treasure.model.WeatherApiResponse;
import com.example.treasure.util.Constants;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main access point for the underlying connection to the local database.
 * <a href="https://developer.android.com/reference/kotlin/androidx/room/Database">...</a>
 */
@Database(entities = {WeatherApiResponse.class}, version = DATABASE_VERSION, exportSchema = true)
public abstract class WeatherRoomDatabase extends RoomDatabase {

    public abstract WeatherDAO weatherDAO();

    private static volatile WeatherRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static WeatherRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    WeatherRoomDatabase.class, Constants.SAVED_WEATHER_DATABASE)
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}