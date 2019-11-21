package com.sample.loginmodule.network.generic;

import com.sample.loginmodule.errorUtils.CustomException;

public class DataWrapper<T> {
    private CustomException apiException;
    private T data;

    public CustomException getApiException() {
        return apiException;
    }

    public void setApiException(CustomException apiException) {
        this.apiException = apiException;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}