package com.gturedi.flickr;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by gturedi on 7.02.2017.
 */
public class App
        extends Application {

    private static Context instance;

    /**
     * app level shared context without memory leak problem
     */
    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        Timber.i("onCreate");
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

}