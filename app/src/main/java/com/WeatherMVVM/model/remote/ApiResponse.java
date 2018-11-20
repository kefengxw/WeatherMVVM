package com.WeatherMVVM.model.remote;

import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    private int mCode;//default
    @Nullable
    private T mBody;
    private String mErrMsg = null;

    public int getmCode() {
        return mCode;
    }

    @Nullable
    T getmBody() {
        return mBody;
    }

    String getmErrMsg() {
        return mErrMsg;
    }

    ApiResponse(Response<T> response) {
        mCode = response.code();

        if (response.isSuccessful()) {
            handleSuccessApiResponse(response);
        } else {
            handleErrorApiResponse(response);
        }
    }

    private void handleSuccessApiResponse(Response<T> response) {
        mBody = response.body();
        mErrMsg = null;//default is null
    }

    private void handleErrorApiResponse(Response<T> response) {
        String msg = null;
        if (response.errorBody() != null) {
            try {
                msg = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();//Log here
            }
        }
        if (msg == null || msg.trim().length() == 0) {
            msg = response.message();
        }
        mBody = null;
        mErrMsg = msg;
    }

    ApiResponse(Throwable it) {
        this.mCode = 500;
        this.mBody = null;
        this.mErrMsg = it.getMessage();
    }

    boolean isSuccessful() {
        //HTTP status codes, HTTP/1.1 standard (RFC 7231)
        //1xx (Informational): The request was received, continuing process
        //2xx (Successful): The request was successfully received, understood, and accepted
        //3xx (Redirection): Further action needs to be taken in order to complete the request
        //4xx (Client Error): The request contains bad syntax or cannot be fulfilled
        //5xx (Server Error): The server failed to fulfill an apparently valid request
        return mCode >= 200 && mCode < 300;
    }
}
