package com.cranberryanalytics.techlabassignment.main;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainLocationService extends Service implements LocationListener {
    LocationManager locationManager;
    public static final int MIN_DURATION = 5000;
    public static final int MIN_DISTANCE = 10;
    LocalBroadcastManager localBroadcastManager;
    static final String LOCATION_ACTION = "com.subodh_meshram.location_updated";
    static final String LAT = "latitude";
    static final String LONG = "longitude";

    private static final String TAG = "MyLocationService";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        boolean isGps = false;
        boolean isNetwork = false;

        isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location networkLocation = null;
        Location gpsLocation = null;
        Location location = null;

        if (isGps) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (isNetwork) {
            networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }
        if (gpsLocation != null && networkLocation != null) {
            if (gpsLocation.getAccuracy() > networkLocation.getAccuracy()) {
                location = networkLocation;
            } else
                location = gpsLocation;
        } else {
            if (gpsLocation != null) {
                location = gpsLocation;
            } else if (networkLocation != null) {
                location = networkLocation;
            }
        }

        if (location != null) {
            Log.d(TAG, "onStartCommand: Last known Location " + location.getLatitude() + "," + location.getLongitude());
            notifyLocationChanged(location);
        } else {
            Log.d(TAG, "onStartCommand: last known location not found");
        }
        if (isGps) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_DURATION, MIN_DISTANCE, this);
        } else if (isNetwork) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_DURATION, MIN_DISTANCE, this);
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: location service");
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        notifyLocationChanged(location);
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void notifyLocationChanged(Location location) {
        Log.d(TAG, "notifyLocationChanged: location : " + location.getLatitude() + "," + location.getLongitude());
        Intent locationIntent = new Intent(LOCATION_ACTION);
        locationIntent.putExtra(LAT, location.getLatitude());
        locationIntent.putExtra(LONG, location.getLongitude());
        localBroadcastManager.sendBroadcast(locationIntent);
    }
}
