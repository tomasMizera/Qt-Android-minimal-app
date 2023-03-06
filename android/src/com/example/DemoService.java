package com.example;

import android.content.Context;
import android.content.Intent;
import android.app.Service;
import android.util.Log;
import android.os.IBinder;
import android.app.PendingIntent;

import java.util.Timer;
import java.util.TimerTask;

public class DemoService extends Service
{
    private static final String TAG = "DemoService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Creating Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroying Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        // Add action ...
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "Service executed dummy code!");
            }
        }, 0, 1000);

        return START_STICKY;
    }
}