package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * 评论类
 */
public class Comments extends SugarRecord implements Serializable {

    String statusId;
    String userId;
    String created_at;
    String text;
    String source;
    User user;
    String idstr;
    Status status;
    Object reply_comment;

    public Comments() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public Object getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(Object reply_comment) {
        this.reply_comment = reply_comment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "created_at='" + created_at + '\'' +
                ", statusId='" + statusId + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", user=" + user +
                ", idstr='" + idstr + '\'' +
                ", status=" + status +
                ", reply_comment=" + reply_comment +
                '}';
    }
}
