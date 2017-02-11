package com.gturedi.flickr.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gturedi.flickr.R;
import com.gturedi.flickr.adapter.DetailPagerAdapter;
import com.gturedi.flickr.model.ImageSize;
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
import timber.log.Timber;

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
    @BindView(R.id.lnrFooter) protected View lnrFooter;
    @BindView(R.id.ivClose) protected View ivClose;
    @BindView(R.id.ivInfo) protected View ivInfo;
    @BindView(R.id.ivShare) protected View ivShare;

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
            setPagerClickListener();
            pager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager(), items));
            pager.setCurrentItem(index);
            onPageSelected(index);
        }

        AppUtil.setVectorBg(ivClose, R.drawable.ic_close_24dp, android.R.color.white, R.color.gray2);
        AppUtil.setVectorBg(ivInfo, R.drawable.ic_info_outline_24dp, android.R.color.white, R.color.gray2);
        AppUtil.setVectorBg(ivShare, R.drawable.ic_share_24dp, android.R.color.white, R.color.gray2);
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
        String subject = detailEvent.item.title._content;
        String text = items.get(pager.getCurrentItem()).getImageUrl(ImageSize.LARGE);
        startActivity(AppUtil.createShareIntent(subject, text));
    }

    // not work http://stackoverflow.com/questions/10243690/onclick-on-viewpager-not-triggered
    @OnClick(R.id.pager)
    public void onPagerClick(View v) {
        Timber.i("onPagerClick");
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

    // http://stackoverflow.com/questions/10243690/onclick-on-viewpager-not-triggered
    private void setPagerClickListener() {
        final GestureDetectorCompat tapGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                int value = lnrFooter.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                Timber.i("pagerClick: "+value);
                lnrFooter.setVisibility(value);
                ivClose.setVisibility(value);
                tvOwner.setVisibility(value);
                return true;
            }
        });
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

}