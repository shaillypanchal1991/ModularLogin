package com.sample.loginmodule.interactor;

import com.sample.loginmodule.BuildConfig;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.network.apiUtils.APIRequestInterface;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;

import retrofit2.Call;

public class ProfileLoginInteractor extends GenericRequestHandler<Login> {

    private APIRequestInterface loginService = DataRepository.getInstance().getApiRequest();
    private String granttype, username, password, profileid;
    int profilepin;

    public static ProfileLoginInteractor createInstance(String granttype, String username, String password, String profileid, int profilepin) {
        ProfileLoginInteractor profileLoginInteractor = new ProfileLoginInteractor();

        profileLoginInteractor.granttype = granttype;
        profileLoginInteractor.username = username;
        profileLoginInteractor.password = password;
        profileLoginInteractor.profileid = profileid;
        profileLoginInteractor.profilepin = profilepin;
        return profileLoginInteractor;
    }

    public static ProfileLoginInteractor createInstance(String granttype, String username, String password, String profileid) {
        ProfileLoginInteractor profileLoginInteractor = new ProfileLoginInteractor();

        profileLoginInteractor.granttype = granttype;
        profileLoginInteractor.username = username;
        profileLoginInteractor.password = password;
        profileLoginInteractor.profileid = profileid;

        return profileLoginInteractor;
    }

    public void onAuthRequest(IResponseStatus status) {

        doRequest(status);
    }

    @Override
    protected Call<Login> makeRequest() {
        if (profilepin != 0) {
            return loginService.loginwithProfileID("Basic " + BuildConfig.APIKEY, granttype, username, password, profileid, profilepin);
        } else {
            return loginService.loginwithProfileIDwithoutPIN("Basic " + BuildConfig.APIKEY, granttype, username, password, profileid);
        }
    }
    }



