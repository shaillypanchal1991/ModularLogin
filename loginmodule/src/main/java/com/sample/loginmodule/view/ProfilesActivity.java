package com.sample.loginmodule.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.sample.loginmodule.R;
import com.sample.loginmodule.adapters.ProfileRecyclerViewAdapter;
import com.sample.loginmodule.databinding.ActivityProfilesBinding;
import com.sample.loginmodule.errorUtils.CustomException;
import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.model.Profile;
import com.sample.loginmodule.network.generic.APIObserver;
import com.sample.loginmodule.utils.AutoFitGridLayoutManager;
import com.sample.loginmodule.utils.GridItemSpacingDecorator;
import com.sample.loginmodule.viewmodel.LoginViewModel;
import com.sample.loginmodule.viewmodel.ProfileLoginViewModel;
import com.sample.loginmodule.viewmodel.ProfileViewModel;

import java.util.List;

public class ProfilesActivity extends AppCompatActivity implements ProfileRecyclerViewAdapter.profileClickListener {
    ProfileViewModel profileViewModel;
    ProfileLoginViewModel profileLoginViewModel;
    ActivityProfilesBinding binding;

    private static final String TAG = ProfilesActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileLoginViewModel = ViewModelProviders.of(this).get(ProfileLoginViewModel.class);
        binding = DataBindingUtil.setContentView(ProfilesActivity.this, R.layout.activity_profiles);
        binding.setLifecycleOwner(this);
        binding.setProfileViewModel(profileViewModel);
        binding.setProfileLoginViewModel(profileLoginViewModel);


        binding.recyclerView.setLayoutManager(new AutoFitGridLayoutManager(this, 200));
        profileViewModel.getProfilesOfUser();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        binding.recyclerView.addItemDecoration(new GridItemSpacingDecorator(2, dpToPx(10), true));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        profileViewModel.getLivedata().observe(this, new APIObserver<List<Profile>>(new APIObserver.ChangeListener<List<Profile>>() {


            @Override
            public void onSuccess(List<Profile> dataWrapper) {
                List<Profile> profiles = dataWrapper;
                Log.e(TAG, "profiles " + dataWrapper.size());
                populateData(profiles);


            }

            @Override
            public void onException(CustomException exception) {
                Snackbar snackbar = Snackbar
                        .make(binding.rootLayout, exception.getErrorMessage(exception.getErrorCode()), Snackbar.LENGTH_LONG);
                snackbar.show();
                Log.e(TAG, "profiles " + exception.getMessage());
            }
        }));












    }

    private void populateData(List<Profile> profiles) {


        ProfileRecyclerViewAdapter profileRecyclerViewAdapter = new ProfileRecyclerViewAdapter(profiles, ProfilesActivity.this);

        binding.setProfileViewAdapter(profileRecyclerViewAdapter);


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));

    }

    @Override
    public void onProfileClicked(final Profile profile) {

        if(profile.getHasPin()){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter PIN");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    profileLoginViewModel.loginwithProfileID(profile,Integer.valueOf(input.getText().toString()));

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        else {
            profileLoginViewModel.loginwithProfileID(profile,0);
        }


    }



}
