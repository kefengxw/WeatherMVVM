package com.WeatherMVVM.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.WeatherMVVM.R;
import com.WeatherMVVM.model.remote.Resource;
import com.WeatherMVVM.viewmodel.WeaShareViewModel;

abstract public class BaseWeaFragment extends Fragment {

    protected WeaShareViewModel mViewModel = null;
    protected Context mCtx = null;
    private View mBaseView = null;

    //These can be improved by data binding,but it will increase app size
    private TextView mLatitude = null;
    private TextView mLongitude = null;
    private TextView mTimezone = null;
    private TextView mTime = null;
    private TextView mSummary = null;
    private TextView mTemperature = null;
    private TextView mHumidity = null;
    private TextView mPressure = null;
    private TextView mWindSpeed = null;
    private TextView mCloudCover = null;
    private TextView mVisibility = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCtx = context;
        getViewModel();//improve query 2 times info from network at initial phase
    }

    private void getViewModel() {
        //Notice, this is the shared view model, share data between these fragments,owner is getActivity()
        //Only get one/same instance of mViewModel
        mViewModel = ViewModelProviders.of(getActivity()).get(WeaShareViewModel.class);
    }

    abstract protected int ProvidedFragmentLayoutId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int id = ProvidedFragmentLayoutId();

        if (mBaseView == null) {
            mBaseView = inflater.inflate(id, container, false);
        }
        ViewGroup viewGroup = (ViewGroup) mBaseView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mBaseView);
        }
        return mBaseView;//just for improve the performance
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(mBaseView);
        initObserve();
    }

    private void init(View view) {

        initCommView(view);
        initView(view);
        //init Data, init Listener if need
        initListener(view);
    }

    private void initCommView(View view) {
        initAllView(view);
    }

    protected void initView(View view) {
        //Tobe re-write
    }

    protected void initListener(View view) {
        //Tobe re-write
    }

    protected void initObserve() {
        //Tobe re-write
    }

    protected void updateData(WeatherInfo it) {
        updateWeaData(it);
    }

    protected void updateStatus(Resource.Status it) {
        //do something here about it.mStatus, let child-class decide
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //These can be improved by data binding,but it will increase app size
    private void initAllView(View view) {
        mLatitude = view.findViewById(R.id.latitude_text);
        mLongitude = view.findViewById(R.id.longitude_text);
        mTimezone = view.findViewById(R.id.timezone_text);
        mTime = view.findViewById(R.id.time_text);
        mSummary = view.findViewById(R.id.summary_text);
        mTemperature = view.findViewById(R.id.temperature_text);
        mHumidity = view.findViewById(R.id.humidity_text);
        mPressure = view.findViewById(R.id.pressure_text);
        mWindSpeed = view.findViewById(R.id.windSpeed_text);
        mCloudCover = view.findViewById(R.id.cloudCover_text);
        mVisibility = view.findViewById(R.id.visibility_text);
    }

    //These can be improved by data binding,but it will increase app size
    private void updateWeaData(WeatherInfo it) {
        mLatitude.setText(it.mLatitude);
        mLongitude.setText(it.mLongitude);
        mTimezone.setText(it.mTimezone);
        mTime.setText(it.mTime);
        mSummary.setText(it.mSummary);
        mTemperature.setText(it.mTemperature);
        mHumidity.setText(it.mHumidity);
        mPressure.setText(it.mPressure);
        mWindSpeed.setText(it.mWindSpeed);
        mCloudCover.setText(it.mCloudCover);
        mVisibility.setText(it.mVisibility);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
