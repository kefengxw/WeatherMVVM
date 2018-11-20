package com.WeatherMVVM.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.WeatherMVVM.R;
import com.WeatherMVVM.model.WeaApplication;
import com.WeatherMVVM.model.local.WeaLocation;
import com.WeatherMVVM.model.remote.Resource;

import static com.WeatherMVVM.model.remote.Resource.Status.ERROR;
import static com.WeatherMVVM.model.remote.Resource.Status.LOADING;
import static com.WeatherMVVM.model.remote.Resource.Status.SUCCESS;

public class CurrentWeaFragment extends BaseWeaFragment {

    private FloatingActionButton mFabtn = null;
    private ImageView mLoadingBg = null;
    private TextView mLoadFailedTextView = null;
    private Group mLoadingGroup = null;

    public static CurrentWeaFragment newInstance(/*String param1, String param2*/) {
        CurrentWeaFragment fragment = new CurrentWeaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int ProvidedFragmentLayoutId() {
        return R.layout.current_wea_frag_lo;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mFabtn = view.findViewById(R.id.cur_wea_frag_view_fab);
        mLoadingBg = view.findViewById(R.id.loading_background);
        mLoadingGroup = view.findViewById(R.id.group_loading);
        mLoadFailedTextView = view.findViewById(R.id.load_failInfo);

        setDefaultView();
    }

    private void setDefaultView(){
        mLoadFailedTextView.setVisibility(View.GONE);//default
        mFabtn.hide();//default
    }

    @Override
    protected void updateStatus(Resource.Status it) {
        super.updateStatus(it);
        mLoadingBg.setVisibility((it == SUCCESS) ? View.GONE : View.VISIBLE);         //error,load
        mLoadingGroup.setVisibility((it == LOADING) ? View.VISIBLE : View.GONE);       //load
        mLoadFailedTextView.setVisibility((it == ERROR) ? View.VISIBLE : View.GONE); //error
        if (it == SUCCESS) { //success
            mFabtn.show();
        } else {
            mFabtn.hide();
        }//error
    }

    @Override
    protected void updateData(WeatherInfo it) {
        super.updateData(it);
    }

    @Override
    protected void initListener(View view) {
        super.initListener(view);
        mFabtn.setOnClickListener(onFloatBtnListener);
    }

    private View.OnClickListener onFloatBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();*/
            mViewModel.insertFavorLocation();
        }
    };

    @Override
    protected void initObserve() {
        mViewModel.getLiveDataCurrentLocation().observe(getViewLifecycleOwner(), observerCurrentLocation);
        mViewModel.getLiveDataCurWeatherInfo().observe(getViewLifecycleOwner(), observerCurrentWeatherInfo);
    }

    private Observer<WeaLocation> observerCurrentLocation = new Observer<WeaLocation>() {
        @Override
        public void onChanged(@Nullable WeaLocation weaLocation) {
        }
    };

    private Observer<Resource<WeatherInfo>> observerCurrentWeatherInfo = new Observer<Resource<WeatherInfo>>() {
        @Override
        public void onChanged(@Nullable Resource<WeatherInfo> it) {
            //Toast.makeText(mCtx, "Current WeatherInfo onChanged", Toast.LENGTH_SHORT).show();
            if (null != it.mData) {
                updateData(it.mData);
            }
            updateStatus(it.mStatus);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        WeaApplication.getmInstanceLds().startLocationServiceUpdate();
    }

    @Override
    public void onPause() {
        WeaApplication.getmInstanceLds().stopLocationServiceUpdate();
        super.onPause();
    }
}
