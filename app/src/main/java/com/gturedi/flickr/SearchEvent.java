package com.gturedi.flickr;

import java.util.List;

/**
 * Created by gturedi on 7.02.2017.
 */
public class SearchEvent {

    public final List<PhotoModel> items;
    public final Throwable exception;

    public SearchEvent(List<PhotoModel> items, Throwable exception) {
        this.items = items;
        this.exception = exception;
    }

}