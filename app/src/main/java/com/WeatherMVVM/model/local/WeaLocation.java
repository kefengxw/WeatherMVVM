package com.WeatherMVVM.model.local;

import android.arch.persistence.room.ColumnInfo;

public class WeaLocation {
    //In Dao interface, for query return value(WeaLocation)
    @ColumnInfo(name = "latitude")
    public double latitude;
    @ColumnInfo(name = "longitude")
    public double longitude;

    public WeaLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
