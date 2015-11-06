package com.cs151.learningassistant;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MainApplication extends Application {

    public static ReminderDataStructure Data;

    @Override
    public void onCreate(){
        super.onCreate();

        /**
         * Setup the Stetho debugging platform
         */
        Stetho.initializeWithDefaults(this);

        Data = ReminderDataStructure.getInstance(this);
    }
}
