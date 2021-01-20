package com.example.mystudyzone;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DeadlineDao {
    @Query("SELECT * FROM Deadline")
    List<Deadline> getAll();

    @Query("SELECT * FROM Deadline WHERE date = (:date)")
    List<Deadline> getOnDate(String date);

    @Query("SELECT * FROM Deadline WHERE date > (:curDate) ORDER BY date, time")
    List<Deadline> getUpcoming(String curDate);

    @Insert
    void insertAll(Deadline... deads);

    @Insert
    void insertOne(Deadline dead);

    @Delete
    void delete(Deadline dead);
}
