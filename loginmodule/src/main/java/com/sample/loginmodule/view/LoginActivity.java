package com.sample.loginmodule.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.sample.loginmodule.R;
import com.sample.loginmodule.databinding.ActivityLoginBinding;
import com.sample.loginmodule.errorUtils.CustomException;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.model.LoginUser;
import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.generic.APIObserver;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;
import com.sample.loginmodule.utils.CustomTextWatcher;
import com.sample.loginmodule.utils.DataRepositoryUtils;
import com.sample.loginmodule.viewmodel.LoginViewModel;
import com.sample.loginmodule.viewmodel.ProfileLoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    LoginViewModel loginViewModel;
    ProfileLoginViewModel profileLoginViewModel;
    ActivityLoginBinding binding;
    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        super.onCreate(savedInstanceState);


        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        profileLoginViewModel = ViewModelProviders.of(this).get(ProfileLoginViewModel.class);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);


        binding.buttonSignin.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        enableDisableSignIn();
        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {
                boolean isValid = true;
                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                    binding.editTextUsername.setError("Enter an E-Mail Address");
                    binding.editTextUsername.requestFocus();
                    isValid = false;
                } else if (!loginUser.isEmailValid()) {
                    binding.editTextUsername.setError("Enter a Valid E-mail Address");
                    binding.editTextUsername.requestFocus();
                    isValid = false;
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                    binding.editTextPassword.setError("Enter a Password");
                    binding.editTextPassword.requestFocus();
                    isValid = false;
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.editTextPassword.setError("Enter at least 6 Digit password");
                    binding.editTextPassword.requestFocus();
                    isValid = false;
                } else if (loginUser.isPasswordLengthGreaterThan120()) {
                    binding.editTextPassword.setError("Password is too long");
                    binding.editTextPassword.requestFocus();
                    isValid = false;
                } else if (!loginUser.isPasswordStrong()) {
                    binding.editTextPassword.setError("Please enter a strong password");
                    binding.editTextPassword.requestFocus();
                    isValid = false;
                }

                if (isValid) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    loginViewModel.callLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "not valid", Toast.LENGTH_SHORT).show();
                }

            }
        });


        loginViewModel.getLivedata().observe(this, new APIObserver<Login>(new APIObserver.ChangeListener<Login>() {
            @Override
            public void onSuccess(Login dataWrapper) {

                Intent intent = new Intent(LoginActivity.this, ProfilesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
                Log.e(TAG, "" + dataWrapper.getAccountId());
            }

            @Override
            public void onException(CustomException exception) {
                Snackbar snackbar = Snackbar
                        .make(binding.rootLayout, exception.getErrorMessage(exception.getErrorCode()), Snackbar.LENGTH_LONG);
                snackbar.show();


                Log.e(TAG, "" + exception.getMessage().toString());
            }
        }));


    }


    public void enableDisableSignIn() {
        CustomTextWatcher customTextWatcher = new CustomTextWatcher(this);
        binding.editTextPassword.addTextChangedListener(customTextWatcher);
        binding.editTextUsername.addTextChangedListener(customTextWatcher);


    }

    public void checkFieldsForEmptyValues() {


        if (!(TextUtils.isEmpty(binding.editTextPassword.getText())) && !(TextUtils.isEmpty(binding.editTextUsername.getText()))) {
            binding.buttonSignin.setEnabled(true);
            binding.buttonSignin.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            binding.buttonSignin.setEnabled(false);
            binding.buttonSignin.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }
}


