package com.example.mystudyzone;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class AssignGrade extends AppCompatActivity {

    AppDatabase db = AppDatabase.getInstance(this);
    DeadlineDao deadlineDao = db.deadlineDao();
    private LinearLayout showgrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_grade);
        EditText title = findViewById(R.id.events_disc);
        Button Add = findViewById(R.id.AddGrade);
        Button Delete = findViewById(R.id.DeleteGrade);
        showgrade = findViewById(R.id.hiddengrade);
        EditText numerator = findViewById(R.id.cheg);
        EditText denimenator = findViewById(R.id.teg);
        Button Save = findViewById(R.id.SaveGrade);
        Context context;







        Spinner spinner_subject = (Spinner) findViewById(R.id.task_Subject);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_subject = ArrayAdapter.createFromResource(this, R.array.task_subject, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_subject.setAdapter(adapter_subject);

        Spinner spinner_catagory = (Spinner) findViewById(R.id.task_Category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_catagory = ArrayAdapter.createFromResource(this, R.array.task_catagory, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_catagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_catagory.setAdapter(adapter_catagory);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showgrade.setVisibility(View.VISIBLE);


            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showgrade.setVisibility(View.INVISIBLE);
                AddGrade(title.getText().toString(),numerator.getText().toString(),denimenator.getText().toString(), spinner_subject.getSelectedItem().toString(),spinner_catagory.getSelectedItem().toString());


            }
        });



        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteGrade(title.getText().toString(), spinner_subject.getSelectedItem().toString(), spinner_catagory.getSelectedItem().toString());
            }
        });

    }

    private void DeleteGrade(String mtitle, String msubject, String mcategory) {
        deadlineDao.updateGrade("0", msubject, mcategory, mtitle);
    }

    private void AddGrade(String mtitle,String yourgrade,String fullgrade, String msubject, String mcategory) {

        float furgrade = Float.parseFloat(yourgrade);
        float fflgrade = Float.parseFloat(fullgrade);
        float ten = 10;
        float weightedgrade = ((furgrade/fflgrade)* ten);
        DecimalFormat df2 = new DecimalFormat("#.##");

        int choose = deadlineDao.updateGrade(String.valueOf(df2.format(weightedgrade)), msubject, mcategory, mtitle);

        if (choose == 1)
        {
            Toast.makeText(AssignGrade.this, "Grade inserted", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(AssignGrade.this, "This Title or this subject doesn't exist please add them first", Toast.LENGTH_SHORT).show();

        }



    }
}