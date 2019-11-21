package com.sample.loginmodule.errorUtils;

import android.util.Log;

public class ValidationAPIException extends Exception {

    public ValidationAPIException(String errorMessage) {
        super(errorMessage);
    }


}
