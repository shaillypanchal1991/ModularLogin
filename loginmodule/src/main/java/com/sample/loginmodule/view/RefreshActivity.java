package com.sample.loginmodule.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.sample.loginmodule.R;
import com.sample.loginmodule.databinding.ActivityLoginBinding;
import com.sample.loginmodule.databinding.ActivityRefreshBinding;
import com.sample.loginmodule.errorUtils.CustomException;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.network.generic.APIObserver;
import com.sample.loginmodule.viewmodel.LoginViewModel;
import com.sample.loginmodule.viewmodel.RefreshTokenViewModel;

public class RefreshActivity extends AppCompatActivity {
    RefreshTokenViewModel refreshTokenModel;
    ActivityRefreshBinding binding;
    private static final String TAG = RefreshActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);


        refreshTokenModel = ViewModelProviders.of(this).get(RefreshTokenViewModel.class);

        binding = DataBindingUtil.setContentView(RefreshActivity.this, R.layout.activity_refresh);
        binding.setLifecycleOwner(this);
        binding.setRefreshTokenModel(refreshTokenModel);

        refreshTokenModel.refreshToken();
        refreshTokenModel.getLivetokendata().observe(this, new APIObserver<Login>(new APIObserver.ChangeListener<Login>() {
            @Override
            public void onSuccess(Login dataWrapper) {

                Intent intent = new Intent(RefreshActivity.this, ProfilesActivity.class);
                startActivity(intent);
                Log.e(TAG, "asdsd" + dataWrapper.getAccountId());
            }

            @Override
            public void onException(CustomException exception) {
                Snackbar snackbar = Snackbar
                        .make(binding.rootlayout, exception.getErrorMessage(exception.getErrorCode()), Snackbar.LENGTH_LONG);
                snackbar.show();


                Log.e(TAG, "" + exception.getMessage().toString());
            }
        }));
    }
}
