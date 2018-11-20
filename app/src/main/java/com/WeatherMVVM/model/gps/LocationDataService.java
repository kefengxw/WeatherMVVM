package com.WeatherMVVM.model.gps;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.WeatherMVVM.model.WeaApplication;
import com.WeatherMVVM.view.HomeActivity;

import static com.WeatherMVVM.model.gps.LocationDataServiceConfigure.REQUEST_CHECK_SETTINGS;

public class LocationDataService {

    private Context mCtx = null;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private MutableLiveData<Location> mResultLocation = new MutableLiveData<>();

    LocationDataService() {
        init();
        createLocationDataRequest();
        buildLocationDataSettingsRequest();
        createLocationDataCallback();
    }

    private void init() {
        mCtx = WeaApplication.getInstanceApp();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mCtx);
        mSettingsClient = LocationServices.getSettingsClient(mCtx);
    }

    private void createLocationDataRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LocationDataServiceConfigure.UPDATE_INTERVAL_IN_SECONDS);
        mLocationRequest.setFastestInterval(LocationDataServiceConfigure.FASTEST_UPDATE_INTERVAL_IN_SECONDS);
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationDataSettingsRequest() {
        LocationSettingsRequest.Builder sqBuilder = new LocationSettingsRequest.Builder();
        sqBuilder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = sqBuilder.build();
    }

    private void createLocationDataCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mResultLocation.postValue(locationResult.getLastLocation());
            }
        };
    }

    public MutableLiveData<Location> getCurrentLocation() {
        return mResultLocation;
    }

    public void startLocationServiceUpdate() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(mOnSuccessListener)
                .addOnFailureListener(mOnFailureListener);
    }

    public void stopLocationServiceUpdate() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                    .addOnCompleteListener(mOnCompleteListener);
        }
    }

    private OnSuccessListener mOnSuccessListener = new OnSuccessListener<LocationSettingsResponse>() {
        @Override
        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            addLocationCallbackListener();
        }
    };

    private OnFailureListener mOnFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            int statusCode = ((ApiException) e).getStatusCode();
            if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                ResolvableApiException it = (ResolvableApiException) e;
                try {
                    if (HomeActivity.getInstanceHa() != null) {
                        it.startResolutionForResult(HomeActivity.getInstanceHa(), REQUEST_CHECK_SETTINGS);//this is phone GPS setting
                    }
                } catch (IntentSender.SendIntentException e1) {
                    e1.printStackTrace();//and log here
                }
            }
        }
    };

    private OnCompleteListener mOnCompleteListener = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            //nothing to do here
        }
    };

    private void addLocationCallbackListener() {
        if (!UtilCheckPermission.checkPermission(mCtx)) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
}
