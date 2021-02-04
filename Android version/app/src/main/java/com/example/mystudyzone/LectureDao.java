package com.example.mystudyzone;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LectureDao {
    @Query("SELECT * FROM Lecture")
    List<Lecture> getAll();

    @Query("SELECT * FROM Lecture WHERE day = (:day)")
    List<Lecture> getOnDay(String day);

    @Insert
    void insertAll(Lecture... lecs);

    @Insert
    void insertOne(Lecture lec);

    @Delete
    void delete(Lecture lec);
}
