package com.sample.loginmodule.repository;

import androidx.lifecycle.MutableLiveData;

import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.apiUtils.APIRequestInterface;

import com.sample.loginmodule.network.RetrofitConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private APIRequestInterface apiRequest;

    final MutableLiveData<Login> loginInfo = new MutableLiveData<>();
    final MutableLiveData<List<Profile>> profiles = new MutableLiveData<>();
    private static final DataRepository instance = new DataRepository();

    public DataRepository() {
        apiRequest = RetrofitConfiguration.getRetrofitInstance().create(APIRequestInterface.class);
    }

    public static DataRepository getInstance() {
        return instance;
    }

    public APIRequestInterface getApiRequest() {
        return apiRequest;
    }

    public MutableLiveData<Login> getLoginResponse(String token, String grantType, String userName, String password) {


       return null;

    }

    public MutableLiveData<List<Profile>> getProfiles(String token) {

        final MutableLiveData<List<Profile>> profileInfo = new MutableLiveData<>();
        apiRequest.getUserProfiles(token).enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {

                profileInfo.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {

            }
        });

        return null;
    }
}
