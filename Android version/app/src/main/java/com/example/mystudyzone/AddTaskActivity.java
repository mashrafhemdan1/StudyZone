package com.example.mystudyzone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private int mDay, mMonth, MYear;
    private int mHour, mMinute;
    Spinner spinner_catagory;
    Spinner spinner_subject;
    TextView data_picker;
    TextView timePicker;
    EditText taskname;
    TextView desc;
    Button button;
    private Deadline d = new Deadline();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        //EDIT BY SHOURBAGY
        AppDatabase db = AppDatabase.getInstance(this);
        DeadlineDao deadlineDao = db.deadlineDao();
        //END OF EDIT

        // Spinner for task catagory
        spinner_catagory = (Spinner) findViewById(R.id.task_catagory);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_catagory = ArrayAdapter.createFromResource(this,
                R.array.task_catagory, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_catagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_catagory.setAdapter(adapter_catagory);

        //Spinner for task subject
        spinner_subject = (Spinner) findViewById(R.id.task_Subject);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_subject = ArrayAdapter.createFromResource(this,
                R.array.task_subject, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_subject.setAdapter(adapter_subject);

        //Date Picker
        data_picker = (TextView) findViewById(R.id.date_picker);
        data_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //Timer Picker
        timePicker = (TextView) findViewById(R.id.time_picker);
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
                                SimpleDateFormat SDFormat = new SimpleDateFormat("HH-mm");
                                timePicker.setText(SDFormat.format(cal.getTime()));
                                d.time = SDFormat.format(cal.getTime()); //to be saved later in the database
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(mHour, mMinute);
                timePickerDialog.show();
            }
        });

        //task Name
        taskname = (EditText) findViewById(R.id.taskName);
        //task Description
        desc = (EditText) findViewById(R.id.taskDesc);
        //button (save)
        button = (Button) findViewById(R.id.task_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.category = spinner_catagory.getSelectedItem().toString();
                d.subject = spinner_subject.getSelectedItem().toString();
                d.name = taskname.getText().toString();
                d.desc = desc.getText().toString();
                d.grade = "";
                d.isFinised = true;
                deadlineDao.insertOne(d);
                Intent i = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(i);
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
        SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy-MM-dd");
        datePicker.setText(SDFormat.format(cal.getTime()));
        d.date = SDFormat.format(cal.getTime());
    }
}
