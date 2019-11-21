package com.sample.loginmodule.errorUtils;

import okhttp3.internal.http2.ErrorCode;

public class CustomException extends Exception {
    private final int errorCode;


    public CustomException(String message, int code) {
        super(message);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage(int errorCode){
        switch (errorCode){
            case 401 : return "Authorization Failure";
            case 500 : return "The server seems to be down";
            case 400 : return "Bad credentials";

        }
        return "Something went wrong.Please try again later";
    }

}
