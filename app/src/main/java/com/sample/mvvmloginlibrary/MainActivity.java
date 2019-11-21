package com.sample.mvvmloginlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sample.loginmodule.app.RootLoginController;
import com.sample.loginmodule.storage.CustomEncryptedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // RootLoginController.initLoginLibrary(MainActivity.this,this);


    }


}
