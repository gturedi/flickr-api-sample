package com.gturedi.flickr.util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * taken from https://github.com/geftimov/android-viewpager-transformers
 */
public class ParallaxPageTransformer
        implements ViewPager.PageTransformer {

    private final int viewToParallax;

    public ParallaxPageTransformer(final int viewToParallax) {
        this.viewToParallax = viewToParallax;
    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(1);
        } else if (position <= 1) { // [-1,1]
            view.findViewById(viewToParallax).setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1);
        }
    }

}