package com.WeatherMVVM.model.remote;

import android.support.annotation.NonNull;

public class Resource<T> {

    public enum Status {SUCCESS, ERROR, LOADING}

    @NonNull
    public final Status mStatus;
    public final T mData;
    public final String mMessage;//in case needed

    public Resource(@NonNull Status status, T data, String message) {
        this.mStatus = status;
        this.mData = data;
        this.mMessage = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<T>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(T data, String msg) {
        return new Resource<T>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<T>(Status.LOADING, data, null);
    }
}
