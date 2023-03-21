package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BroadcastMiddleware {

    private static native void sendToQt(String message);

    private static final String TAG = "BroadcastMiddleware";
    public static final String BROADCAST_CUSTOM_ACTION = "com.example.location.broadcast";

    public void registerServiceBroadcastReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_CUSTOM_ACTION);
        context.registerReceiver(serviceMessageReceiver, intentFilter);
        Log.i(TAG, "Registered broadcast receiver");
    }

    private BroadcastReceiver serviceMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "In OnReceive()");
            if (BROADCAST_CUSTOM_ACTION.equals(intent.getAction())) {
                String message = intent.getStringExtra("message");
                sendToQt(message);
                Log.i(TAG, "Service sent back message to C++: " + message);
            }
        }
    };
}