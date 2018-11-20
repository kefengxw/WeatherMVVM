package com.WeatherMVVM.model.local;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

public class FavorLocRepository {
    //this is just for expansion from architecture aspect
    private FavorLocationDao mFavorLocationDao = null;
    private LiveData<WeaLocation> mFavorLocation = null;

    public FavorLocRepository(FavorLocationDao local) {
        this.mFavorLocationDao = local;
        this.mFavorLocation = mFavorLocationDao.getFavorLocationLd();
    }

    public void insert(WeaLocation it) {
        if (it != null) {
            FavorLocationBean fLo = new FavorLocationBean(it);
            new InsertLocationAsyncTask(mFavorLocationDao).execute(fLo);
        }
    }

    public LiveData<WeaLocation> getFavorLocationLd() {
        return mFavorLocation;
    }

    private static class InsertLocationAsyncTask extends AsyncTask<FavorLocationBean, Void, Void> {
        private FavorLocationDao favorLocationDao;

        public InsertLocationAsyncTask(FavorLocationDao it) {
            this.favorLocationDao = it;
        }

        @Override
        protected Void doInBackground(FavorLocationBean... fLos) {
            favorLocationDao.insert(fLos[0]);
            return null;
        }
    }
}
