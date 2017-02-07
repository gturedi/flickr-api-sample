package com.gturedi.flickr.model;

/**
 * Created by gturedi on 7.02.2017.
 */
public class PhotoInfoModel
        extends BaseModel {

    public long id;
    public long dateuploaded;
    public long views;
    public ContentModel comments;
    public ContentModel owner;
    public ContentModel title;
    public ContentModel description;

}