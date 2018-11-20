package com.WeatherMVVM.model.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavorLocationBean.class}, version = 1, exportSchema = false)
//@TypeConverters
public abstract class FavorLocationDb extends RoomDatabase {

    private static volatile FavorLocationDb mInstanceDb = null;

    public abstract FavorLocationDao favorLocationDao();

    public static synchronized FavorLocationDb getInstanceDb(Context ctx) {
        if (null == mInstanceDb) {
            mInstanceDb = Room.databaseBuilder(ctx.getApplicationContext(),
                    FavorLocationDb.class, "favor_location_database")
                    //.allowMainThreadQueries()
                    .build();
        }
        return mInstanceDb;
    }

    public static void destroyInstanceDb() {
        mInstanceDb = null;
    }
}