package com.example.treasure.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.treasure.model.Event;

import java.util.List;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM Event")
    List<Event> getAll();

    @Query("SELECT * FROM Event WHERE date = :date ORDER BY time ASC")
    List<Event> getEventsByDate(String date);

    @Query("SELECT * FROM Event WHERE date = :date BETWEEN :currentTime AND :futureTime ORDER BY date ASC")
    List<Event> getEventsBetweenTimes(String date, String currentTime, String futureTime);

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event events);

    @Query("DELETE FROM Event WHERE id = :eventId")
    void deleteById(int eventId);

    // Funzione per eliminare tutte le entry
    @Query("DELETE FROM Event")
    void deleteAll();

    // Nuova funzione per inserire un singolo evento
    @Insert
    void insert(Event event);
}