package com.example.treasure.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.treasure.model.Date;
import com.example.treasure.model.Event;
import com.example.treasure.model.EventApiResponse;

import java.util.List;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM Event")
    List<Event> getAll();

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event events);

    // Nuova funzione per inserire un singolo evento
    @Insert
    void insert(Event event);
}