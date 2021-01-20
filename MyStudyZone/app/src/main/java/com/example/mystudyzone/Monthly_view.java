package com.example.mystudyzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Monthly_view extends AppCompatActivity {
    Monthly_calender monthly_calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_view);

        monthly_calender = (Monthly_calender) findViewById(R.id.monthly_calendar_view);
    }
}