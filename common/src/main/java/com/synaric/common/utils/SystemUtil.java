package com.synaric.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * 系统相关工具类。
 * Created by Synaric on 2016/9/21 0021.
 */
@SuppressWarnings("unused")
public class SystemUtil {

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
}
