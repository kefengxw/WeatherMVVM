package com.WeatherMVVM.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.WeatherMVVM.R;
import com.WeatherMVVM.model.gps.LocationDataServiceConfigure;

public class HomeActivity extends AppCompatActivity {

    private static HomeActivity mInstanceHa = null;
    private BottomNavigationView mBotmNavi = null;
    private CurrentWeaFragment mCurFragment = null;
    private FavorWeaFragment mFavorFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setInstanceHa(this);
        initFragment(savedInstanceState);
        initToolBar();
        initBotmNavigation();
    }

    private void initFragment(Bundle savedInstanceState) {
        mCurFragment = CurrentWeaFragment.newInstance();
        mFavorFragment = FavorWeaFragment.newInstance();
        if (null == savedInstanceState) {
            setFragment(mCurFragment);//default
        }
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initBotmNavigation() {
        mBotmNavi = findViewById(R.id.botm_navigation);
        mBotmNavi.setOnNavigationItemSelectedListener(onBtomNaviLisener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onBtomNaviLisener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    setFragment((menuItem.getItemId() == R.id.navi_favorite) ? mFavorFragment : mCurFragment);
                    return true;
                }
            };

    private void setFragment(Fragment it) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, it).commit();
    }

    public static HomeActivity getInstanceHa() {
        return mInstanceHa;
    }

    public static void setInstanceHa(HomeActivity it) {
        HomeActivity.mInstanceHa = it;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //No need to process startResolutionForResult, because activity onResume will do it again
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationDataServiceConfigure.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                //Toast.makeText(this, "Permission allowed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //if configChange is set, than on configuration chang will be run.
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setInstanceHa(null);//set as default
        super.onDestroy();
    }
}
