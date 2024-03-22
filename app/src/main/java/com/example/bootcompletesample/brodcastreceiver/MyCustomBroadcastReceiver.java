package com.example.bootcompletesample.brodcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.service.SampleService;

public class MyCustomBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action != null) {
            if (action.equals(Intent.ACTION_BOOT_COMPLETED) ) {
                Intent intent1 = new Intent(context.getApplicationContext(), SampleService.class);
                context.startService(intent1);
            }
        }
    }
}
