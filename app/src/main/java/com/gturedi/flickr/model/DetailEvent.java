package com.gturedi.flickr.model;

/**
 * Created by gturedi on 7.02.2017.
 */
public class DetailEvent {

    public final PhotoInfoModel item;
    public final Throwable exception;

    public DetailEvent(PhotoInfoModel item, Throwable exception) {
        this.item = item;
        this.exception = exception;
    }

}