package com.example.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class SampleService extends Service {
    private static final String TAG = SampleService.class.getSimpleName();
    private final String CHANNEL_ID = "SampleServiceChannel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return START_NOT_STICKY; // do not auto-restart on crash or out of memory kill
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private Notification.Builder createNotificationBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(this, CHANNEL_ID);
        } else {
            return new Notification.Builder(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void registerDefaultNotificationChannel() {
        final String channelName = "SampleServiceChannel alerts";
        NotificationChannel channel =
                new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW);
        channel.enableVibration(false);
        channel.enableLights(false);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerDefaultNotificationChannel();
        }

        Notification.Builder builder =
                createNotificationBuilder()
                        .setContentTitle(
                                "Running Sample Service")
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setTicker("SampleServiceChannel enabled");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder =
                    builder
                            // No sound, vibration, or lights.
                            .setDefaults(0)
                            // This should be consistent with NotificationChannel importance.
                            .setPriority(Notification.PRIORITY_LOW);
        }
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }
}
