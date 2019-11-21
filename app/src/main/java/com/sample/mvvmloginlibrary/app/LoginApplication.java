package com.sample.mvvmloginlibrary.app;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.Intent;

import com.sample.loginmodule.app.RootLoginController;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;
import com.sample.mvvmloginlibrary.HomepageActivity;
import com.sample.mvvmloginlibrary.MainActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginApplication extends Application implements RootLoginController.LoginCallback {

    @Override
    public void onCreate() {
        super.onCreate();

        RootLoginController.initLoginLibrary(this,this);


        /*try {
            new CustomEncryptedPreferences.Builder()
                    .setContext(getApplicationContext())
                    .setMode(ContextWrapper.MODE_PRIVATE)
                    .setPrefsName(getPackageName())
                    .setUseDefaultSharedPreference(true)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void profileLoggedInSuccessfully() {
       // CustomEncryptedPreferences.putString("userdetails",null);
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);

    }
}
