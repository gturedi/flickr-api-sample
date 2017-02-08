package com.gturedi.flickr.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.ui.DetailFragment;

import java.util.List;

/**
 * Created by gturedi on 8.02.2017.
 */
public class DetailPagerAdapter
        extends FragmentStatePagerAdapter {

    private final List<PhotoModel> items;

    public DetailPagerAdapter(FragmentManager fm, List<PhotoModel> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }

}