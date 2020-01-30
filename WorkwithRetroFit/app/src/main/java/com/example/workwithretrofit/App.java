package com.example.workwithretrofit;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BackendModule.createInstance();
    }
}
