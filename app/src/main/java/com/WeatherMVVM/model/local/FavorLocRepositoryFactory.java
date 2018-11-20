package com.WeatherMVVM.model.local;

import com.WeatherMVVM.model.WeaApplication;

public class FavorLocRepositoryFactory {

    private static volatile FavorLocRepository mInstanceRepoDb = null;

    public static synchronized FavorLocRepository getInstanceRepoDb() {
        if (null == mInstanceRepoDb) {
            mInstanceRepoDb = new FavorLocRepository(WeaApplication.getInstanceDao());
        }
        return mInstanceRepoDb;
    }

    public static void destroyInstanceRepoDb() {
        mInstanceRepoDb = null;
    }
}
