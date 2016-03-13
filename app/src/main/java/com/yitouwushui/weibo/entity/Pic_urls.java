package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * 图片
 */
public class Pic_urls extends SugarRecord implements Serializable {

    String thumbnail_pic;

    public Pic_urls() {
    }

    public Pic_urls(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
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
                "thumbnail_pic='" + thumbnail_pic + '\'' +
                '}';
    }
}
