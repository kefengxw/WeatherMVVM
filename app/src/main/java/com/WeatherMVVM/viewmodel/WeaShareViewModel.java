package com.WeatherMVVM.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import com.WeatherMVVM.model.WeaApplication;
import com.WeatherMVVM.model.gps.LocationDataService;
import com.WeatherMVVM.model.local.WeaLocation;
import com.WeatherMVVM.model.remote.Resource;
import com.WeatherMVVM.model.remote.WeatherInfoRepository;
import com.WeatherMVVM.view.WeatherInfo;
import com.WeatherMVVM.model.local.FavorLocRepository;

public class WeaShareViewModel extends ViewModel {

    private FavorLocRepository mFavorLocRepository;
    private WeatherInfoRepository mWeaInfoSerRepository;
    private LocationDataService mLocationDataService;

    private LiveData<Resource<WeatherInfo>> mResultCurWeaInfo;
    private MutableLiveData<Location> mCurLocationLD;//from GPS,network,cell
    private LiveData<WeaLocation> mCurLocationInfo;//for current display

    private LiveData<Resource<WeatherInfo>> mResultFavorWeaInfo;
    private LiveData<WeaLocation> mFavorLocationInfo;//for favor display, from Database
    private WeaLocation tempLastFavorLocation = null;

    public WeaShareViewModel() {

        mFavorLocRepository = WeaApplication.getInstanceReposDb();
        mWeaInfoSerRepository = WeaApplication.getInstanceReposSer();
        mLocationDataService = WeaApplication.getmInstanceLds();

        preInit();
        //LiveData<Y> map 		(LiveData<X> source, 	Function<X, Y> func)
        //LiveData<Y> switchMap 	(LiveData<X> trigger, 	Function<X, LiveData<Y>> func)

        mCurLocationLD = mLocationDataService.getCurrentLocation();
        mCurLocationInfo = Transformations.map(mCurLocationLD, it -> {
            return new WeaLocation(it.getLatitude(), it.getLongitude());
        });

        mResultCurWeaInfo = Transformations.switchMap(mCurLocationInfo, it -> {
            return mWeaInfoSerRepository.getRemoteWeatherInfo(it);
        });

        mFavorLocationInfo = mFavorLocRepository.getFavorLocationLd();
        mResultFavorWeaInfo = Transformations.switchMap(mFavorLocationInfo, it -> {
            return mWeaInfoSerRepository.getRemoteWeatherInfo(it);
        });
    }

    private void preInit() {
        //fix bug for database is empty, should take sharedpreferences to fix it, make sure only add one time
        tempLastFavorLocation = new WeaLocation(0.0, 0.0);
        mFavorLocRepository.insert(tempLastFavorLocation);
    }

    public LiveData<WeaLocation> getLiveDataCurrentLocation() {
        return mCurLocationInfo;
    }

    public LiveData<WeaLocation> getLiveDataFavorLocation() {
        return mFavorLocationInfo;
    }

    public LiveData<Resource<WeatherInfo>> getLiveDataCurWeatherInfo() {
        return mResultCurWeaInfo;
    }

    public LiveData<Resource<WeatherInfo>> getLiveDataFavorWeatherInfo() {
        return mResultFavorWeaInfo;
    }

    public void insertFavorLocation() { //command from button
        mFavorLocRepository.insert(mCurLocationInfo.getValue());
        tempLastFavorLocation = mCurLocationInfo.getValue();
    }

    public void updateFavorLocation() {
        //fix Favor location weather info update problem.
        mFavorLocRepository.insert(tempLastFavorLocation);
    }
}
