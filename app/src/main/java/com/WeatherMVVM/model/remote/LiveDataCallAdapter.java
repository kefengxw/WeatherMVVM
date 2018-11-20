package com.WeatherMVVM.model.remote;

import android.arch.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<ApiResponse<T>>> {

    private final Type mResponseType;

    public LiveDataCallAdapter(Type responseType) {
        this.mResponseType = responseType;
    }

    @Override
    public LiveData<ApiResponse<T>> adapt(Call<T> call) {

        LiveData<ApiResponse<T>> it = new LiveData<ApiResponse<T>>() {
            //just to make sure init by only one Thread
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> call, Response<T> response) {
                            //postValue() can be used in both workThread and UI thread
                            //setValue() can only be used in UI thread
                            postValue(new ApiResponse<>(response));
                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
                            postValue(new ApiResponse<T>(t));
                        }
                    });
                }
            }
        };

        return it;
    }

    @Override
    public Type responseType() {
        return mResponseType;
    }
}
