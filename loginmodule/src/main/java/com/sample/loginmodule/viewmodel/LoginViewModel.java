package com.sample.loginmodule.viewmodel;


import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sample.loginmodule.BuildConfig;
import com.sample.loginmodule.interactor.SignInInteractor;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.model.LoginUser;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;
import com.sample.loginmodule.utils.DataRepositoryUtils;

import java.util.Objects;

public class LoginViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus {
    private static final String TAG = LoginViewModel.class.getName();
    public MutableLiveData<String> strUserName = new MutableLiveData<>();
    public MutableLiveData<String> strPassword = new MutableLiveData<>();


    public MutableLiveData livedata;


    private MutableLiveData<LoginUser> userMutableLiveData;

    public void onLoginClick() {

        setUser();


    }

    public void callLogin() {

        CustomEncryptedPreferences.putString("username", strUserName.getValue());
        CustomEncryptedPreferences.putString("password", strPassword.getValue());

        SignInInteractor.createInstance(BuildConfig.APIKEY, "password", strUserName.getValue(), strPassword.getValue()).onAuthRequest(LoginViewModel.this);

    }


    public void setUser() {

        LoginUser loginUser = new LoginUser(strUserName.getValue(), strPassword.getValue());

        userMutableLiveData.setValue(loginUser);
    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }


    @Override
    public void onResponseReceived(DataWrapper liveData) {

//store user session
        if (liveData.getData() != null && liveData.getApiException() == null) {
            DataRepositoryUtils.getInstance().storeUserSession((Login) liveData.getData());
        }

        livedata.setValue(liveData);


    }


    public MutableLiveData getLivedata() {

        if (livedata == null) {
            livedata = new MutableLiveData<MutableLiveData>();
        }
        return livedata;
    }


}




