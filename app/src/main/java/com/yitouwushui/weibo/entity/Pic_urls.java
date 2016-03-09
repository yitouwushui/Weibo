package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by yitouwushui on 2016/3/8.
 */
public class Pic_urls extends SugarRecord implements Serializable {

    long status_id;

    String thumbnail_pic;

    public Pic_urls() {
    }

    public long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(long status_id) {
        this.status_id = status_id;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    @Override
    public String toString() {
        return "Pic_urls{" +
                "status_id='" + status_id + '\'' +
                ", thumbnail_pic='" + thumbnail_pic + '\'' +
                '}';
    }
}
