package com.yitouwushui.weibo.Login;


import android.content.Context;

public class App {

    public static final String URL = "https://api.weibo.com/oauth2/authorize?client_id=3078393470&redirect_uri=https://api.weibo.com/oauth2/default.html";

    //微博开发者App Key
    public static final String CLIENT_ID = "3078393470";

    //微博开发者App Secret
    public static final String CLIENT_SECRET = "fa341b31035872b7ee9657288ba2dc4f";

    //grant_type
    public static final String GRANT_TYPE = "authorization_code";

    //回调地址
    public static final String REDIRECTURL = "https://api.weibo.com/oauth2/default.html";

    //令牌
    public static final String ACCESS_TOKEN = "access_token";

    // 登录
    public static final int MESSAGE_LOGIN = 1;

    // 用户
    public static final int MESSAGE_USER = 2;

    // 公共微博
    public static final int MESSAGE_PUBLIC_STATUS = 3;

    // 关注微博
    public static final int MESSAGE_FRIENDS_STATUS = 4;

    // 评论
    public static final int MESSAGE_STATUS_COMMENTS = 5;

    // 关注的用户
    public static final int MESSAGE_FRIENDS_USERS = 6;

    // 我的微博
    public static final int MESSAGE_MY_STATUS = 7;

    // 添加收藏
    public static final int MESSAGE_ADD_FAVORITES = 8;

    // 转发标题
    public static final String ACTION_TRANLATE_TITLE = "ACTION_TRANLATE_TITLE";
    // 转发微博id
    public static final String ACTION_TRANLATE_STATUS_IDSTR = "ACTION_TRANLATE_STATUS_IDSTR";
    // 评论标题
    public static final String ACTION_COMMENT_TITLE = "ACTION_COMMENT_TITLE";
    // 评论微博id
    public static final String ACTION_COMMENT_STATUS_IDSTR = "ACTION_COMMENT_STATUS_IDSTR";
    // 发微博
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    // 关注和粉丝用户Id
    public static final String ACTION_USERID = "ACTION_USERID";
    // 关注和粉丝的标题
    public static final String ACTION_FOLLOW_TITLE = "ACTION_FOLLOW_TITLE";

    // 是粉丝还是关注
    public static final String ACTION_ISFOLLOW = "ACTION_ISFOLLOW";

    /**
     * 当前用户编号
     */
    public static final String CURRENT_ID = "CURRENT_ID";

}

