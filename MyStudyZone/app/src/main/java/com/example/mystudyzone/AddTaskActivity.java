package com.example.mystudyzone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private int mDay, mMonth, MYear;
    private int mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        // Spinner for task catagory
        Spinner spinner_catagory = (Spinner) findViewById(R.id.task_catagory);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_catagory = ArrayAdapter.createFromResource(this,
                R.array.task_catagory, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_catagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_catagory.setAdapter(adapter_catagory);

        //Spinner for task subject
        Spinner spinner_subject = (Spinner) findViewById(R.id.task_Subject);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_subject = ArrayAdapter.createFromResource(this,
                R.array.task_subject, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_subject.setAdapter(adapter_subject);

        //Date Picker
        TextView data_picker = (TextView) findViewById(R.id.date_picker);
        data_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //Timer Picker
        TextView timePicker = (TextView) findViewById(R.id.time_picker);
        timePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                mHour = hourOfDay;
                                mMinute = minute;
                                Calendar cal = Calendar.getInstance();
                                cal.set(0, 0, 0, mHour, mMinute);
                                timePicker.setText(DateFormat.getTimeInstance().format(cal.getTime()));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(mHour, mMinute);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, dayOfMonth);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        String currentDateString = DateFormat.getDateInstance().format(cal.getTime());
        TextView datePicker = (TextView) findViewById(R.id.date_picker);
        datePicker.setText(currentDateString);
    }
}
