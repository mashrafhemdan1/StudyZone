package com.example.mystudyzone;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PeriodDao {
    @Query("SELECT * FROM Period")
    List<Period> getAll();

    @Insert
    void insertAll(Period... periods);

    @Insert
    void insertOne(Period period);

    @Delete
    void delete(Period period);
}
