package com.gturedi.flickr;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by gturedi on 7.02.2017.
 */
public class App
        extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

}