package com.example.mystudyzone;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class Monthly_calender extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridview;
    private static final int MAX_CALENDER_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    MyGridAdapter myGridAdapter;
    List<Date> dates = new ArrayList<>();
    List<Event> eventList = new ArrayList<>();
    AlertDialog alertDialog;






    public Monthly_calender(Context context) {
        super(context);
    }

    public Monthly_calender(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeLayout();
        SetUpCalendar();
        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();
            }
        });
        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout, null);
                EditText title = addView.findViewById(R.id.events_id);
                EditText Description = addView.findViewById(R.id.events_disc);
                EditText period = addView.findViewById(R.id.periodofTime);
                TextView EventTime = addView.findViewById(R.id.eventtime);
                TextView category = addView.findViewById(R.id.category_text);
                TextView subject = addView.findViewById(R.id.subject_text);
                ImageView SetTime = addView.findViewById(R.id.seteventtime);
                Button AddEvent = addView.findViewById(R.id.addevent);




                // Spinner for task catagory
                Spinner spinner_catagory = (Spinner) addView.findViewById(R.id.task_Category);
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter_catagory = ArrayAdapter.createFromResource(context, R.array.task_catagory, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter_catagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner_catagory.setAdapter(adapter_catagory);

                //Spinner for task subject
                Spinner spinner_subject = (Spinner) addView.findViewById(R.id.task_Subject);
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter_subject = ArrayAdapter.createFromResource(context, R.array.task_subject, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner_subject.setAdapter(adapter_subject);

                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Calendar c = Calendar.getInstance();
                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        c.set(Calendar.MINUTE, minute);
                                        c.setTimeZone(TimeZone.getDefault());
                                        SimpleDateFormat hformat = new SimpleDateFormat("HH-mm", Locale.ENGLISH);
                                        String event_Time = hformat.format(c.getTime());
                                        EventTime.setText(event_Time);



                                    }
                                }, hours, minute, true);
                        timePickerDialog.show();
                    }
                });
                String date = dateFormat.format(dates.get(position));
                String month = monthFormat.format(dates.get(position));
                String year = yearFormat.format(dates.get(position));
                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveEvent(title.getText().toString(), EventTime.getText().toString(), date, month, year, Description.getText().toString(), spinner_catagory.getSelectedItem().toString(),spinner_subject.getSelectedItem().toString(), period.getText().toString() );
                        SetUpCalendar();
                        alertDialog.dismiss();

                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }

    public Monthly_calender(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    // this is only to save the event no need to go the distance,
    private void SaveEvent(String name, String startTime, String date, String month, String year, String desc, String category, String subject, String period )
    { // here we only save the information of the event in shared preferences
        Event addedevent = new Event();
        addedevent.setName(name);
        addedevent.setStartTime(startTime);
        addedevent.setEndTime(period);
        addedevent.setDescription(desc);
        addedevent.setCategory(category);
        addedevent.setSubject(subject);

        //saving into the database
        AppDatabase db = AppDatabase.getInstance(getContext());
        PeriodDao periodDao = db.periodDao();
        Period period_obj = new Period();
        period_obj.name = name;
        period_obj.start_date = date;
        period_obj.duration = period;
        period_obj.start_time = startTime;
        period_obj.type = category;
        period_obj.subject = subject;
        periodDao.insertOne(period_obj);
        //done saving to database

        Toast.makeText(context, "event written", Toast.LENGTH_SHORT).show();



    }
    private void SetUpCalendar(){
        String Date = dateFormat.format(calendar.getTime());
        CurrentDate.setText(Date);
        dates.clear();
        Calendar monthCalendar =(Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);

        while (dates.size() < MAX_CALENDER_DAYS)
        {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        myGridAdapter = new MyGridAdapter(context, dates, calendar, eventList);
        gridview.setAdapter(myGridAdapter);

    }

    private void initializeLayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.monthly_calender_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate = view.findViewById(R.id.currentdate);
        gridview = view.findViewById(R.id.gridview);

    }
}
