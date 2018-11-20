package com.WeatherMVVM.model;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.WeatherMVVM.model.gps.LocationDataService;
import com.WeatherMVVM.model.gps.LocationDataServiceFactory;
import com.WeatherMVVM.model.local.FavorLocRepository;
import com.WeatherMVVM.model.local.FavorLocRepositoryFactory;
import com.WeatherMVVM.model.local.FavorLocationDao;
import com.WeatherMVVM.model.local.FavorLocationDb;
import com.WeatherMVVM.model.remote.WeatherInfoRepository;
import com.WeatherMVVM.model.remote.WeatherInfoRepositoryFactory;
import com.WeatherMVVM.model.remote.WeatherInfoService;
import com.WeatherMVVM.model.remote.WeatherInfoServiceFactory;

public class WeaApplication extends Application {

    private static WeaApplication mInstanceApp = null;
    private static FavorLocRepository mInstanceReposDb = null;
    private static WeatherInfoRepository mInstanceReposSer = null;
    private static FavorLocationDao mInstanceDao = null;
    private static WeatherInfoService mInstanceService = null;
    private static LocationDataService mInstanceLds = null;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void init() {//can be replaced by dagger2, but dagger2 will reduce performance
        //application
        setInstanceApp();
        //network(remote)
        mInstanceService = WeatherInfoServiceFactory.getInstanceService();
        //database(local)
        mInstanceDao = FavorLocationDb.getInstanceDb(mInstanceApp).favorLocationDao();
        //Repository
        mInstanceReposSer = WeatherInfoRepositoryFactory.getInstanceRepoSer();
        mInstanceReposDb = FavorLocRepositoryFactory.getInstanceRepoDb();
        //Location
        mInstanceLds = LocationDataServiceFactory.getInstanceLds();
        //other, debug, log,
        //Timber here;
    }

    public void setInstanceApp() {
        this.mInstanceApp = this;
    }

    public static WeaApplication getInstanceApp() {
        return (WeaApplication) mInstanceApp;
    }

    public static WeatherInfoService getInstanceService() {
        return mInstanceService;
    }

    public static FavorLocationDao getInstanceDao() {
        return mInstanceDao;
    }

    public static WeatherInfoRepository getInstanceReposSer() {
        return mInstanceReposSer;
    }

    public static FavorLocRepository getInstanceReposDb() {
        return mInstanceReposDb;
    }

    public static LocationDataService getmInstanceLds() {
        return mInstanceLds;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //log here, in case too much Food info
    }
}
