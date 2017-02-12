package com.gturedi.flickr.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.gturedi.flickr.App;
import com.gturedi.flickr.R;

/**
 * Created by gturedi on 8.02.2017.
 */
public class AppUtil {

    public static final String FEEDBACK_MAIL = "turedi.gokhan@gmail.com";
    private static final String MIME_TYPE_SHARE = "text/plain";
    private static final String MIME_TYPE_MAIL = "plain/text";

    public static boolean isConnected() {
        ConnectivityManager connectivitymanager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivitymanager.getActiveNetworkInfo() != null
                && connectivitymanager.getActiveNetworkInfo().isAvailable()
                && connectivitymanager.getActiveNetworkInfo().isConnected();
    }

    public static void bindImage(String url, ImageView target, boolean centerCrop) {
        DrawableRequestBuilder<String> builder = Glide.with(App.getContext())
                .load(url)
                .error(R.drawable.ic_broken_image_24dp)
                //.placeholder(R.color.gray2)
                .crossFade();
        if (centerCrop) builder.centerCrop();
        builder.into(target);
    }

    public static Snackbar createSnackbar(Activity activity, @StringRes int resId) {
        View root = activity.findViewById(android.R.id.content);
        return Snackbar.make(root, resId, Snackbar.LENGTH_LONG);
    }

    // http://stackoverflow.com/questions/26788251/android-tint-using-drawablecompat
    // http://www.donnfelker.com/quick-and-easy-statelistdrawables-in-android-with-one-png/
    public static void setVectorBg(View target, @DrawableRes int drRes, @ColorRes int normalRes, @ColorRes int pressedRes) {
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_pressed},
                new int[] { }
        };
        int[] colors = new int[] {
                ContextCompat.getColor(target.getContext(), pressedRes),
                ContextCompat.getColor(target.getContext(), normalRes)
        };
        ColorStateList cl = new ColorStateList(states, colors);

        // if you pass application as context is throw exception: Resources$NotFoundException: File res/drawable/ic_close_24dp.xml from drawable resource ID #0x7f02005c
        Drawable drawable = ContextCompat.getDrawable(target.getContext(), drRes);
        Drawable wrapped = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrapped, cl);
        target.setBackground(wrapped);
    }

    public static Intent createShareIntent(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType(MIME_TYPE_SHARE)
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, text);
        return Intent.createChooser(intent, App.getContext().getString(R.string.chooserTitle));
    }

    public static Intent createMailIntent(String mail, String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType(MIME_TYPE_MAIL)
                .putExtra(Intent.EXTRA_EMAIL, new String[]{mail})
                .putExtra(Intent.EXTRA_SUBJECT, subject);
        return Intent.createChooser(intent, App.getContext().getString(R.string.chooserTitle));
    }

}