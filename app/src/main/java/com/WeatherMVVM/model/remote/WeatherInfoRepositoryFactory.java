package com.WeatherMVVM.model.remote;

import com.WeatherMVVM.model.WeaApplication;

public class WeatherInfoRepositoryFactory {

    private static volatile WeatherInfoRepository mInstanceRepoSer = null;

    public static synchronized WeatherInfoRepository getInstanceRepoSer() {
        if (null == mInstanceRepoSer) {
            mInstanceRepoSer = new WeatherInfoRepository(WeaApplication.getInstanceService());
        }
        return mInstanceRepoSer;
    }

    public static void destroyInstanceRepoSer() {
        mInstanceRepoSer = null;
    }
}
