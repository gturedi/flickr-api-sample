package com.gturedi.flickr.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.gturedi.flickr.App;

/**
 * Created by gturedi on 8.02.2017.
 */
public class AppUtil {

    public static boolean isConnected() {
        ConnectivityManager connectivitymanager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivitymanager.getActiveNetworkInfo() != null
                && connectivitymanager.getActiveNetworkInfo().isAvailable()
                && connectivitymanager.getActiveNetworkInfo().isConnected();
    }

    public static void bindImage(String url, ImageView target, boolean centerCrop) {
        DrawableRequestBuilder<String> builder = Glide.with(App.getContext())
                .load(url)
                //.placeholder(R.color.gray2)
                .crossFade();
        if (centerCrop) builder.centerCrop();
        builder.into(target);
    }

    public static Snackbar createSnackbar(Activity activity, @StringRes int resId) {
        View root = activity.findViewById(android.R.id.content);
        return Snackbar.make(root, resId, Snackbar.LENGTH_LONG);
    }

}