package com.example.treasure.database;

import static com.example.treasure.util.Constants.DATABASE_VERSION;

import android.content.Context;

import com.example.treasure.model.Feeling;
import com.example.treasure.util.Constants;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.treasure.model.Date;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main access point for the underlying connection to the local database.
 * <a href="https://developer.android.com/reference/kotlin/androidx/room/Database">...</a>
 */
@Database(entities = {Feeling.class}, version = 2, exportSchema = true)
public abstract class FeelingRoomDatabase extends RoomDatabase {

    public abstract FeelingDAO feelingDAO();

    private static volatile FeelingRoomDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static FeelingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeelingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FeelingRoomDatabase.class, Constants.SAVED_FEELING_DATABASE)
                            .allowMainThreadQueries().addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }


    //migrazione necessaria perchè è stata aggiunta
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Esegui le modifiche necessarie allo schema del database
            database.execSQL("ALTER TABLE Feeling RENAME TO Feeling_old");
            database.execSQL("CREATE TABLE Feeling (id INTEGER PRIMARY KEY NOT NULL, face INTEGER NOT NULL, text TEXT, date TEXT, time TEXT, condition TEXT)");
            database.execSQL("INSERT INTO Feeling (id, face, text, date, time) SELECT id, face, text, date, time FROM Feeling_old");
            database.execSQL("DROP TABLE Feeling_old");
        }
    };
}