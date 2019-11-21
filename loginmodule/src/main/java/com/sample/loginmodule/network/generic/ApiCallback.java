package com.sample.loginmodule.network.generic;


import com.sample.loginmodule.errorUtils.CustomException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


abstract public class ApiCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        if (response.body() != null) {
            handleResponseData(response.body());
        } else {
            handleError(response);
        }
    }

    abstract protected void handleResponseData(T data);

    abstract protected void handleError(Response<T> response);

    abstract protected void handleException(CustomException t);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof Exception) {
            handleException((CustomException) t);
        } else {
            //do something else
        }
    }


}





