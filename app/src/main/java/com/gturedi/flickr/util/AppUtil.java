package com.gturedi.flickr.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gturedi.flickr.App;
import com.gturedi.flickr.R;

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

    public static void bindImage(String url, ImageView target) {
        Glide.with(App.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_image_24dp)
                .crossFade()
                .into(target);
    }

}