package com.gturedi.flickr.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gturedi on 7.02.2017.
 */
public class PhotoInfoModel
        extends BaseModel {

    private static final String DATE_PATTERN = "dd.MM.yyyy";

    public long id;
    public long dateuploaded;
    public long views;
    public ContentModel comments;
    public ContentModel owner;
    public ContentModel title;
    public ContentModel description;

    public String getFormattedDate() {
        return new SimpleDateFormat(DATE_PATTERN).format(new Date(dateuploaded));
    }

}