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

    @Query("SELECT * FROM Deadline WHERE date > (:curDate) AND isStudied = 0 ORDER BY date, time")
    List<Deadline> getDeadlineForWeek(String curDate);
    @Query("SELECT grade FROM Deadline WHERE subject = (:subject) AND category = (:category) ")
    String [] getgrades(String subject, String category);

    @Insert
    void insertAll(Deadline... deads);

    @Insert
    void insertOne(Deadline dead);

    @Delete
    void delete(Deadline dead);

    @Query("SELECT count(*) FROM Deadline WHERE date < (:curDate) AND isFinised = 0")
    int getCountFinished(String curDate);

    @Query("SELECT count(*) FROM Deadline WHERE date < (:curDate)")
    int getCountAll(String curDate);
    @Query("UPDATE Deadline SET grade = :grade WHERE subject = :subject AND category = :category AND name = :name")
    int updateGrade(String grade , String subject, String category, String name);

}