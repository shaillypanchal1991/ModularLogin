package com.sample.loginmodule.interactor;

import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.network.apiUtils.APIRequestInterface;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;


import retrofit2.Call;


public class SignInInteractor extends GenericRequestHandler<Login> {
    private APIRequestInterface loginService = DataRepository.getInstance().getApiRequest();
    private String token, granttype, username, password;

    public static SignInInteractor createInstance(String token, String granttype, String username, String password) {
        SignInInteractor signInWithPinLoader = new SignInInteractor();
        signInWithPinLoader.token = token;
        signInWithPinLoader.granttype = granttype;
        signInWithPinLoader.username = username;
        signInWithPinLoader.password = password;
        return signInWithPinLoader;
    }

    public void onAuthRequest(IResponseStatus status) {

        doRequest(status);
    }

    @Override
    protected Call<Login> makeRequest() {

        return loginService.login("Basic " + token, granttype, username, password);

    }


}