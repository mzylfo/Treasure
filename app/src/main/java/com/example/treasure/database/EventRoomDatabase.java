package com.example.treasure.database;

import static com.example.treasure.util.Constants.DATABASE_VERSION;

import android.content.Context;

import com.example.treasure.model.Event;
import com.example.treasure.util.Constants;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.treasure.model.Date;

/**
 * Main access point for the underlying connection to the local database.
 * <a href="https://developer.android.com/reference/kotlin/androidx/room/Database">...</a>
 */
@Database(entities = {Event.class}, version = DATABASE_VERSION, exportSchema = true)
public abstract class EventRoomDatabase extends RoomDatabase {

    public abstract EventDAO eventDAO();

    private static volatile EventRoomDatabase INSTANCE;

    public static EventRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventRoomDatabase.class, Constants.SAVED_EVENT_DATABASE)
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}