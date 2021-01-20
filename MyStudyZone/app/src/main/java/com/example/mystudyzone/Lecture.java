package com.example.mystudyzone;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.firebase.ui.auth.data.model.User;

import java.util.List;

@Entity(primaryKeys = {"subject", "day", "start_time"})
public class Lecture {
    @NonNull
    public String subject = "";

    @NonNull
    public String day = "";

    @NonNull
    public String start_time = "";

    @ColumnInfo(name = "duration")
    public String duration;

}
