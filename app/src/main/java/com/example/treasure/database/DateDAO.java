package com.example.treasure.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.treasure.model.Date;

import java.util.List;

@Dao
public interface DateDAO {
    @Query("SELECT * FROM Date")
    List<Date> getAll();

    @Insert
    void insertAll(Date... dates);

    @Delete
    void delete(Date date);
}