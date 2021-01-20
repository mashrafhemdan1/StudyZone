package com.example.mystudyzone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class StudyAct extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton startbtn;
    private MaterialButton stopbtn;
    public static final int REQUEST_CODE=0;
    private DevicePolicyManager DM;
    private ActivityManager AM;
    private ComponentName comp;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        DM= (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        AM= (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        comp= new ComponentName(this,superuser.class);
        startbtn=findViewById(R.id.startbtn);
        stopbtn=findViewById(R.id.stopbtn);

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

    @Override
    public void onClick(View v) {

        if(v==startbtn)
        {
            boolean running= DM.isAdminActive(comp);
            if(running)
            {
                DM.setMaximumTimeToLock(comp,30000);
                DM.lockNow();

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


}