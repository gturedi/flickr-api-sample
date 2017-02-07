package com.gturedi.flickr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gturedi.flickr.R;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.util.AppUtil;
import com.gturedi.flickr.util.RowClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gturedi on 8.02.2017.
 */
public class PhotoAdapter
        extends RecyclerView.Adapter<PhotoViewHolder> {

    private final List<PhotoModel> items = new ArrayList<>();
    private RowClickListener<PhotoModel> rowClickListener;

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, final int position) {
        PhotoModel item = items.get(position);
        AppUtil.bindImage(item.url_n, holder.ivCover);
        if (rowClickListener != null) {
            holder.ivCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rowClickListener.onRowClicked(position, items.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setRowClickListener(RowClickListener<PhotoModel> rowClickListener) {
        this.rowClickListener = rowClickListener;
    }

    public void addAll(List<PhotoModel> items) {
        this.items.addAll(items);
        notifyItemInserted(0);
        notifyDataSetChanged();
    }

}