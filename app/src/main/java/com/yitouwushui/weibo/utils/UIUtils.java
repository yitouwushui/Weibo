package com.yitouwushui.weibo.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIUtils {

    /**
     * tag
     */
    private static final String TAG = "Utils";

    /**
     * 获取权限
     *
     * @param permission 权限
     * @param path       文件路径
     */
    public static void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
        }
    }

    /**
     * 是否安装了sdcard。
     *
     * @return true表示有，false表示没有
     */
    public static boolean haveSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取系统内部可用空间大小
     *
     * @return available size
     */

    public static long getSystemAvailableSize() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        return availCount * blockSize;
    }

    /**
     * 获取sd卡可用空间大小
     *
     * @return available size
     */
    public static long getSDCardAvailableSize() {
        long available = 0;
        if (haveSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();

            available = availaBlock * blocSize;
        } else {
            available = -1;
        }
        return available;
    }

    public static String getAvailSDSize() {
        if (haveSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs fs = new StatFs(path.getPath());
            long size = (long) fs.getAvailableBlocks() * (long) fs.getBlockSize();
            return OtherUtil.getFileLength(size);
        } else {
            return "没有SD卡";
        }
    }

    public static String getAvailROMSize() {
        File path = Environment.getDataDirectory();
        StatFs fs = new StatFs(path.getPath());
        long f_bavail = fs.getAvailableBlocks();
        return OtherUtil.getFileLength(f_bavail
                * fs.getBlockSize());
    }

    public static long getAvailROMLength() {
        File path = Environment.getDataDirectory();
        StatFs fs = new StatFs(path.getPath());
        return fs.getAvailableBlocks() * fs.getBlockSize();
    }

    /**
     * 获取application层级的metadata
     *
     * @param context
     * @param key
     * @return
     */
    public static String getApplicationMetaData(Context context, String key) {
        try {
            Object metaObj = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).metaData
                    .get(key);
            if (metaObj instanceof String) {
                return metaObj.toString();
            } else if (metaObj instanceof Integer) {
                return ((Integer) metaObj).intValue() + "";
            } else if (metaObj instanceof Boolean) {
                return ((Boolean) metaObj).booleanValue() + "";
            }
        } catch (NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        return 0;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 隐藏键盘
     *
     * @param activity activity
     */
    public static void hideInputMethod(Activity activity) {
        hideInputMethod(activity, activity.getCurrentFocus());
    }

    /**
     * 隐藏键盘
     *
     * @param context context
     * @param view    The currently focused view
     */
    public static void hideInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入键盘
     *
     * @param context context
     * @param view    The currently focused view, which would like to receive soft
     *                keyboard input
     */
    public static void showInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * Bitmap缩放，注意源Bitmap在缩放后将会被回收。
     *
     * @param origin
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getScaleBitmap(Bitmap origin, int width, int height) {
        float originWidth = origin.getWidth();
        float originHeight = origin.getHeight();

        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width) / originWidth;
        float scaleHeight = ((float) height) / originHeight;

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap scale = Bitmap.createBitmap(origin, 0, 0, (int) originWidth,
                (int) originHeight, matrix, true);
        origin.recycle();
        return scale;
    }

    /**
     * 计算某一时间与现在时间间隔的文字提示
     */
    public static String countTimeIntervalText(long time) {
        long dTime = System.currentTimeMillis() - time;
        // 15分钟
        if (dTime < 15 * 60 * 1000) {
            return "刚刚";
        } else if (dTime < 60 * 60 * 1000) {
            // 一小时
            return "一小时内";
        } else if (dTime < 24 * 60 * 60 * 1000) {
            return (int) (dTime / (60 * 60 * 1000)) + "小时前";
        } else {
            return DateFormat.format("MM-dd kk:mm", System.currentTimeMillis())
                    .toString();
        }
    }

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int x = 0, statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取标题栏高度
     *
     * @param context
     * @return
     */
    public static int getTitleBarHeight(Activity context) {
        int contentTop = context.getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(context);
    }

    /**
     * 获取屏幕宽度，px
     *
     * @param context
     * @return
     */
    public static float getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度，px
     *
     * @param context
     * @return
     */
    public static float getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕像素密度
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取scaledDensity
     *
     * @param context
     * @return
     */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    /**
     * 计算View的width以及height
     *
     * @param child
     */
    @SuppressWarnings("deprecation")
    public static void measureView(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0,
                params.width);
        int lpHeight = params.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 获取当前小时分钟，24小时制
     *
     * @return
     */
    public static String getTime24Hours() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取电池电量,0~1
     *
     * @param context
     * @return
     */
    @SuppressWarnings("unused")
    public static float getBattery(Context context) {
        Intent batteryInfoIntent = context.getApplicationContext()
                .registerReceiver(null,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = batteryInfoIntent.getIntExtra("status", 0);
        int health = batteryInfoIntent.getIntExtra("health", 1);
        boolean present = batteryInfoIntent.getBooleanExtra("present", false);
        int level = batteryInfoIntent.getIntExtra("level", 0);
        int scale = batteryInfoIntent.getIntExtra("scale", 0);
        int plugged = batteryInfoIntent.getIntExtra("plugged", 0);
        int voltage = batteryInfoIntent.getIntExtra("voltage", 0);
        int temperature = batteryInfoIntent.getIntExtra("temperature", 0); // 温度的单位是10℃
        String technology = batteryInfoIntent.getStringExtra("technology");
        return ((float) level) / scale;
    }

    /**
     * 获取手机名称
     *
     * @return
     */
    public static String getMobileName() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 是否安装了sdcard。
     *
     * @return true表示有，false表示没有
     */
    public static boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取sd卡可用空间
     *
     * @return available size
     */
    public static long getAvailableExternalSize() {
        long available = 0;
        if (hasSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();

            available = availaBlock * blocSize;
        } else {
            available = -1;
        }
        return available;
    }

    /**
     * 获取内存可用空间
     *
     * @return available size
     */
    public static long getAvailableInternalSize() {
        long available = 0;
        if (hasSDCard()) {
            File path = Environment.getRootDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();

            available = availaBlock * blocSize;
        } else {
            available = -1;
        }
        return available;
    }

	/*
     * 版本控制部分
	 */

    /**
     * 是否在2.2版本及以上
     *
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 是否在2.3版本及以上
     *
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 是否在3.0版本及以上
     *
     * @return
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 是否在3.1版本及以上
     *
     * @return
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 是否在4.1版本及以上
     *
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static String getPhoneType() {

        String phoneType = Build.MODEL;


        return phoneType;
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getOsVersion() {
        String osversion;
        int osversion_int = getOsVersionInt();
        osversion = osversion_int + "";
        return osversion;

    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static int getOsVersionInt() {
        return Build.VERSION.SDK_INT;

    }

//    /**
//     * 获取ip地址
//     *
//     * @return
//     */
//    public static String getHostIp() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf
//                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(inetAddress
//                            .getHostAddress())) {
//                        if (!inetAddress.getHostAddress().toString()
//                                .equals("null")
//                                && inetAddress.getHostAddress() != null) {
//                            return inetAddress.getHostAddress().toString()
//                                    .trim();
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//        }
//        return "";
//    }

    /**
     * 获取手机号，几乎获取不到
     *
     * @param context
     * @return
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getApplicationContext().getSystemService(
                        Context.TELEPHONY_SERVICE);
        String phoneNum = mTelephonyMgr.getLine1Number();
        return TextUtils.isEmpty(phoneNum) ? "" : phoneNum;
    }

    /**
     * 获取imei号
     *
     * @param context
     * @return
     */
    public static String getPhoneImei(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getApplicationContext().getSystemService(
                        Context.TELEPHONY_SERVICE);
        String phoneImei = mTelephonyMgr.getDeviceId();
        return TextUtils.isEmpty(phoneImei) ? "" : phoneImei;
    }

    /**
     * 获取imsi号
     *
     * @param context
     * @return
     */
    public static String getPhoneImsi(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getApplicationContext().getSystemService(
                        Context.TELEPHONY_SERVICE);
        String phoneImsi = mTelephonyMgr.getSubscriberId();

        return TextUtils.isEmpty(phoneImsi) ? "" : phoneImsi;
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getLocalMacAddress() {
        String Mac = null;
        try {
            String path = "sys/class/net/wlan0/address";
            if ((new File(path)).exists()) {
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer = new byte[8192];
                int byteCount = fis.read(buffer);
                if (byteCount > 0) {
                    Mac = new String(buffer, 0, byteCount, "utf-8");
                }
                fis.close();
            }

            if (Mac == null || Mac.length() == 0) {
                path = "sys/class/net/eth0/address";
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer_name = new byte[8192];
                int byteCount_name = fis.read(buffer_name);
                if (byteCount_name > 0) {
                    Mac = new String(buffer_name, 0, byteCount_name, "utf-8");
                }
                fis.close();
            }

            if (Mac == null || Mac.length() == 0) {
                return "";
            } else if (Mac.endsWith("\n")) {
                Mac = Mac.substring(0, Mac.length() - 1);
            }
        } catch (Exception io) {
        }

        return TextUtils.isEmpty(Mac) ? "" : Mac;
    }

    /**
     * 获取重复字段最多的个数
     *
     * @param s
     * @return
     */
    public static int getRepeatTimes(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }

        char[] mChars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < mChars.length; i++) {
            char key = mChars[i];
            if (map.containsKey(key)) {
                int value = map.get(key);
                ++value;
                map.put(key, value);
            } else {
                map.put(key, 1);
            }
        }

        Iterator<Integer> values = map.values().iterator();
        int count = 0;
        while (values.hasNext()) {
            int value = values.next();
            if (value > count) {
                count = value;
            }
        }

        return count;
    }

    /**
     * 简单判断是否为手机号码
     *
     * @param num
     * @return
     */
    public static boolean isPhoneNum(String num) {
        // modified by liyang
        // 确保每一位都是数字
        return !TextUtils.isEmpty(num) && num.matches("1[0-9]{10}")
                && !isRepeatedStr(num) && !isContinuousNum(num);
    }

    /**
     * 判断是否为重复字符串
     *
     * @param str ，需要检查的字符串
     */
    public static boolean isRepeatedStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int len = str.length();
        if (len <= 1) {
            return false;
        } else {
            char firstChar = str.charAt(0);// 第一个字符
            for (int i = 1; i < len; i++) {
                char nextChar = str.charAt(i);// 第i个字符
                if (firstChar != nextChar) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断字符串是否为连续数字 45678901等
     */
    public static boolean isContinuousNum(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        if (!isNumbericString(str))
            return true;
        int len = str.length();
        for (int i = 0; i < len - 1; i++) {
            char curChar = str.charAt(i);
            char verifyChar = (char) (curChar + 1);
            if (curChar == '9')
                verifyChar = '0';
            char nextChar = str.charAt(i + 1);
            if (nextChar != verifyChar) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为连续字母 xyZaBcd等
     */
    public static boolean isContinuousWord(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        if (!isAlphaBetaString(str))
            return true;
        int len = str.length();
        String local = str.toLowerCase();
        for (int i = 0; i < len - 1; i++) {
            char curChar = local.charAt(i);
            char verifyChar = (char) (curChar + 1);
            if (curChar == 'z')
                verifyChar = 'a';
            char nextChar = local.charAt(i + 1);
            if (nextChar != verifyChar) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是否为纯数字
     *
     * @param str ，需要检查的字符串
     */
    public static boolean isNumbericString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]+$");// 从开头到结尾必须全部为数字
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 判断是否为纯字母
     *
     * @param str
     * @return
     */
    public static boolean isAlphaBetaString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern p = Pattern.compile("^[a-zA-Z]+$");// 从开头到结尾必须全部为字母或者数字
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 判断是否为纯字母或数字
     *
     * @param str
     * @return
     */
    public static boolean isAlphaBetaNumbericString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");// 从开头到结尾必须全部为字母或者数字
        Matcher m = p.matcher(str);

        return m.find();
    }

    private static String regEx = "[\u4e00-\u9fa5]";
    private static Pattern pat = Pattern.compile(regEx);

    /**
     * 判断是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /****************************************************************************/
    // import PPutils
    private static int id = 1;

    public static int getNextId() {
        return id++;
    }

    /**
     * 将输入流转为字节数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] read2Byte(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        return outSteam.toByteArray();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        // canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 判断是否符合月和年的过期时间规则
     *
     * @param date
     * @return
     */
    public static boolean isMMYY(String date) {
        try {
            if (!TextUtils.isEmpty(date) && date.length() == 4) {
                int mm = Integer.parseInt(date.substring(0, 2));
                return mm > 0 && mm < 13;
            }

        } catch (Exception e) {
        }

        return false;
    }

    /**
     * 20120506 共八位，前四位-年，中间两位-月，最后两位-日
     *
     * @param date
     * @return
     */
    public static boolean isRealDate(String date, int yearlen) {
        // if(yearlen != 2 && yearlen != 4)
        // return false;
        int len = 4 + yearlen;
        if (date == null || date.length() != len)
            return false;

        if (!date.matches("[0-9]+"))
            return false;

        int year = Integer.parseInt(date.substring(0, yearlen));
        int month = Integer.parseInt(date.substring(yearlen, yearlen + 2));
        int day = Integer.parseInt(date.substring(yearlen + 2, yearlen + 4));

        if (year <= 0)
            return false;
        if (month <= 0 || month > 12)
            return false;
        if (day <= 0 || day > 31)
            return false;

        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return day > 30 ? false : true;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                    return day > 29 ? false : true;
                return day > 28 ? false : true;
            default:
                return true;
        }
    }

    /**
     * 判断字符串是否为连续字符 abcdef 45678等
     */
    public static boolean isContinuousStr(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char curChar = str.charAt(i);
            char nextChar = (char) (curChar + 1);
            if (i + 1 < len) {
                nextChar = str.charAt(i + 1);
            }
            if (nextChar != (curChar + 1)) {
                return false;
            }
        }
        return true;
    }

    public static final String REGULAR_NUMBER = "(-?[0-9]+)(,[0-9]+)*(\\.[0-9]+)?";

    /**
     * 为字符串中的数字染颜色
     *
     * @param str   ，待处理的字符串
     * @param color ，需要染的颜色
     * @return
     */
    public static SpannableString setDigitalColor(String str, int color) {
        if (str == null)
            return null;
        SpannableString span = new SpannableString(str);

        Pattern p = Pattern.compile(REGULAR_NUMBER);
        Matcher m = p.matcher(str);
        while (m.find()) {
            int start = m.start();
            int end = start + m.group().length();
            span.setSpan(new ForegroundColorSpan(color), start, end,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return span;
    }

    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    public static String getFixedNumber(String str, int length) {
        if (str == null || length <= 0 || str.length() < length) {
            return null;
        }
        Pattern p = Pattern.compile("\\d{" + length + "}");
        Matcher m = p.matcher(str);
        String result = null;
        if (m.find()) {
            int start = m.start();
            int end = start + m.group().length();
            result = str.substring(start, end);
        }

        return result;
    }

    public static int getLengthWithoutSpace(CharSequence s) {
        return s.toString().replaceAll(" ", "").length();
    }

    /**
     * 计算上次更新时间的提示文字
     */
    public static String countLastRefreshHintText(long time) {
        long dTime = System.currentTimeMillis() - time;
        // 15分钟
        if (dTime < 15 * 60 * 1000) {
            return "刚刚";
        } else if (dTime < 60 * 60 * 1000) {
            // 一小时
            return "一小时内";
        } else if (dTime < 24 * 60 * 60 * 1000) {
            return (int) (dTime / (60 * 60 * 1000)) + "小时前";
        } else {
            return DateFormat.format("MM-dd kk:mm", System.currentTimeMillis())
                    .toString();
        }
    }

    /**
     * 设置View是否可见
     *
     * @param view
     * @param isVisible
     */
    public static void setViewSafeVisible(View view, Boolean isVisible) {
        if (view != null) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 传入长/宽，居中得到一个图片
     *
     * @param bitmap
     * @param scale
     * @return
     */
    public static Bitmap turnSmall(Bitmap bitmap, float scale) {

        if (scale > 2.5f) {
            scale = 2.5f;
        }
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float x, y, wi, he;
        if (width / height > scale) {
            // 截取width
            wi = (height * scale);
            x = (width - wi) / 2;
            y = 0;
            he = height;
        } else {
            x = 0;
            wi = width;
            he = (width / scale);
            y = (height - he) / 2;
        }
//        Matrix matrix = new Matrix();
//        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        try {
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, (int) x, (int) y, (int) wi, (int) he);
            return resizeBmp;
        } catch (Exception e) {
            // 有的厂商随意修改createBitmap方法，可能造成nullpoint。
        }
        return bitmap;
    }

    /**
     * 计算指定ListView的滚动位置<br>
     * 数组第 1 个值是列表首个可见内容的位置<br>
     * 数组第 2 个值是列表首个元素的Top<br>
     *
     * @param listView
     * @return 可能返回null
     */
    public static int[] getListViewScrollPosition(ListView listView) {
        if (listView != null && listView.getAdapter() != null && listView.getAdapter().getCount() != 0) {
            View v = listView.getChildAt(0);
            int firstChildTop = v == null ? 0 : v.getTop();
            int[] firstVisiblePosition = new int[]{
                    listView.getFirstVisiblePosition(),
                    firstChildTop
            };
            return firstVisiblePosition;
        }
        return null;
    }

    /**
     * 设置ListView的滚动位置
     *
     * @param listView
     * @param firstVisibleItemPosition 列表首个可见内容的位置
     * @param firstChildTop            列表首个元素的Top
     */
    public static void setListViewScrollPosition(ListView listView, int firstVisibleItemPosition, int firstChildTop) {
        if (listView != null) {
            try {
                if (listView.getAdapter() != null && listView.getAdapter().getCount() < firstVisibleItemPosition) {
                    return;
                }
                listView.setSelectionFromTop(Math.max(firstVisibleItemPosition, 0), firstChildTop);
            } catch (Exception e) {
            }
        }
    }


    /**
     * 加载大图，进行剪裁，节省内存
     *
     * @param context
     * @param imageView
     * @param srcId
     */
    public static void loadBigImage(final Context context, final ImageView imageView, final int srcId) {
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                int width = 0, height = 0;
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                if (params != null) {
                    if (params.width > 0) {
                        width = params.width;
                    } else {
                        width = imageView.getWidth();
                    }

                    if (params.height > 0) {
                        height = params.height;
                    } else {
                        height = imageView.getHeight();
                    }
                }

                try {
                    Bitmap bitmap = decodeSampledBitmapFromResource(context.getResources(), srcId, width, height);
                    imageView.setImageBitmap(bitmap);
                } catch (OutOfMemoryError oom) {
                    imageView.setImageBitmap(null);
                }

            }
        });
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if ((reqHeight > 0 && height > reqHeight) || (reqHeight > 0 && width > reqWidth)) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 获得该text的px长
     *
     * @param textView
     * @param text
     * @return
     */
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

    public static String getAllTime(String time) {
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat sdf = new SimpleDateFormat("yy年MM月dd日 HH:mm:ss");
        return sdf.format(date);
    }

    public static String getTime(String time) {
        return getTime(time, "HH:mm:ss");
    }

    public static String getTime(String time, String format) {
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getDate(String time) {
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(date);
    }
}
