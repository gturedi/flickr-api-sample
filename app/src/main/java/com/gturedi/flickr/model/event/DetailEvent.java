package com.gturedi.flickr.model.event;

import com.gturedi.flickr.model.PhotoInfoModel;

/**
 * Created by gturedi on 7.02.2017.
 */
public class DetailEvent
        extends BaseServiceEvent<PhotoInfoModel> {

    public DetailEvent(PhotoInfoModel item, Throwable exception) {
        super(item, exception);
    }

}