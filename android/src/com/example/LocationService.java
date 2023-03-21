package com.example;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.os.IBinder;
import android.app.PendingIntent;
import android.widget.Toast;

import com.example.BroadcastMiddleware;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service implements LocationListener {
    public static final String CHANNEL_ID = "LocationServiceChannel";
    private static final String TAG = "LocationService";

//    private final Context mContext;

    boolean checkGPS = false;

    boolean checkNetwork = false;

    boolean canGetLocation = false;

    Location loc;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 1000; //ms

    protected LocationManager locationManager;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

        Intent notificationIntent = new Intent(this, LocationService.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification not = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_merginmaps_launcher_background)
                    .setTicker(getText(R.string.ticker_text))
                    .build();

            Log.d("MM", "About to start the foreground service!");

            startForeground(1010, not);

            Log.d("MM", "Started the foreground service!");

            locationManager = (LocationManager) getApplication().getSystemService(LOCATION_SERVICE);
            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!checkGPS) {
                Log.d(TAG, "GPS is not available!");
                stopSelf();
                stopForeground(true);
            } else {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Log.d(TAG, "Missing permissions!");
                    stopSelf();
                    stopForeground(true);
                }
                else {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d("MM", "Started to listen to position updates!");

                    Log.d("MM", String.format("Last location %s", locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).toString() ) );

                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(LocationService.this.getApplicationContext(),"My Awesome service toast...",Toast.LENGTH_SHORT).show();
                        }
                    });

                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Service executed dummy code!");

                            Intent sendToUiIntent = new Intent();
                            sendToUiIntent.setAction(BroadcastMiddleware.BROADCAST_CUSTOM_ACTION);
                            sendToUiIntent.putExtra("message", "started in service!");

                            Log.i(TAG, "Service sending broadcast");
                            sendBroadcast(sendToUiIntent);
                        }
                    }, 0, 2000);
                }
            }
        } else {
            Log.d("MM", "Sorry, tracking is not supported on your Android version");
            stopSelf();
        }

        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MM", String.format("Location updated %s", location.toString() ) );

        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(LocationService.this.getApplicationContext(),String.format("Location: %s", location.toString()),Toast.LENGTH_SHORT).show();
            }
        });

        Intent sendToUiIntent = new Intent();
        sendToUiIntent.setAction(BroadcastMiddleware.BROADCAST_CUSTOM_ACTION);
        sendToUiIntent.putExtra("message", "simple_string");

        Log.i(TAG, "Service sending broadcast");
        sendBroadcast(sendToUiIntent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);

        Log.d( TAG, String.format("Status has changed to %s", status));
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d( TAG, "Provider enabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d( TAG, "Provider disabled");
    }
}