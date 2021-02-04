package com.example.mystudyzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooserBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_basic);
        findViewById(R.id.BasicSessionsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooserBasicActivity.this, BasicActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.BasicDeadlineBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooserBasicActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.BasicSubjectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooserBasicActivity.this, Monthly_view.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.BasicGradeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooserBasicActivity.this, AssignGrade.class);
                startActivity(intent);
            }
        });
    }
}