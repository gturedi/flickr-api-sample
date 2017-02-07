package com.gturedi.flickr;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by gturedi on 8.02.2017.
 */
public class AppUtil {

    public static void bindImage(String url, ImageView target) {
        Glide.with(App.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_image_24dp)
                .crossFade()
                .into(target);
    }

}