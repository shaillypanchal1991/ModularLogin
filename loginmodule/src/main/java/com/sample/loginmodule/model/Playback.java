package com.sample.loginmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Playback {

    @SerializedName("language")
    @Expose
    private Object language;

    public Object getLanguage() {
        return language;
    }



}