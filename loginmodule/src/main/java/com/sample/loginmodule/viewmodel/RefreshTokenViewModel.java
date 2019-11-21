package com.sample.loginmodule.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.loginmodule.BuildConfig;
import com.sample.loginmodule.errorUtils.CustomException;
import com.sample.loginmodule.interactor.RefreshTokenInteractor;
import com.sample.loginmodule.interactor.SignInInteractor;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;
import com.sample.loginmodule.utils.DataRepositoryUtils;

public class RefreshTokenViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus {

    public MutableLiveData livetokendata;


    public void refreshToken() {
        try {
            Login login = DataRepositoryUtils.getInstance().retrieveUserSession();
            String expiredtoken = login.getRefreshToken();

            RefreshTokenInteractor.createInstance(expiredtoken, "refresh_token").onAuthRequest(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResponseReceived(DataWrapper liveData) {
        if (liveData != null) {
            if (liveData.getData() != null) {
                DataRepositoryUtils.getInstance().storeUserSession((Login) liveData.getData());
            }


            livetokendata.setValue(liveData);


        }
    }


    public MutableLiveData getLivetokendata() {
        if (livetokendata == null) {
            livetokendata = new MutableLiveData<MutableLiveData>();
        } else {
            return livetokendata;
        }
        return livetokendata;

    }

}
