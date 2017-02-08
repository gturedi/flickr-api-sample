package com.gturedi.flickr.model.event;

import com.gturedi.flickr.model.PhotoModel;

import java.util.List;

/**
 * Created by gturedi on 7.02.2017.
 */
public class SearchEvent
        extends BaseEvent<List<PhotoModel>> {

    public SearchEvent(List<PhotoModel> item, Throwable exception) {
        super(item, exception);
    }

}