package com.yitouwushui.weibo.utils;

import android.content.Context;
import android.content.Intent;

import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.me.HomeActivity;

/**
 * Created by yitouwushui on 2016/3/13.
 */
public class IntentUtils {

    public IntentUtils() {

    }

    public static void startHome(Context context, String idstr) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("homeIdstr",idstr);
    }
}
