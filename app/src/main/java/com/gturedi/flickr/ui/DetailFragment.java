package com.gturedi.flickr.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.Toast;

import com.gturedi.flickr.R;
import com.gturedi.flickr.model.event.DetailEvent;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.service.FlickrService;
import com.gturedi.flickr.util.AppUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by gturedi on 8.02.2017.
 */
public class DetailFragment
        extends BaseFragment {

    private static final String EXTRA_ITEM = "EXTRA_ITEM";
    private FlickrService flickrService = FlickrService.INSTANCE;
    @BindView(R.id.image) protected ImageView image;

    public static Fragment newInstance(PhotoModel item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_ITEM, item);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PhotoModel item = (PhotoModel) getArguments().getSerializable(EXTRA_ITEM);
        flickrService.getDetailAsync(item.id);
        AppUtil.bindImage(item.url_n, image);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DetailEvent event) {
        //dismissLoadingDialog();
        if (event.exception == null) {
            Toast.makeText(getActivity(), event.item.title.toString(), Toast.LENGTH_SHORT).show();
        } else {
            //showGeneralError();
        }
    }

}