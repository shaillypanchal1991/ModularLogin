package com.sample.loginmodule.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.loginmodule.network.apiUtils.HeaderInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitConfiguration {


    private static final RetrofitConfiguration instance = new RetrofitConfiguration();
    public static final String BASE_URL = "https://usermgt-staging.shared-svc.bellmedia.ca";


    public static Retrofit getRetrofitInstance() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
        okBuilder.addInterceptor(headerInterceptor);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okBuilder.build())
                .build();

        return retrofit;

    }


}
