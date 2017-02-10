package com.gturedi.flickr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gturedi.flickr.R;
import com.gturedi.flickr.model.ImageSize;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.util.AppUtil;
import com.gturedi.flickr.util.RowClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gturedi on 8.02.2017.
 */
public class PhotoAdapter
        extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private final List<PhotoModel> items = new ArrayList<>();
    private RowClickListener<PhotoModel> rowClickListener;

    @Override
    public int getItemViewType(int position) {
        return items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo, parent, false);
            return new PhotoViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false);
            return new ProgressViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PhotoViewHolder) {
            PhotoViewHolder vh = (PhotoViewHolder) holder;
            PhotoModel item = items.get(position);
            AppUtil.bindImage(item.getImageUrl(ImageSize.MEDIUM), vh.image, true);
            if (rowClickListener != null) {
                vh.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rowClickListener.onRowClicked(position, items.get(position));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setRowClickListener(RowClickListener<PhotoModel> rowClickListener) {
        this.rowClickListener = rowClickListener;
    }

    public void addAll(List<PhotoModel> newItems) {
        if (newItems == null) {
            items.add(null);
            notifyItemInserted(getItemCount() - 1);
        } else {
            items.addAll(newItems);
            notifyDataSetChanged();
        }
    }

    public List<PhotoModel> getAll() {
        return items;
    }

    public void remove(int index) {
        if (index == -1) return;
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

}