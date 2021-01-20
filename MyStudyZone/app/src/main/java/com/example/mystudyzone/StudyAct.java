package com.example.mystudyzone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudyAct extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton startbtn;
    private MaterialButton stopbtn;
    private long timeleft;
    private TimePicker picker;
    public static final int REQUEST_CODE=0;
    private DevicePolicyManager DM;
    private ActivityManager AM;
    private ComponentName comp;
    private boolean isRunning;
    private TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        DM= (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        AM= (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        comp= new ComponentName(this,superuser.class);
        startbtn=findViewById(R.id.startbtn);
        stopbtn=findViewById(R.id.stopbtn);

        picker = findViewById(R.id.timepicker1);
        int hours = picker.getHour();
        int minutes = picker.getMinute();
        textView=findViewById(R.id.textview);
        String value= Integer.toString(hours)+":"+Integer.toString(minutes);
        textView.setText(value);
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String value2= Integer.toString(hourOfDay)+":"+Integer.toString(minute);
                textView.setText(value2);
                long val = hourOfDay*60*60*1000+minute*60*1000;
                timeleft= val;
            }
        });



        if (!DM.isAdminActive(comp)) {
            // try to become active
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,comp);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
            startActivityForResult(intent, REQUEST_CODE);
        }

        startbtn.setOnClickListener(this);

        stopbtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning= DM.isAdminActive(comp);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        if(v==startbtn)
        {
            boolean running= DM.isAdminActive(comp);
            if(running)
            {
                long o=0;
                final int[] yourVariable = new int[1];
                yourVariable[0] = 0;
                String currentime = getCurrentTime();

                String HH= currentime.substring(0,2);
                long valHH=Long.parseLong(HH);
                valHH=valHH*1000*60*60;
                String MM= currentime.substring(3,5);
                long valMM=Long.parseLong(MM);
                valMM=valMM*1000*60;
                timeleft=timeleft-valHH-valMM;
                CountDownTimer updateVariableTimer = new CountDownTimer(timeleft, 1000)
                {
                    @Override
                    public void onTick(long l) {
                        yourVariable[0] += 1;
                        DM.lockNow();
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();




            }else
            {
                Toast.makeText(this,"no admin priviliges",Toast.LENGTH_SHORT).show();
            }
        }else if(v==stopbtn)
        {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,comp);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"NEED PRIVILIGES");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_CODE == requestCode)
        {
            if(requestCode == Activity.RESULT_OK)
            {
                // done with activate to Device Admin
            }
            else
            {
                //cancel
            }
        }
    }


    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}