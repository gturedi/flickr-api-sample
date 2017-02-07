package com.gturedi.flickr.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gturedi.flickr.util.AppUtil;
import com.gturedi.flickr.R;
import com.gturedi.flickr.model.SearchEvent;
import com.gturedi.flickr.service.FlickrService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by gturedi on 8.02.2017.
 */
public class DetailActivity
        extends BaseActivity {

    public static final String EXTRA_ID = "ID";
    private FlickrService flickrService = FlickrService.INSTANCE;

    public static Intent createIntent(Context context, long id) {
        return new Intent(context, DetailActivity.class).putExtra(EXTRA_ID, id);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        if (id == -1) {
            finish();
        } else if (!AppUtil.isConnected()) {
            showConnectionError();
        } else {
            showLoadingDialog();
            flickrService.getDetailAsync(id);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        dismissLoadingDialog();
        if (event.exception == null) {
        } else {
            showErrorDialog();
        }
    }

}