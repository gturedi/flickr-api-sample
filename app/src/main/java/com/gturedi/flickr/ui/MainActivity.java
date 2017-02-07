package com.gturedi.flickr.ui;

import android.os.Bundle;

import com.gturedi.flickr.R;
import com.gturedi.flickr.model.SearchEvent;
import com.gturedi.flickr.service.FlickrService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity
        extends BaseActivity {

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLoadingDialog();
        FlickrService.INSTANCE.searchAsync(page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        dismissLoadingDialog();
        if (event.exception == null) {
            // bind data
        } else {
            showErrorDialog();
        }
    }

}