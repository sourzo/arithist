package com.github.sourzo.a_rithist;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class ARithist extends Application {
    @Override
    // Called when the application is starting, before any other application objects have been created.
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
