package com.WeatherMVVM.model.gps;

public class LocationDataServiceFactory {

    private static LocationDataService mInstanceLds = null;

    public static LocationDataService getInstanceLds() {
        if (mInstanceLds == null) {
            mInstanceLds = new LocationDataService();
        }
        return mInstanceLds;
    }

    public static void destroyInstanceLds() {
        mInstanceLds = null;
    }
}
