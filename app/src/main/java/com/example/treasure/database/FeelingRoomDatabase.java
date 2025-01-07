package com.example.treasure.database;

import static com.example.treasure.util.Constants.DATABASE_VERSION;

import android.content.Context;

import com.example.treasure.model.Feeling;
import com.example.treasure.util.Constants;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.treasure.model.Date;

/**
 * Main access point for the underlying connection to the local database.
 * <a href="https://developer.android.com/reference/kotlin/androidx/room/Database">...</a>
 */
@Database(entities = {Feeling.class}, version = DATABASE_VERSION, exportSchema = true)
public abstract class FeelingRoomDatabase extends RoomDatabase {

    public abstract FeelingDAO feelingDAO();

    private static volatile FeelingRoomDatabase INSTANCE;

    public static FeelingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeelingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FeelingRoomDatabase.class, Constants.SAVED_FEELING_DATABASE)
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}