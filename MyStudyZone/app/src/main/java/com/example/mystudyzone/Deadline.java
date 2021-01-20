package com.example.mystudyzone;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Deadline {
    @NonNull
    public String name = "";

    @NonNull
    public String category = "";

    @NonNull
    public String subject = "";

    @NonNull
    public String date = "";

    @NonNull
    public String time = "";

    @ColumnInfo(name = "description")
    public String desc;

    @ColumnInfo(name = "grade")
    public String grade;

    @ColumnInfo(name = "isStudied")
    public boolean isStudied;

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    @ColumnInfo
    public boolean isFinised = false;

}
