package com.gturedi.flickr.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.gturedi.flickr.R;
import com.gturedi.flickr.adapter.DetailPagerAdapter;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.model.event.DetailEvent;
import com.gturedi.flickr.service.FlickrService;
import com.gturedi.flickr.util.AppUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created by gturedi on 8.02.2017.
 */
public class DetailActivity
        extends BaseActivity {

    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final String EXTRA_ITEMS = "EXTRA_ITEMS";
    private int index;
    private List<PhotoModel> items;
    private FlickrService flickrService = FlickrService.INSTANCE;
    private DetailEvent detailEvent;

    @BindView(R.id.pager) protected ViewPager pager;
    @BindView(R.id.tvOwner) protected TextView tvOwner;
    @BindView(R.id.tvTitle) protected TextView tvTitle;
    @BindView(R.id.tvDate) protected TextView tvDate;
    @BindView(R.id.tvViewCount) protected TextView tvViewCount;

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
            onPageSelected(index);
        }
    }

    @OnClick(R.id.ivClose)
    public void onCloseClick(View v) {
        finish();
    }

    @OnClick(R.id.ivInfo)
    public void onInfoClick(View v) {
        if (detailEvent.exception != null) return;
        new AlertDialog.Builder(this)
                .setTitle(R.string.description)
                .setMessage(detailEvent.item.description.toString())
                .setNegativeButton(R.string.close, null)
                .show();
    }

    @OnClick(R.id.ivShare)
    public void onShareClick(View v) {
        if (detailEvent.exception != null) return;
        startActivity(new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_SUBJECT, detailEvent.item.title)
                .putExtra(Intent.EXTRA_TEXT, items.get(pager.getCurrentItem()).url_n)
        );
    }

    @OnPageChange(R.id.pager)
    void onPageSelected(int position) {
        //showLoadingDialog();
        tvOwner.setText(R.string.loading);
        tvTitle.setText(R.string.loading);
        tvDate.setText(R.string.loading);
        tvViewCount.setText(R.string.loading);
        flickrService.getDetailAsync(items.get(position).id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DetailEvent event) {
        //dismissLoadingDialog();
        detailEvent = event;
        if (event.exception == null) {
            tvOwner.setText(event.item.owner.toString());
            tvTitle.setText(event.item.title.toString());
            tvDate.setText(event.item.getFormattedDate());
            tvViewCount.setText(getString(R.string.views, event.item.views));
        } else {
            tvOwner.setText("-");
            tvTitle.setText("-");
            tvDate.setText("-");
            tvViewCount.setText("-");
            showGeneralError();
        }
    }

}