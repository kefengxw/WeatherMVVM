package com.WeatherMVVM.model.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.WeatherMVVM.model.local.WeaLocation;
import com.WeatherMVVM.view.WeatherInfo;
import com.WeatherMVVM.model.data.Weather;

public class WeatherInfoRepository {
    //this is just for expansion from architecture aspect
    private LiveData<Resource<WeatherInfo>> mResultWeatherInfo = null;
    private WeatherInfoService mWeatherInfoService = null;

    public WeatherInfoRepository(WeatherInfoService remote) {
        this.mWeatherInfoService = remote;
    }

    public LiveData<Resource<WeatherInfo>> getRemoteWeatherInfo(WeaLocation it) {
        NetworkResource<WeatherInfo, Weather> nBResource = new NetworkResource<WeatherInfo, Weather>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Weather>> createNetworkCall() {
                LiveData<ApiResponse<Weather>> call = null;
                if (it != null) {
                    call = mWeatherInfoService.getRemoteWeatherInfo(it.latitude, it.longitude);
                }
                return call;
            }

            @Override
            protected void convertRequestToResult(Weather it) {
                // if Error happens, it == null
                if (it == null) {
                    mResultTemp = null;//error happens
                } else {
                    mResultTemp = new WeatherInfo(it);
                }
            }
        };
        mResultWeatherInfo = nBResource.getAsLiveData();

        return mResultWeatherInfo;
    }
}
