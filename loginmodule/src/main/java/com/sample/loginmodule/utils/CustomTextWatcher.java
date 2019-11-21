package com.sample.loginmodule.utils;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.sample.loginmodule.model.Login;
import com.sample.loginmodule.view.LoginActivity;

public class CustomTextWatcher implements TextWatcher {
    LoginActivity _context;

    public CustomTextWatcher(LoginActivity context) {
        _context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        _context.checkFieldsForEmptyValues();
    }

}
