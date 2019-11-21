package com.sample.loginmodule.interactor;

import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.apiUtils.APIRequestInterface;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;

import java.util.List;

import retrofit2.Call;

public class ProfilesInteractor extends GenericRequestHandler<List<Profile>>  {


    private APIRequestInterface profileService = DataRepository.getInstance().getApiRequest();
    private String authorizationToken;

    public static ProfilesInteractor createInstance(String token) {
        ProfilesInteractor profilesInteractor = new ProfilesInteractor();
        profilesInteractor.authorizationToken = token;

        return profilesInteractor;
    }

    public void onProfilesRequest(IResponseStatus status) {

        doRequest(status);

    }

    @Override
    protected Call<List<Profile>> makeRequest() {
        return profileService.getUserProfiles(authorizationToken);
    }


}
