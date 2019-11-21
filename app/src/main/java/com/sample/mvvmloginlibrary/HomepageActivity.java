package com.sample.mvvmloginlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sample.loginmodule.app.RootLoginController;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;

public class HomepageActivity extends AppCompatActivity {
    TextView txtLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        txtLogout=findViewById(R.id.txtLogout);
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CustomEncryptedPreferences.putString("userdetails",null);
                RootLoginController.initLoginLibrary(getApplicationContext(), (RootLoginController.LoginCallback) getApplicationContext());


            }
        });



    }


}
