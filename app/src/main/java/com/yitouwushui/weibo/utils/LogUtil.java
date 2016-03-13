package com.yitouwushui.weibo.utils;

import android.util.Log;

public class LogUtil {
    private static boolean isDebug = true;
    private static String TAG = "hb";

    public static void e(String text) {
        e(TAG, text);
    }


    public static void e(String tag, String text) {
        if (isDebug && text != null) {
            Log.e(tag, text);
        }
    }

    public static void w(String text) {
        w(TAG, text);
    }

    public static void w(String tag, String text) {
        if (isDebug && text != null) {
            Log.w(tag, text);
        }
    }

    public static void i(String text) {
        i(TAG, text);
    }

    public static void i(String tag, String text) {
        if (isDebug && text != null) {
            Log.i(tag, text);
        }
    }

    public static void d(String text) {
        d(TAG, text);
    }

    public static void d(String tag, String text) {
        if (isDebug && text != null) {
            Log.d(tag, text);
        }
    }

    public static void v(String text) {
        v(TAG, text);
    }

    public static void v(String tag, String text) {
        if (isDebug && text != null) {
            Log.v(tag, text);
        }
    }


    public static void printStackTrace() {
        try {
            StackTraceElement[] sts = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTraceElement : sts) {
                e(stackTraceElement.toString());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
