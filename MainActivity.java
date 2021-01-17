package com.example.lock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ActivityManager;
import android.app.TimePickerDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private MaterialButton btn1;
    private MaterialButton btn2;
    private CountDownTimer CN;
    private long timeleft;
    private String timel;
    private boolean isRunning;
    private TextView textV;


    public static final int RESULT_ENABLE = 11;
    private DevicePolicyManager MGR;
    private ActivityManager ACT;
    private ComponentName COMPP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=findViewById(R.id.duration);
        btn2=findViewById(R.id.startbtn);
        textV = (TextView) findViewById(R.id.textview);


        MGR = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ACT = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        COMPP = new ComponentName(this, sudo.class);

//        @Override
//        protected void onResume() {
//            super.onResume();
//            boolean isActive = devicePolicyManager.isAdminActive(COMPP);
//
//        }



        //btn1 handles fragment of picking time
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timefrag= new TimeF();
                timefrag.show(getSupportFragmentManager(),"timefragment");
            }
        });


        //btn2 lock system, not finished
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn2) {
                    boolean active = MGR.isAdminActive(COMPP);

                    if (active) {
                        MGR.lockNow();
                    }
                    //startcount(); commented for now to test smth
                }
                //DONT TEST RN, LOCK,UNLOCK ASK
                btn2.setText("locked");
            }
        });
            //update countdown on the system
            updatecd();
    }

    private void startcount() {

        CN = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                 timeleft= millisUntilFinished;
                 updatecd();
            }

            @Override
            public void onFinish() {
                isRunning=false;
            }
        }.start();
        isRunning=true;

    }

    private void updatecd() {

        int min = (int) timeleft/1000/60;
        int seconds = (int) timeleft/1000%60;
        int hours = (int) timeleft/1000;
        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,min,seconds);
        textV.setText(timeFormat);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar x = Calendar.getInstance();
        x.set(Calendar.HOUR_OF_DAY, hourOfDay);
        x.set(Calendar.MINUTE, minute);
        x.set(Calendar.SECOND, 0);
        String timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(x.getTime());
        textV.setText(timeFormat);

       // timel= timeFormat;//DateFormat.getTimeInstance(DateFormat.HOUR1_FIELD).format((x.getTime()));
        if(timeFormat.substring(2, 3).equals(":"))
        {
            //current time and extracted time difference in milliseconds should be pushed to lock system flag
            long val = hourOfDay*60*60*1000+minute*60*1000;
            Log.d("timehourminuteinms", String.valueOf(val));
            Log.d("x.gettime", String.valueOf(x.getTime()));


        }else if(timeFormat.substring(1, 2).equals(":"))
        {
            long val = hourOfDay*60*60*1000+minute*60*1000;
            Log.d("timehourminuteinms", String.valueOf(val));
            Log.d("x.gettime", String.valueOf(x.getTime()));

        }



    }
}