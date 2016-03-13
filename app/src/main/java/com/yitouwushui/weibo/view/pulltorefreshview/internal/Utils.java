package com.yitouwushui.weibo.view.pulltorefreshview.internal;

import com.yitouwushui.weibo.utils.LogUtil;

public class Utils {

    static final String LOG_TAG = "PullToRefresh";

    public static void warnDeprecation(String depreacted, String replacement) {
        LogUtil.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }

}
