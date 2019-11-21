package com.sample.loginmodule.utils;

import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.sample.loginmodule.R;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.network.generic.DataWrapper;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;

public class DataRepositoryUtils {
    private static final String TAG = DataRepositoryUtils.class.getName();
    private static final DataRepositoryUtils ourInstance = new DataRepositoryUtils();

    public static DataRepositoryUtils getInstance() {
        return ourInstance;
    }

    private DataRepositoryUtils() {

    }

    public Login retrieveUserSession() {
        String login = CustomEncryptedPreferences.getString("userdetails");
        Login userObj = null;
        try {
            Gson gson = new Gson();
            userObj = gson.fromJson(login, Login.class);
            Log.e(TAG, "decrypted" + userObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userObj;
    }

    public RequestOptions getrequestoptions() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon);

        return options;


    }


    public void storeUserSession(Login liveData) {
        Gson gson = new Gson();
        String loginString = gson.toJson((Login) liveData);
        CustomEncryptedPreferences.putString("userdetails", loginString);
        Log.e(TAG, " user session" + loginString);

    }
}
