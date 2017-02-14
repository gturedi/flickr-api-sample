package com.gturedi.flickr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by gturedi on 8.02.2017.
 */
public class ProgressViewHolder
        extends RecyclerView.ViewHolder {

    public ProgressViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}