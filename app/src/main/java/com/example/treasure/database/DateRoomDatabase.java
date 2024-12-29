package com.example.treasure.database;

import static com.example.treasure.util.Constants.DATABASE_VERSION;

import android.content.Context;
import com.example.treasure.util.Constants;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.treasure.model.Date;

/**
 * Main access point for the underlying connection to the local database.
 * <a href="https://developer.android.com/reference/kotlin/androidx/room/Database">...</a>
 */
@Database(entities = {Date.class}, version = DATABASE_VERSION, exportSchema = true)
public abstract class DateRoomDatabase extends RoomDatabase {

    //public abstract DateDAO dateDao();

    private static volatile DateRoomDatabase INSTANCE;

    public static DateRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DateRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DateRoomDatabase.class, Constants.SAVED_DATE_DATABASE)
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}