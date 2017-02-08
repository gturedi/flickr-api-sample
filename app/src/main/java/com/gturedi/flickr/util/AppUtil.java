package com.gturedi.flickr.util;

import android.content.Context;
import android.net.ConnectivityManager;
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
                //.placeholder(R.drawable.ic_image_24dp)
                .crossFade();
        if (centerCrop) builder.centerCrop();
        builder.into(target);
    }

}