package com.WeatherMVVM.model.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public abstract class NetworkResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> mResult = new MediatorLiveData<>();
    protected ResultType mResultTemp = null;

    @MainThread
    public NetworkResource() {
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<RequestType>> apiResponse = createNetworkCall();
        mResult.setValue((Resource<ResultType>) Resource.loading(null));
        mResult.addSource(apiResponse, requestTypeApiResponse -> {
            mResult.removeSource(apiResponse);
            convertRequestToResult(requestTypeApiResponse.getmBody());

            if (requestTypeApiResponse.isSuccessful()) {
                mResult.setValue(Resource.success(mResultTemp));
            } else {
                onFetchFailed();
                mResult.setValue(Resource.error(mResultTemp, requestTypeApiResponse.getmErrMsg()));
            }
        });
    }

    protected abstract void convertRequestToResult(RequestType it);

    @NonNull
    @MainThread // Called to create the API call.
    protected abstract LiveData<ApiResponse<RequestType>> createNetworkCall();

    @MainThread
    // Called when the fetch fails. The child class may want to reset components like rate limiter.
    protected void onFetchFailed() {
        /*Log*/
    }

    // Returns a LiveData object that represents the resource that's implemented in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return mResult;
    }
}