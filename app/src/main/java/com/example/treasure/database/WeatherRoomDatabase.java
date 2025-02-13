package com.example.treasure.database;

import static com.example.treasure.database.FeelingRoomDatabase.MIGRATION_1_2;
import static com.example.treasure.util.Constants.DATABASE_VERSION;

import android.content.Context;

import com.example.treasure.model.Weather;
import com.example.treasure.util.Constants;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main access point for the underlying connection to the local database.
 * <a href="https://developer.android.com/reference/kotlin/androidx/room/Database">...</a>
 */
@Database(entities = {Weather.class}, version = 2, exportSchema = true)
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
                            .allowMainThreadQueries().addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }

    //migrazione necessaria perchè lo schema è cambiato
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Esegui le modifiche necessarie allo schema del database
            database.execSQL("ALTER TABLE WeatherApiResponse RENAME TO Weather");
        }
    };
}