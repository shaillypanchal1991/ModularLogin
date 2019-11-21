package com.sample.loginmodule.interactor;

import com.sample.loginmodule.BuildConfig;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.network.apiUtils.APIRequestInterface;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;

import retrofit2.Call;

public class RefreshTokenInteractor extends GenericRequestHandler<Login> {

    private APIRequestInterface loginService = DataRepository.getInstance().getApiRequest();
    private String refreshToken, granttype;

    public static RefreshTokenInteractor createInstance(String token, String granttype) {

        RefreshTokenInteractor refreshWithPinLoader = new RefreshTokenInteractor();
        refreshWithPinLoader.refreshToken = token;
        refreshWithPinLoader.granttype = granttype;

        return refreshWithPinLoader;
    }

    public void onAuthRequest(IResponseStatus status) {

        doRequest(status);
    }



    @Override
    protected Call<Login> makeRequest() {
        return loginService.refreshToken( "Basic "+ BuildConfig.APIKEY,granttype,refreshToken);
    }
}
