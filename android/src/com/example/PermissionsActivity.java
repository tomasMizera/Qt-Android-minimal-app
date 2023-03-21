package com.example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PermissionsActivity extends Activity {
    private static final String TAG = "Permissions activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d( TAG, "permissions created!");

        // check permissions
        List<String> permissionsList = new ArrayList<String>();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Requesting permissions - FINE LOCATION!");

            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (permissionsList.size() > 0) {
            String[] permissions = new String[permissionsList.size()];
            permissionsList.toArray(permissions);

            requestPermissions( permissions, 1000 );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1000 ) {
            Log.d( TAG, "permissions finished!");
        }
    }
}