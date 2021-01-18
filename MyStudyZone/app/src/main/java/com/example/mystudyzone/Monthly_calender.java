package com.example.mystudyzone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Monthly_calender extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridview;
    private static final int MAX_CALENDER_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    List<Date> dates = new ArrayList<>();
    List<Event> eventList = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);



    public Monthly_calender(Context context) {
        super(context);
    }

    public Monthly_calender(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
