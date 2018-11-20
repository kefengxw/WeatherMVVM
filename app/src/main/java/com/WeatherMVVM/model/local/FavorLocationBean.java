package com.WeatherMVVM.model.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favor_location_table",
        indices = {
                @Index(value = {"id"}, unique = true),
                /*@Index(value = {"latitude", "longitude"}, unique = true)*/}
)
//@Entity(primaryKeys = {"latitude", "longitude"}) also fine
public class FavorLocationBean {
    @PrimaryKey(autoGenerate = false)
    public int id;

    @ColumnInfo(name = "latitude")
    public double latitude;
    @ColumnInfo(name = "longitude")
    public double longitude;

    //@Ignore public int/string/double item;
    //@Embedded for Class item

    public FavorLocationBean() {
    }

    public FavorLocationBean(WeaLocation it) {
        this.id = 0;//Only one item, can be expansion later
        this.latitude = it.latitude;
        this.longitude = it.longitude;
    }
}
