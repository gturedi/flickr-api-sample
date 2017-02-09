package com.gturedi.flickr.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.gturedi.flickr.R;
import com.gturedi.flickr.model.ImageSize;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.util.AppUtil;

import butterknife.BindView;

/**
 * Created by gturedi on 8.02.2017.
 */
public class DetailFragment
        extends BaseFragment {

    private static final String EXTRA_ITEM = "EXTRA_ITEM";

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
        AppUtil.bindImage(item.getImageUrl(ImageSize.LARGE), image, false);
    }

}