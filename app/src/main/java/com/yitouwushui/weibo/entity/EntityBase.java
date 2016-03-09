package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by yitouwushui on 2016/3/9.
 */
public class EntityBase extends SugarRecord {

    ArrayList<Comments> commentsArrayList;

    public EntityBase() {
    }

    public ArrayList<Comments> getCommentsArrayList() {
        return commentsArrayList;
    }

    public void setCommentsArrayList(ArrayList<Comments> commentsArrayList) {
        this.commentsArrayList = commentsArrayList;
    }

    @Override
    public String toString() {
        return "EntityBase{" +
                "commentsArrayList=" + commentsArrayList +
                '}';
    }
}
