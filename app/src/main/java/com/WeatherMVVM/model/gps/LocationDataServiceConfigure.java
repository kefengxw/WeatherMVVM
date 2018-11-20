package com.WeatherMVVM.model.gps;

public class LocationDataServiceConfigure {

    final static long UPDATE_INTERVAL_IN_SECONDS = (2 * 1000);
    final static long FASTEST_UPDATE_INTERVAL_IN_SECONDS = (UPDATE_INTERVAL_IN_SECONDS / 2);
    final static int REQUEST_CHECK_SETTINGS = 0x01;
    public final static int REQUEST_LOCATION_PERMISSION = 0x02;
}
