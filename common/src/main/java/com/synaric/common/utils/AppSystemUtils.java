package com.synaric.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 系统、应用操作相关工具类。
 * <br/><br/>Created by Synaric on 2016/9/29 0029.
 */
public class AppSystemUtils {

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
}
