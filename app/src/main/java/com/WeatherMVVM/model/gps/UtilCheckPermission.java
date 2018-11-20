package com.WeatherMVVM.model.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.WeatherMVVM.view.HomeActivity;

import static com.WeatherMVVM.model.gps.LocationDataServiceConfigure.REQUEST_LOCATION_PERMISSION;

public class UtilCheckPermission {

    public static boolean checkPermission(Context mCtx) {

        boolean it = true;

        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            HomeActivity.getInstanceHa().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            it = false;
        }
        return it;
    }
}
