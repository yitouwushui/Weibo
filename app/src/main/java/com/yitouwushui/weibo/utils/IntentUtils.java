package com.yitouwushui.weibo.utils;

import android.content.Context;
import android.content.Intent;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.main.UpdateActivity;
import com.yitouwushui.weibo.me.HomeActivity;

/**
 * 意图工具类
 */
public class IntentUtils {

    public IntentUtils() {

    }

    /**
     * 跳转个人主页
     *
     * @param context
     * @param userIdstr 需要查看的用户Id
     */
    public static void startHome(Context context, String userIdstr) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(App.ACTION_USERID, userIdstr);
        context.startActivity(intent);
    }

    /**
     * 转发微博
     *
     * @param context
     * @param statusIdstr 转发的微博id
     */
    public static void startTranlate(Context context, String statusIdstr) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(App.ACTION_UPDATE_TITLE, "转发");
        intent.putExtra(App.ACTION_TRANLATE_STATUS_IDSTR, statusIdstr);
        context.startActivity(intent);
    }

    /**
     * 评论微博
     *
     * @param context
     * @param statusIdstr 评论的微博id
     */
    public static void startComment(Context context, String statusIdstr) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(App.ACTION_UPDATE_TITLE, "评论");
        intent.putExtra(App.ACTION_COMMENT_STATUS_IDSTR, statusIdstr);
        context.startActivity(intent);
    }

    /**
     * 发微博
     *
     * @param context
     */
    public static void startUpdate(Context context) {
        Intent intent = new Intent(context, UpdateActivity.class);

        intent.putExtra(App.ACTION_UPDATE_TITLE, "发微博");
        context.startActivity(intent);
    }


}
