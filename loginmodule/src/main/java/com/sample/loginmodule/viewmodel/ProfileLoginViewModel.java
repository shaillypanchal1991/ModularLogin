package com.sample.loginmodule.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.loginmodule.BuildConfig;
import com.sample.loginmodule.app.RootLoginController;
import com.sample.loginmodule.interactor.ProfileLoginInteractor;
import com.sample.loginmodule.interactor.SignInInteractor;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.model.LoginUser;
import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;
import com.sample.loginmodule.utils.DataRepositoryUtils;

public class ProfileLoginViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus {
    private static final String TAG = ProfileLoginViewModel.class.getName();
    public MutableLiveData<String> strUserName = new MutableLiveData<>();
    public MutableLiveData<String> strPassword = new MutableLiveData<>();


    public MutableLiveData livedata;


    private MutableLiveData<LoginUser> userMutableLiveData;


    public void loginwithProfileID(Profile profile, int pin) {
        Log.e(TAG, "profile model " + profile.getNickname());

        String username = CustomEncryptedPreferences.getString("username");
        String password = CustomEncryptedPreferences.getString("password");

        if (pin != 0) {
            ProfileLoginInteractor.createInstance("password", username, password, profile.getId(), pin).onAuthRequest(this);
        } else {
            ProfileLoginInteractor.createInstance("password", username, password, profile.getId()).onAuthRequest(this);

        }
    }


    @Override
    public void onResponseReceived(DataWrapper dataWrapper) {

//store user session
        if (dataWrapper.getData() != null && dataWrapper.getApiException() == null) {

            gotoHomepage();


            //  DataRepositoryUtils.getInstance().storeUserSession((Login) liveData.getData());
        }


    }

    private void gotoHomepage() {


        RootLoginController.setProfileSuccess();

    }


    public MutableLiveData getLivedata() {

        if (livedata == null) {
            livedata = new MutableLiveData<MutableLiveData>();
        }
        return livedata;
    }
}
