package com.yitouwushui.weibo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class OtherUtil {
    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < Config.MIME_MapTable.length; i++) {
            if (end.equals(Config.MIME_MapTable[i][0]))
                type = Config.MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 通过long获得文件大小
     *
     * @param length
     * @return
     * @author Huangbin
     * @date 2015-1-10
     */
    public static String getFileLength(long length) {
        if (length < 0) {
            return "";
        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        String str = length + "B";
        double result = length;
        if (result > 900) {
            result = result / 1024.0;
            str = nf.format(result) + "KB";
        }
        if (result > 900) {
            result = result / 1024.0;
            str = nf.format(result) + "MB";
        }
        if (result > 900) {
            result = result / 1024.0;
            str = nf.format(result) + "GB";
        }
        return str;
    }

    public static String getTime(int duration) {
        if (duration < 0) {
            return "未知";
        }
        if (duration > 3600 * 24) {
            int h = duration / 3600;
            return "超过" + h + "小时";
        }
        if (duration > 3600) {
            int h = duration / 3600;
            int m = duration % 3600 / 60;
            return h + ":" + m + ":" + duration % 60;
        }
        if (duration > 60) {
            int m = duration / 60;
            return "0:" + m + ":" + duration % 60;
        }
        return "0:0:" + duration;
    }

    public static String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        return format.format(date);
    }

    @SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
    public static Drawable getApkIcon(String apkPath, Context context) {
        String PATH_PackageParser = "android.content.pm.PackageParser";
        String PATH_AssetManager = "android.content.res.AssetManager";
        try {
            Class pkgParserCls = Class.forName(PATH_PackageParser);
            Class[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            Object pkgParser = pkgParserCt.newInstance(valueArgs);
            LogUtil.d("ANDROID_LAB", "pkgParser:" + pkgParser.toString());
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            typeArgs = new Class[4];
            typeArgs[0] = File.class;
            typeArgs[1] = String.class;
            typeArgs[2] = DisplayMetrics.class;
            typeArgs[3] = Integer.TYPE;
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
                    "parsePackage", typeArgs);
            valueArgs = new Object[4];
            valueArgs[0] = new File(apkPath);
            valueArgs[1] = apkPath;
            valueArgs[2] = metrics;
            valueArgs[3] = 0;
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
                    valueArgs);
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
                    "applicationInfo");
            ApplicationInfo info = (ApplicationInfo) appInfoFld
                    .get(pkgParserPkg);
            Class assetMagCls = Class.forName(PATH_AssetManager);
            Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
            Object assetMag = assetMagCt.newInstance((Object[]) null);
            typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
                    "addAssetPath", typeArgs);
            valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
            Resources res = context.getResources();// getResources();
            typeArgs = new Class[3];
            typeArgs[0] = assetMag.getClass();
            typeArgs[1] = res.getDisplayMetrics().getClass();
            typeArgs[2] = res.getConfiguration().getClass();
            Constructor resCt = Resources.class.getConstructor(typeArgs);
            valueArgs = new Object[3];
            valueArgs[0] = assetMag;
            valueArgs[1] = res.getDisplayMetrics();
            valueArgs[2] = res.getConfiguration();
            res = (Resources) resCt.newInstance(valueArgs);
            CharSequence label = null;
            if (info.labelRes != 0) {
                label = res.getText(info.labelRes);
            }
            LogUtil.d("ANDROID_LAB", "label=" + label);
            if (info.icon != 0) {
                return res.getDrawable(info.icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<File, Long> getFileMap(File menu) {
        File[] files = menu.listFiles();
        HashMap<File, Long> fileMap = new HashMap<File, Long>();
        for (File f : files) {
            if (f.isFile()) {
                fileMap.put(f, f.length());
            } else {
                // 是目录
                fileMap.put(f, -1l);
            }
        }
        return fileMap;
    }

    public static boolean isMobilePhoneNumber(String num) {
        if (num.length() == 11) {
            try {
                Long.valueOf(num);
                return num.startsWith("1");
            } catch (Exception e) {
            }
        }
        return false;
    }

    /**
     * 比较器
     *
     * @author Huangbin
     * @date 2015-1-10
     */
    static class MyComparable implements Comparator<File> {

        @Override
        public int compare(File file1, File file2) {
            return file1.getName().toLowerCase()
                    .compareTo(file2.getName().toLowerCase());
        }

    }

    public static String getDate(long createTime) {
        Date date = new Date(createTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static boolean contain(String parent[], String son) {
        for (String str : parent) {
            if (str.equals(son.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(19)
    public static void setTranslucentStatus(Activity act, boolean on) {
        Window win = act.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 开启沉浸式菜单栏
     *
     * @param act
     */
    @TargetApi(19)
    public static void setTranslucent(Activity act) {
        Window win = act.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);
    }

    /**
     * 获得正在运行的进程名字
     *
     * @param appContext
     * @return
     */
    public static String getProcessName(Context appContext) {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }


    /**
     * 判断MIUI的悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isMiuiFloatWindowOpAllowed(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        return (context.getApplicationInfo().flags & 1 << 27) == 1;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class managerClass = manager.getClass();
                Method method = managerClass.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int isAllowNum = (Integer) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());

                if (AppOpsManager.MODE_ALLOWED == isAllowNum) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static String getDistanceText(double distance) {
        String result;
        if (distance < 10) {
            return "<10m";
        } else if (distance > 1000) {
            distance /= 1000;
            result = "" + distance;
            if (result.contains(".")) {
                result = result.substring(0, Math.min(result.indexOf(".") + 3, result.length()));
            }
            return result + "km";
        } else {
            result = "" + distance;
            if (result.contains(".")) {
                result = result.substring(0, Math.min(result.indexOf(".") + 3, result.length()));
            }
            return result + "m";
        }
    }
}
