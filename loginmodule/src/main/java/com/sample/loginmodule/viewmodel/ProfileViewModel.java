package com.sample.loginmodule.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sample.loginmodule.BuildConfig;
import com.sample.loginmodule.adapters.CustomClickListener;
import com.sample.loginmodule.interactor.ProfileLoginInteractor;
import com.sample.loginmodule.interactor.ProfilesInteractor;
import com.sample.loginmodule.interactor.SignInInteractor;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.RetrofitConfiguration;
import com.sample.loginmodule.network.generic.APIObserver;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;
import com.sample.loginmodule.utils.DataRepositoryUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus {
    public MutableLiveData livedata;

    public void getProfilesOfUser() {

        if (DataRepositoryUtils.getInstance().retrieveUserSession() != null) {
            String token = DataRepositoryUtils.getInstance().retrieveUserSession().getAccessToken();
            ProfilesInteractor.createInstance("bearer " + token).onProfilesRequest(ProfileViewModel.this);

        }


    }

    /*public void loginwithProfileID(Profile profile) {
        Log.e("profile", "profile model " + profile.getNickname());
        ProfileLoginInteractor.createInstance("password","protiksengupta@yahoo.com","PoC@2019","05f47ad2af154d85b2c53f61a693e403",1111).onAuthRequest(this);

    }*/



    @Override
    public void onResponseReceived(final DataWrapper liveData) {
        if (liveData.getData() != null && liveData.getApiException() == null) {
/*
            List<Profile> profiles = (List<Profile>) liveData.getData();
            Log.e("list of profiles", "profiles " + profiles.size());*/

            livedata.setValue(liveData);

        } /*else if (liveData.getApiException().getMessage().contains("Authorization")) {
            String refreshtoken = DataRepositoryUtils.getInstance().retrieveUserSession().getRefreshToken();
            DataRepository.getInstance().getApiRequest().refreshToken("refresh_token", refreshtoken);
*/
        livedata.setValue(liveData);

    }


    public MutableLiveData getLivedata() {

        if (livedata == null) {
            livedata = new MutableLiveData<MutableLiveData>();
        }
        return livedata;
    }





}
