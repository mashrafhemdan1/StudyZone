package com.example.mystudyzone;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = {Lecture.class, Period.class, Deadline.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract LectureDao lectureDao();
    public abstract DeadlineDao deadlineDao();
    public abstract PeriodDao periodDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
