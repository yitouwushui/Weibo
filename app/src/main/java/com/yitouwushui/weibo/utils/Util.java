package com.yitouwushui.weibo.utils;

import android.util.Log;

import com.yitouwushui.weibo.entity.Pic_urls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Util {


    /**
     * 将字符串转换为时间
     *
     * @param str
     * @return
     */
    public static String stringTranslateTime(String str) {
        Date date = new Date(str);
        return date.toLocaleString();
    }


    /**
     * 将时间转为字符串
     *
     * @param date
     * @return
     */
    public static String timeTranslateString(Date date) {
        String str = date.toString();
        return str;
    }


    /**
     * 将图片字符串转换成对应的 字符串数组
     *
     * @param pic
     * @return
     */
    public static String[] getPicList(String pic) {
        String[] pics = pic.split(",");
        return pics;
    }


}
