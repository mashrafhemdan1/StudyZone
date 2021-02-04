package com.example.mystudyzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_add);

        findViewById(R.id.Lecture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAdd.this, ChooseAdd.class);
                intent.putExtra("Saving", "Lecture");
                startActivity(intent);
            }
        });

        findViewById(R.id.Deadline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAdd.this, AsynchronousActivity.class);
                intent.putExtra("Saving", "Deadline");
                startActivity(intent);
            }
        });
        findViewById(R.id.event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAdd.this, Monthly_view.class);
                intent.putExtra("Saving", "event");
                startActivity(intent);
            }
        });
    }
}