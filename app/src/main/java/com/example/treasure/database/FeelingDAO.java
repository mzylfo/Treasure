package com.example.treasure.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;

import java.util.List;

@Dao
public interface FeelingDAO {
    @Query("SELECT * FROM Feeling")
    List<Feeling> getAll();

    @Query("SELECT * FROM Feeling WHERE date = :date")
    List<Feeling> getFeelingsByDate(String date);

    @Query("SELECT COUNT(*) FROM Feeling WHERE strftime('%m %Y', date) = :formattedDate")
    int countHappiness(String formattedDate);

    @Query("SELECT COUNT(*) FROM Feeling WHERE face = 'sad' AND strftime('%m %Y', date) = :formattedDate")
    int countSadness(String formattedDate);

    @Query("SELECT COUNT(*) FROM Feeling WHERE face = 'neutral' AND strftime('%m %Y', date) = :formattedDate")
    int countNeutral(String formattedDate);

    @Insert
    void insertAll(Feeling... feelings);

    @Delete
    void delete(Feeling feelings);

    // Nuova funzione per inserire un singolo evento
    @Insert
    void insert(Feeling feeling);
}