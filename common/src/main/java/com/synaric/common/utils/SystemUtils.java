package com.synaric.common.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Process;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 系统相关工具类。
 * Created by Synaric on 2016/9/21 0021.
 */
@SuppressWarnings("unused")
public class SystemUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 。
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 。
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 是否DEBUG模式。
     */
    public static boolean isDebugMode(Context context){
        ApplicationInfo info= context.getApplicationInfo();
        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 获取设备唯一标识。某些Android平板获取不到IMEI，则使用Secure下的标识码代替。
     * 需要申请权限{@link android.Manifest.permission#READ_PHONE_STATE}。
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            return imei;
        }
    }

    /**
     * 获取应用签名。
     * .so库在被调用的时候应该首先获取应用的签名并进行验证，防止其他应用直接使用.so库。
     */
    public static String getSignInfo(Context context) {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            return sign.toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 是否主进程。
     */
    public static boolean isMainProgress(Context context) {
        return context.getPackageName().equals(getCurrentProcessName(context));
    }

    /**
     * 获取当前进程名。
     */
    public static String getCurrentProcessName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取指定.dex文件的SHA1值。
     * @param dex 索引，从1开始。
     */
    public static String getDexSHA1(Context context, int dex) {
        ApplicationInfo ai = context.getApplicationInfo();
        String source = ai.sourceDir;
        try {
            JarFile jar = new JarFile(source);
            Manifest mf = jar.getManifest();
            Map<String, Attributes> map = mf.getEntries();
            Attributes a = map.get("classes" + dex + ".dex");
            return a.getValue("SHA1-Digest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    /**
     * 获取版本号。
     */
    public static int getVersionCode(Context context){
        PackageManager packageManager = context.getPackageManager();

        int versionCode = 0;
        try {

            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(0 == versionCode){
            throw new RuntimeException("versionCode is not found. return null");
        }

        return versionCode;
    }

    /**
     * 获取版本名。
     */
    public static String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();

        String versionName = null;
        try {

            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(null == versionName){
            throw new NullPointerException("versionName is fot found. return null");
        }

        return versionName;
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值,或者异常)，则返回值为default_channel。
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return "default_channel";
        }
        String resultData = "default_channel";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }
}
