package com.socialapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by User on 3/3/2018.
 */

public class MyApp extends Application {


    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }


}
