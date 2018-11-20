package com.WeatherMVVM.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.WeatherMVVM.R;
import com.WeatherMVVM.model.local.WeaLocation;
import com.WeatherMVVM.model.remote.Resource;

public class FavorWeaFragment extends BaseWeaFragment {

    public static FavorWeaFragment newInstance(/*String param1, String param2*/) {
        FavorWeaFragment fragment = new FavorWeaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int ProvidedFragmentLayoutId() {
        return R.layout.wea_frag_lo;
    }

    @Override
    protected void initObserve() {
        mViewModel.getLiveDataFavorLocation().observe(getViewLifecycleOwner(), observerFavorLocation);
        mViewModel.getLiveDataFavorWeatherInfo().observe(getViewLifecycleOwner(), observerFavorWeatherInfo);
    }

    private Observer<WeaLocation> observerFavorLocation = new Observer<WeaLocation>() {
        @Override
        public void onChanged(@Nullable WeaLocation weaLocation) {
        }
    };

    private Observer<Resource<WeatherInfo>> observerFavorWeatherInfo = new Observer<Resource<WeatherInfo>>() {
        @Override
        public void onChanged(@Nullable Resource<WeatherInfo> it) {
            //Toast.makeText(mCtx, "Favor WeatherInfo onChanged", Toast.LENGTH_SHORT).show();
            if (null != it.mData) {
                updateData(it.mData);
            }
            updateStatus(it.mStatus);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //fix Favor location weather info update problem.
        mViewModel.updateFavorLocation();
    }
}
