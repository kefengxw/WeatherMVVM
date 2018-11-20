package com.WeatherMVVM.model.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface FavorLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavorLocationBean it);

    //just because return WeaLocation, while not FavorLocationBean, see @ColumnInfo in WeaLocation
    @Query("SELECT latitude, longitude FROM favor_location_table LIMIT 1")
    LiveData<WeaLocation> getFavorLocationLd();
}
