package com.gturedi.flickr.model.event;

/**
 * Created by gturedi on 7.02.2017.
 */
public class BaseServiceEvent<T> {

    public final T item;
    public final Throwable exception;

    public BaseServiceEvent(T item, Throwable exception) {
        this.item = item;
        this.exception = exception;
    }

}