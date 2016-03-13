package com.yitouwushui.weibo.Login;


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

    public static final int MESSAGE_FRIENDS_USERS = 6;
    public static final int MESSAGE_MY_STATUS = 7;
}

