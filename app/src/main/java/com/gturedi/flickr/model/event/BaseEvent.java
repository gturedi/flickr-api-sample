package com.gturedi.flickr.model.event;

/**
 * Created by gturedi on 7.02.2017.
 */
public class BaseEvent<T> {

    public final T item;
    public final Throwable exception;

    public BaseEvent(T item, Throwable exception) {
        this.item = item;
        this.exception = exception;
    }

}