package com.gturedi.flickr;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import timber.log.Timber;

/**
 * Created by gturedi on 7.02.2017.
 */
public class App
        extends Application {

    static {
        //AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * app level shared context without memory leak problem
     */
    @SuppressLint("StaticFieldLeak")
    private static Context instance;

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