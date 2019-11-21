package com.sample.loginmodule.app;

import android.app.Activity;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.Intent;


import com.sample.loginmodule.errorUtils.CustomException;
import com.sample.loginmodule.interactor.RefreshTokenInteractor;
import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.network.generic.GenericRequestHandler;
import com.sample.loginmodule.repository.DataRepository;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;
import com.sample.loginmodule.utils.DataRepositoryUtils;
import com.sample.loginmodule.view.LoginActivity;
import com.sample.loginmodule.view.ProfilesActivity;
import com.sample.loginmodule.view.RefreshActivity;
import com.sample.loginmodule.viewmodel.RefreshTokenViewModel;

import android.content.Context;

import java.io.IOException;
import java.security.GeneralSecurityException;

public final class RootLoginController implements GenericRequestHandler.IResponseStatus {


    private static Context _context;
    private static LoginCallback _loginCallback;


    public static void initLoginLibrary(Context context, LoginCallback loginCallback) {
        _context = context;
        _loginCallback = loginCallback;

        initializeCustomPreference(context);


        if (isUserSessionAvailable()) {

            navigateToRefreshActivity(context);
        } else {
            navigateToLoginActivity(context);
        }

    }


    private static void navigateToRefreshActivity(Context activityContext) {

        Intent intent = new Intent(activityContext, RefreshActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(intent);

    }

    private static void initializeCustomPreference(Context preferenceContext) {
        try {
            new CustomEncryptedPreferences.Builder()
                    .setContext(preferenceContext)
                    .setMode(ContextWrapper.MODE_PRIVATE)
                    .setPrefsName(preferenceContext.getPackageName())
                    .setUseDefaultSharedPreference(true)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Context getApplicationContext() {
        return _context;
    }

    private static boolean isUserSessionAvailable() {

        if (DataRepositoryUtils.getInstance().retrieveUserSession() != null) {
            return true;

        } else {
            return false;
        }

    }

    private static void navigateToLoginActivity(Context activityContext) {

        Intent intent = new Intent(activityContext, LoginActivity.class);
        //  intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(intent);

    }

    private static void navigateToProfilesActivity(Context activityContext) {

        Intent intent = new Intent(activityContext, ProfilesActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(intent);
    }


    @Override
    public void onResponseReceived(DataWrapper liveData) {

    }

    public static void setProfileSuccess() {
        _loginCallback.profileLoggedInSuccessfully();
    }

    public interface LoginCallback {
        public void profileLoggedInSuccessfully();

    }
}
