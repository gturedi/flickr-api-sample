package com.gturedi.flickr.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gturedi.flickr.R;
import com.gturedi.flickr.adapter.PhotoAdapter;
import com.gturedi.flickr.model.SearchEvent;
import com.gturedi.flickr.service.FlickrService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class MainActivity
        extends BaseActivity {

    private PhotoAdapter adapter;
    private int page = 1;

    @BindView(R.id.rvItems)
    protected RecyclerView rvItems;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showLoadingDialog();
        FlickrService.INSTANCE.searchAsync(page);

        adapter = new PhotoAdapter();
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        dismissLoadingDialog();
        if (event.exception == null) {
            adapter.addAll(event.items);
        } else {
            showErrorDialog();
        }
    }

}