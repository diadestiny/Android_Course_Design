package com.guet.shareapp.Common;

import android.app.Application;


public class ShareApp extends Application {

    public static ShareApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    public static ShareApp getInstance() {
        return mInstance;
    }

}