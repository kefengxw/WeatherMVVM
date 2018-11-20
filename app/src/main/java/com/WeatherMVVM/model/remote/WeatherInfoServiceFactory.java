package com.WeatherMVVM.model.remote;

public class WeatherInfoServiceFactory {
    private static volatile WeatherInfoService mInstanceService = null;

    public static synchronized WeatherInfoService getInstanceService() {
        if (null == mInstanceService) {
            mInstanceService = RetrofitClient.createService(WeatherInfoService.class);
        }
        return mInstanceService;
    }

    public static void destroyInstanceService() {
        mInstanceService = null;
    }
}
