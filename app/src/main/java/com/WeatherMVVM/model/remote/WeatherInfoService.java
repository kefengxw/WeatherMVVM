package com.WeatherMVVM.model.remote;

import android.arch.lifecycle.LiveData;

import com.WeatherMVVM.model.data.Weather;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherInfoService {

    @GET("{latitude},{longitude}")
    LiveData<ApiResponse<Weather>> getRemoteWeatherInfo(
            @Path("latitude") double latitude,
            @Path("longitude") double longitude
    );
}