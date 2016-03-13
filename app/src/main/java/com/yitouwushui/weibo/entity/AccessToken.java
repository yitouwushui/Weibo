package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * 用户令牌
 */
public class AccessToken extends SugarRecord implements Serializable {

    String access_token;
    String expires_in;
    String uid;

    public AccessToken() {
    }

    public AccessToken(String access_token, String expires_in, String uid) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.uid = uid;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
