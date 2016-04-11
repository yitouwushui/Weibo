package com.yitouwushui.weibo.net;

import android.os.Handler;

import com.yitouwushui.weibo.entity.Comments;
import com.yitouwushui.weibo.entity.Status;


public interface NetQuery {

    /**
     * 登录请求
     *
     * @param handler
     * @param url
     */
    void loginQuery(Handler handler, String url);

    /**
     * 获得用户资料
     *
     * @param handler
     */
    void userQuery(Handler handler);

    /**
     * 获得他人的资料
     *
     * @param handler
     */
    void userOtherQuery(Handler handler, String idstr);

    /**
     * 获得公共微博
     *
     * @param handler
     */
    void publicStatusQuery(Handler handler);

    /**
     * 获得关注微博
     *
     * @param handler
     */
    void friendStatusQuery(Handler handler);

    /**
     * 根据id获得单条微博信息
     * @param handler
     * @param idstr
     */
    void singleStatusQuery(Handler handler,String idstr);

    /**
     * 获得指定用户的微博
     *
     * @param handler
     */
    void myStatusQuery(Handler handler, String idstr, int page);


    /**
     * 查询单条微博的评论
     *
     * @param handler
     * @param idstr
     */
    void statusCommentQuery(Handler handler, String idstr, int page);


    /**
     * 评论一条微博
     * @param handler
     * @param string
     * @param statusId
     */
    void commentStatus(Handler handler, String string,String statusId);

    /**
     * 转发一条微博
     */
    void repostsStatusQuery(Handler handler, String Idstr);


    /**
     * 发布一条微博
     *
     * @param handler
     * @param string
     */
    void updateStatus(Handler handler, String string);

    /**
     * 发布一条带图片的微博
     *
     * @param handler
     * @param string
     */
    void uploadStatus(Handler handler, String string);

    /**
     * 查询一个用户的关注数量
     *
     * @param handler
     * @param idstr
     */
    void userFriendsQuery(Handler handler, String idstr, int cursor);

    /**
     * 查询一个用户的粉丝数量
     *
     * @param handler
     * @param idstr
     */
    void userFollowersQuery(Handler handler, String idstr, int cursor);

    /**
     * 收藏一条微博
     *
     * @param handler
     * @param statusIdstr
     */
    void addFavorites(Handler handler, String statusIdstr);
}
