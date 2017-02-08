package com.gturedi.flickr.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.gturedi.flickr.R;
import com.gturedi.flickr.adapter.DetailPagerAdapter;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.util.AppUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gturedi on 8.02.2017.
 */
public class DetailActivity
        extends BaseActivity {

    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final String EXTRA_ITEMS = "EXTRA_ITEMS";
    private int index;
    private List<PhotoModel> items;
    @BindView(R.id.pager) protected ViewPager pager;

    public static Intent createIntent(Context context, int index, List<PhotoModel> items) {
        return new Intent(context, DetailActivity.class)
                .putExtra(EXTRA_INDEX, index)
                .putExtra(EXTRA_ITEMS, (Serializable) items);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra(EXTRA_INDEX, -1);
        items = (List<PhotoModel>) getIntent().getSerializableExtra(EXTRA_ITEMS);
        if (index == -1) {
            finish();
        } else if (!AppUtil.isConnected()) {
            showConnectionError();
        } else {
            pager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager(), items));
            pager.setCurrentItem(index);
        }
    }

}