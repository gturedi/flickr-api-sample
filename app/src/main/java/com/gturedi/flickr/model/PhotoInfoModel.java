package com.gturedi.flickr.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gturedi on 7.02.2017.
 */
public class PhotoInfoModel
        extends BaseModel {

    public long id;
    public long dateuploaded;
    public long views;
    public ContentModel title;
    public ContentModel description;
    public OwnerModel owner;
    //public ContentModel comments;

    public String getFormattedDate() {
        // service short timeStamp veriyor o yuzden bin ile carpiyoruz
        return SimpleDateFormat.getDateInstance().format(new Date(dateuploaded* 1000));
    }

}