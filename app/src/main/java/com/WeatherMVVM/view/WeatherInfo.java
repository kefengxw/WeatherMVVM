package com.WeatherMVVM.view;

import com.WeatherMVVM.model.data.Weather;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//this class is for display weather info on the fragment, perfer Java code while not data-binding
public class WeatherInfo {
    public String mLatitude;
    public String mLongitude;
    public String mTimezone;
    public String mTime;
    public String mSummary;
    public String mTemperature;
    public String mHumidity;
    public String mPressure;
    public String mWindSpeed;
    public String mCloudCover;
    public String mVisibility;

    public WeatherInfo(String mLatitude, String mLongitude, String mTimezone,
                       String mTime, String mSummary, String mTemperature,
                       String mHumidity, String mPressure, String mWindSpeed,
                       String mCloudCover, String mVisibility) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mTimezone = mTimezone;
        this.mTime = mTime;
        this.mSummary = mSummary;
        this.mTemperature = mTemperature;
        this.mHumidity = mHumidity;
        this.mPressure = mPressure;
        this.mWindSpeed = mWindSpeed;
        this.mCloudCover = mCloudCover;
        this.mVisibility = mVisibility;
    }

    public WeatherInfo(Weather it) {
        //can be adapt to different UI
        this.mLatitude = String.valueOf(it.latitude);
        this.mLongitude = String.valueOf(it.longitude);
        this.mTimezone = String.valueOf(it.timezone);
        this.mTime = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm:ss, Z").format(new Date((it.currently.time) * 1000));
        this.mSummary = String.valueOf(it.currently.summary);
        this.mTemperature = String.valueOf(new DecimalFormat("#.##").format((it.currently.temperature-32)/1.8))+" ℃";
        this.mHumidity = String.valueOf(it.currently.humidity);
        this.mPressure = String.valueOf(it.currently.pressure);
        this.mWindSpeed = String.valueOf(it.currently.windSpeed);
        this.mCloudCover = String.valueOf(it.currently.cloudCover);
        this.mVisibility = String.valueOf(it.currently.visibility);
    }
}
