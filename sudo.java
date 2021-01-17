package com.example.lock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

public class sudo extends DeviceAdminReceiver {
    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
            //might out a toasst
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {

    }
}
