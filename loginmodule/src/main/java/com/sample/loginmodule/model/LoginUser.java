package com.sample.loginmodule.model;

import android.util.Patterns;


public class LoginUser {

    private String strEmailAddress;
    private String strPassword;

    public LoginUser(String EmailAddress, String Password) {
        strEmailAddress = EmailAddress;
        strPassword = Password;
    }

    public String getStrEmailAddress() {
        return strEmailAddress;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getStrEmailAddress()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getStrPassword().length() > 5;
    }

    public boolean isPasswordLengthGreaterThan120() {
        return getStrPassword().length() > 120;
    }

    public boolean isPasswordStrong() {
        if(getStrPassword().equalsIgnoreCase("password")|| getStrPassword().equalsIgnoreCase("qwerty")){
            return false;
        }
        else{
            return true;
        }
    }
}
