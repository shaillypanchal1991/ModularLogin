package com.sample.loginmodule.network.generic;


import retrofit2.Response;

public class ApiErrorHandler {


    public static <R> Exception getErrorData(Response<R> response) {

        if (response.errorBody() != null) {

            switch (response.code()) {
                case 400:
                    return new Exception("The username or password is incorrect", new Throwable(String.valueOf(UserExceptionType.INVALID_CREDENTIALS)));

                case 500:
                    return new Exception("The server seems to be down. Please try again later", new Throwable(String.valueOf(UserExceptionType.SERVER_DOWN)));
                case 401:
                    return new Exception("Authorization error . Please try again later", new Throwable(String.valueOf(UserExceptionType.AUTHORIZATION_ERROR)));

            }
        }

        return new Exception("Something failed.", new Throwable(String.valueOf(UserExceptionType.UNKNOWN_ERROR)));

    }
}
